package com.cft.hogan.platform.ppm.services.massmaintenance.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPException;

import org.apache.axis.message.MessageElement;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cft.hogan.platform.ppm.services.config.context.SystemContext;
import com.cft.hogan.platform.ppm.services.massmaintenance.bean.ImportTaskBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.ImportTaskDAO_I;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.cor.ImportTaskDAO_COR;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.pascor.ImportTaskDAO_PASCOR;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.pastda.ImportTaskDAO_PASTDA;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.tda.ImportTaskDAO_TDA;
import com.cft.hogan.platform.ppm.services.massmaintenance.entity.ImportTaskEntity;
import com.cft.hogan.platform.ppm.services.massmaintenance.entity.ImportTaskReviewDetailEntity;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.BadRequestException;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.BusinessException;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.ItemNotFoundException;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.SystemException;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Constants;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.PCDService;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Utils;
import com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfCdkKey_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfRegKey_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfSimKey_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdEntry;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItem;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdXmlRs_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.UpdPcdRecRq_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.UpdPcdRecRs_Type;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ImportTaskService {

	@Autowired
	private ImportTaskDAO_COR daoCOR;

	@Autowired
	private ImportTaskDAO_TDA daoTDA;

	@Autowired
	private ImportTaskDAO_PASCOR daoPASCOR;

	@Autowired
	private ImportTaskDAO_PASTDA daoPASTDA;

	@Autowired
	private ImportTaskReviewDetailsService importTaskReviewDetailsService;

	private StringBuffer validationMessgae = null;
	private int errorRow = 0; 

	public ImportTaskBean save(String taskName, String taskType, MultipartFile file){
		log.debug("Processing Import Task request: TaskName-"+taskName+" Tasktype-"+taskType+" File:"+file.getOriginalFilename());
		String uuid = null;
		try {
			PCDService service = new PCDService();
			ImportTaskBean bean = new ImportTaskBean();
			bean.setName(taskName);
			bean.setType(taskType);
			bean.setCreatedBy(SystemContext.getUser());		
			bean.setModifiedBy(SystemContext.getUser());	
			bean.setStatus(Constants.INPROGRESS);
			bean.setInputFileName(file.getOriginalFilename());

			Map<String, String> pcdNameMap = new HashMap<>();
			List<UpdPcdRecRq_Type> requestList = processWorkBook(file, null, false, pcdNameMap, service);
			if(requestList.size()<1) {
				throw new BadRequestException("NO valid PCD records present in the input file to process");
			}
			uuid = getDAO().save(beanToEntity(bean));
			log.debug("Import task created :"+uuid+" --Total num of records to be udpated :"+requestList.size());
			int endIndex = 0;
			int savedItems = 0;
			int batchSize = SystemContext.getPCDServiceBatchUpdateSize();
			for(int index=0; index < requestList.size();){
				endIndex  = index+batchSize;
				if(endIndex > requestList.size()) {
					endIndex = requestList.size();
				}
				savedItems += processUpdate(requestList.subList(index, endIndex), service, uuid ,pcdNameMap);
				index = endIndex;
			}
			log.debug("Import task ID :"+uuid+" --Import Task Review Details updated records :"+savedItems);
			updateImportTaskStatus(uuid);
		}catch(Exception e) {
			Utils.handleException(e);
		}
		return findByUUID(uuid);
	}


	public ImportTaskBean findByUUID(String taskId) {
		ImportTaskBean bean = null;
		try {
			bean = entityToBean(getDAO().findByUUID(taskId));
			bean.setImportTaskReviewDetails(importTaskReviewDetailsService.findByImportTaskUUID(taskId));
		}catch(Exception e) {
			Utils.handleException(e);
		}
		return bean;
	}


	public void delete(String taskId) {
		try {
			ImportTaskBean importTask = findByUUID(taskId);
			if(importTask == null ) {
				throw new ItemNotFoundException();
			}
			if(!SystemContext.getUser().equalsIgnoreCase(importTask.getCreatedBy())) {
				throw new BadRequestException("Import task can be deleted by task creator/owner");
			}

			List<ImportTaskReviewDetailEntity> taskDetailsList = importTaskReviewDetailsService.findPsetKeyByImportTaskUUIDAndStatus(taskId, Constants.SUCCESS);
			if(taskDetailsList !=null && taskDetailsList.size() > 0) {
				throw new BadRequestException("Delete not allowed when items updated by the task. Please review the updated items and submit the failed records.");
			}

			getDAO().delete(taskId);
		}catch(Exception e) {
			Utils.handleException(e);
		}
	}


	public List<ImportTaskBean> findByStatus(String status) {
		List<ImportTaskBean> beanList = new ArrayList<>();
		try {
			List<ImportTaskEntity>	entityList = null;
			entityList = getDAO().findByStatus(status);
			entityList.forEach((entity)->{
				beanList.add(entityToBean(entity));
			});
		}catch(Exception e) {
			Utils.handleException(e);
		}
		return  beanList;
	}


	public ImportTaskBean reload(String taskUUID, MultipartFile file, String user) {
		try {
			List<ImportTaskReviewDetailEntity> resultSet = importTaskReviewDetailsService.findPsetKeyByImportTaskUUIDAndStatus(taskUUID, Constants.FAILED);
			log.debug("Import task ID :"+taskUUID+" --No of failed records retrieved for import task"+taskUUID +"  :"+ resultSet.size());
			if(resultSet!=null && resultSet.size()>0) {
				PCDService service = new PCDService();
				List<String> failedItems = new ArrayList<>();
				resultSet.forEach((result)->{
					failedItems.add(result.getPSetKey());
				});
				List<UpdPcdRecRq_Type> requestList = processWorkBook(file, failedItems, true, null, service); 
				int endIndex = 0;
				int updatedItems = 0;
				int batchSize = SystemContext.getPCDServiceBatchUpdateSize();
				for(int index=0; index < requestList.size();){
					endIndex  = index+batchSize;
					if(endIndex > requestList.size()) {
						endIndex = requestList.size();
					}
					updatedItems += processReloadUpdate(requestList.subList(index, endIndex), service, taskUUID);
					index = endIndex;
				}
				log.debug("Import task ID :"+taskUUID+" --Import Task Review Details updated records :"+updatedItems);
			}
			updateImportTaskStatus(taskUUID);
		}catch(Exception e) {
			Utils.handleException(e);
		}
		return findByUUID(taskUUID);
	}


	private int processReloadUpdate(List<UpdPcdRecRq_Type> requestList, PCDService service, String taskUUID) throws RemoteException, Exception {
		UpdPcdRecRs_Type[] updPcdRecRs = service.updatePcd(requestList);
		List<UpdPcdRecRs_Type> responseList = Arrays.asList(updPcdRecRs);
		responseList.forEach((response) -> {
			if(response!=null) {
				try {
					ImportTaskReviewDetailEntity importTaskReviewDetail = new ImportTaskReviewDetailEntity();
					String key = Constants.EMPTY;
					MessageElement[] elements = getKeyElements(response.getCdmfKeyInfo());
					if (elements != null) {
						StringBuffer keys = new StringBuffer ();
						for (MessageElement node : elements) {
							String val = node.getValue();
							if(val!=null && !"null".equalsIgnoreCase(val) && !Constants.EMPTY.equalsIgnoreCase(val)) {
								keys.append(val);
								keys.append("|");
							}
						}

						if(keys.length()>0) {
							key = keys.substring(0, keys.length()-1);
						}
					}

					importTaskReviewDetail.setImportTaskUUID(taskUUID);
					importTaskReviewDetail.setPSetKey(key);
					importTaskReviewDetail.setResult(response.getXStatus().getStatusDesc());
					String status = Constants.SUCCESS;
					if(response.getXStatus().getStatusCode() != 0){
						status = Constants.FAILED;
					}
					importTaskReviewDetail.setStatus(status);			
					importTaskReviewDetail.setAction(response.getCdmfKeyInfo().getCdmfAction());
					importTaskReviewDetail.setCompanyID(String.valueOf(response.getCdmfKeyInfo().getCdmfFmtCoId()));
					importTaskReviewDetail.setApplicationID(String.valueOf(response.getCdmfKeyInfo().getCdmfOwnerApp()));
					importTaskReviewDetail.setEffectiveDate((String.valueOf(response.getCdmfKeyInfo().getCdmfFmtEffDt())));
					importTaskReviewDetail.setExpiryDate((String.valueOf(response.getCdmfKeyInfo().getCdmfFmtExpDt())));
					importTaskReviewDetail.setModifiedBy(SystemContext.getUser());
					try {
						importTaskReviewDetailsService.Update(importTaskReviewDetail);
					} catch (Exception e) {
						StringBuffer message = new StringBuffer();
						message.append("Import task ID :").append(taskUUID);
						message.append(" --Error in update Import task review details --Key :").append(key);
						message.append(" StatusDesc :").append(response.getXStatus().getStatusDesc());
						message.append(" StatusCode :").append(response.getXStatus().getStatusCode());
						throw new BusinessException(message.toString()); 
					}
				}catch(Exception e) {
					Utils.handleException(e);
				}
			}
		});
		return responseList.size();
	}


	private int processUpdate(List<UpdPcdRecRq_Type> requestList, PCDService service, String uuid, Map<String, String> pcdNameMap) throws RemoteException, Exception {

		UpdPcdRecRs_Type[] updPcdRecRs = service.updatePcd(requestList);
		List<UpdPcdRecRs_Type> responseList = Arrays.asList(updPcdRecRs);
		List<ImportTaskReviewDetailEntity> reviewList = new ArrayList<>();
		responseList.forEach((response) -> {
			try {
				String key = Constants.EMPTY;
				ImportTaskReviewDetailEntity input = new ImportTaskReviewDetailEntity();
				input.setImportTaskUUID(uuid);
				input.setApplicationID(response.getCdmfKeyInfo().getCdmfOwnerApp());
				input.setAction(response.getCdmfKeyInfo().getCdmfAction());
				input.setCompanyID(String.valueOf(response.getCdmfKeyInfo().getCdmfFmtCoId()));
				input.setEffectiveDate(response.getCdmfKeyInfo().getCdmfFmtEffDt());
				input.setExpiryDate(response.getCdmfKeyInfo().getCdmfFmtExpDt());
				input.setPSetName(pcdNameMap.get(String.valueOf(response.getCdmfKeyInfo().getCdmfFmt())));
				input.setPSetNumber(String.valueOf(response.getCdmfKeyInfo().getCdmfFmt()));
				input.setResult(response.getXStatus().getStatusDesc());
				input.setCreatedBy(SystemContext.getUser());
				if(response.getXStatus().getStatusCode() != 0){
					input.setStatus(Constants.FAILED);	
				}else {
					input.setStatus(Constants.SUCCESS);	
				}
					
				try {
					key = constructPCDKey(response.getCdmfKeyInfo());
					input.setPSetKey(key);
					reviewList.add(input);
				}catch(Exception e) {
					throw new BusinessException("Import task ID :"+uuid+" --Error occured prepare ImportTaskReviewDetailEntity PCD#/KEY :"+String.valueOf(response.getCdmfKeyInfo().getCdmfFmt())+"/"+key);
				}
			}catch(Exception e) {
				Utils.handleException(e);
			}
		});
		return importTaskReviewDetailsService.save(reviewList);
	}
	
	private String constructPCDKey(CdmfKeyInfo_Type  cdmfKeyInfo) {
		String key = Constants.EMPTY;
		MessageElement[] elements = getKeyElements(cdmfKeyInfo);
		if (elements != null) {
			StringBuffer keys = new StringBuffer ();
			for (MessageElement node : elements) {
				String val = node.getValue();
				if(val!=null && !"null".equalsIgnoreCase(val) && !Constants.EMPTY.equalsIgnoreCase(val)) {
					keys.append(val);
					keys.append("|");
				}
			}

			if(keys.length()>0) {
				key = keys.substring(0, keys.length()-1);
			}
		}

		if(key.length()<1){
			String msg = cdmfKeyInfo.getCdmfFmt()+Constants.EMPTY;
			if(validationMessgae!=null) {
				msg = validationMessgae.append(", row#: ").append(errorRow).toString();
			}
			throw new BadRequestException("Invalid PCD Key. "+msg);
		}
		return key;
	}

	private MessageElement[] getKeyElements(CdmfKeyInfo_Type cdmfKeyInfo) {
		if(cdmfKeyInfo.getCdmfCdkKey()!=null) {
			return cdmfKeyInfo.getCdmfCdkKey().get_any();
		}else if(cdmfKeyInfo.getCdmfRegKey()!=null) {
			return cdmfKeyInfo.getCdmfRegKey().get_any();
		}else if(cdmfKeyInfo.getCdmfSimKey()!=null) {
			return cdmfKeyInfo.getCdmfSimKey().get_any();
		}
		return null;
	}


	private List<UpdPcdRecRq_Type>  processWorkBook(MultipartFile file, List<String> failedItems, boolean reload, Map<String, String> pcdNameMap, PCDService service) throws Exception {
		List<UpdPcdRecRq_Type> requestList = new ArrayList<UpdPcdRecRq_Type>();
		HSSFWorkbook psetWorkbook = new HSSFWorkbook(file.getInputStream());
		try {
			int sheetCount = psetWorkbook.getNumberOfSheets();
			List<String> pcdKeys = new ArrayList<>();
			log.debug("Input file :"+file.getOriginalFilename()+" --No of sheets in the workbook: "+sheetCount);
			for(int sheetIndex = 0; sheetIndex<sheetCount; sheetIndex++) { 
				validationMessgae = new StringBuffer();
				validationMessgae.append("Workbook: ").append(file.getOriginalFilename());
				HSSFSheet hssfSheet = psetWorkbook.getSheetAt(sheetIndex);
				String worksheetName = hssfSheet.getSheetName();
				validationMessgae.append(", Sheet: ").append(worksheetName);
				log.debug("Input file :"+file.getOriginalFilename()+" --Processing worksheet: "+worksheetName);

				HSSFRow header = hssfSheet.getRow(0);
				String[] head = String.valueOf(header.getCell(0)).split("-");
				String parameterNum = head[0];
				if(pcdNameMap!=null) {
					pcdNameMap.put(head[0], head[1]);
				}

				PcdXmlRs_Type template = null;
				HSSFRow  labelRow = hssfSheet.getRow(1);
				String[] laebls = new String[labelRow.getLastCellNum()];
				for(int r =0; r<labelRow.getLastCellNum(); r++) {
					laebls[r] = String.valueOf(labelRow.getCell(r));
				}
				int changedRecords = 0;
				for(int i=3; i<=hssfSheet.getLastRowNum(); i++) {
					errorRow = i;
					HashMap<String, String> data = new HashMap<>();
					data.put("CdmfFmt", parameterNum);
					HSSFRow  row = hssfSheet.getRow(i);
					if(validateRow(row)) {
						for(int r =0; r<row.getLastCellNum();r++) {
							HSSFCell val = row.getCell(r);
							if(val != null && !String.valueOf(val).equalsIgnoreCase("null")) {
								data.put(laebls[r], String.valueOf(val).trim());
							}
						}
						if(template == null) {
							template = service.getParameterXmlTemplate(parameterNum);
						}
						UpdPcdRecRq_Type req = prepareUpdateRequest(data, template, failedItems, reload);
						if(req.getCdmfKeyInfo()!=null) {
							String key =parameterNum+ constructPCDKey(req.getCdmfKeyInfo());
							if(pcdKeys.contains(key)) {
								throw new BadRequestException("Invalid Action - can not perform multiple actions on the same PCD key. "+validationMessgae.append(", row#: ").append(errorRow).append(", key:").append(key));
							}else {
								pcdKeys.add(key);
							}
							requestList.add(req);
							changedRecords++;
						}
					}
				}
				log.debug("Input file :"+file.getOriginalFilename()+" --Processing worksheet: "+worksheetName+" --Num of records in sheet :"+hssfSheet.getLastRowNum() +" --changed records :"+changedRecords);
			}
		}finally {
			if(psetWorkbook!=null) {
				psetWorkbook.close();
			}
		}
		return requestList;
	}

	private boolean validateRow(HSSFRow  row) {
		if(row!=null && row.getCell(0)!=null && !StringUtils.isEmpty(String.valueOf(row.getCell(0))) && 
				(Constants.EXCEL_ACTION_ADD.equalsIgnoreCase(String.valueOf(row.getCell(0))) || 
						Constants.EXCEL_ACTION_CHANGE.equalsIgnoreCase(String.valueOf(row.getCell(0))) ||
						Constants.EXCEL_ACTION_DELETE.equalsIgnoreCase(String.valueOf(row.getCell(0))))) {
			return true;
		}

		return false;
	}
	
	
	private UpdPcdRecRq_Type prepareUpdateRequest(HashMap<String, String> data , PcdXmlRs_Type template, List<String> failedItems, boolean reload) throws SOAPException{
		UpdPcdRecRq_Type req = new UpdPcdRecRq_Type();
		CdmfKeyInfo_Type cdmfKeyInfo = setKeyElements(template.getPcdItemList().getPcdItem(0), data, failedItems, reload);
		if(cdmfKeyInfo!=null) {
			req.setCdmfKeyInfo(cdmfKeyInfo);
			req.setPcdEntry(setPcdEntry(template.getPcdItemList().getPcdItem(0), data));
		}else {
			req.setCdmfKeyInfo(null);
			req.setPcdEntry(null);
		}
		return req;
	}


	private PcdEntry setPcdEntry(PcdItemList_TypePcdItem pcdItemTemplate, HashMap<String, String> data) throws SOAPException {
		PcdEntry req = new PcdEntry();
		PcdEntry template = pcdItemTemplate.getPcdEntry();
		if (template != null) {
			MessageElement[] entryElements = template.get_any();
			MessageElement[]  rElements = new MessageElement[entryElements.length];
			int r= 0;
			for (MessageElement node : entryElements) {
				@SuppressWarnings("unchecked")
				List<MessageElement> childs = node.getChildren();
				MessageElement rNode = new MessageElement();
				rNode.setName(node.getName());
				if (childs != null && !childs.isEmpty() && childs.get(0) instanceof MessageElement) {
					for (MessageElement child : childs) {
						MessageElement rChildNode = new MessageElement();
						rChildNode.setName(child.getName());
						rChildNode.setValue(data.get(child.getName()));
						rNode.addChild(rChildNode);
					}
				} else {
					rNode.setValue(data.get(node.getName()));
				}
				rElements[r] = rNode;
				r++;
			}
			req.set_any(rElements);
		}
		return req;
	}


	private CdmfKeyInfo_Type setKeyElements(PcdItemList_TypePcdItem pcdItemTemplate, HashMap<String, String> data, List<String> failedItems, boolean reload) {

		//Validate and throw error
		validateKeyElements(data);

		CdmfKeyInfo_Type key = new CdmfKeyInfo_Type();
		String action = Constants.EMPTY;
		String keys = Constants.EMPTY;

		if(Constants.EXCEL_ACTION_ADD.equalsIgnoreCase(data.get(Constants.ACTION))) {
			action = Constants.ACTION_ADD;
		}else if(Constants.EXCEL_ACTION_CHANGE.equalsIgnoreCase(data.get(Constants.ACTION))) {
			action = Constants.ACTION_CHANGE;
		}else if(Constants.EXCEL_ACTION_DELETE.equalsIgnoreCase(data.get(Constants.ACTION))) {
			action = Constants.ACTION_DELETE;
		}
		key.setCdmfAction(action);
		key.setCdmfCCNum(Long.parseLong(data.get("CdmfCCNum")));
		if(!StringUtils.isEmpty(data.get("CdmfFmt"))) {
			key.setCdmfFmt(Long.parseLong(data.get("CdmfFmt")));
		}
		if(!StringUtils.isEmpty(data.get("CdmfFmtCoId"))) {
			key.setCdmfFmtCoId(Long.parseLong(data.get("CdmfFmtCoId")));
		}
		key.setCdmfFmtEffDt(data.get("CdmfFmtEffDt"));
		key.setCdmfFmtExpDt(data.get("CdmfFmtExpDt"));
		key.setCdmfOwnerApp(data.get("CdmfOwnerApp"));
		key.setCdmfChgBy(data.get("CdmfChgBy"));
		key.setCdmfChgDt(data.get("CdmfChgDt"));
		key.setCdmfChgTm(data.get("CdmfChgTm"));
		key.setCdmfFmtHighUse(data.get("CdmfFmtHighUse"));

		if(pcdItemTemplate.getCdmfCdkKey()!=null) {
			CdmfCdkKey_Type cdmfCdkKey = new CdmfCdkKey_Type();
			MessageElement[] elements = pcdItemTemplate.getCdmfCdkKey().get_any();
			MessageElement[] rElements = new MessageElement[elements.length];
			keys = processKeyElements(rElements, elements, data, reload);
			cdmfCdkKey.set_any(rElements);
			key.setCdmfCdkKey(cdmfCdkKey);
		}else if(pcdItemTemplate.getCdmfRegKey()!=null) {
			CdmfRegKey_Type cdmfRegKey = new CdmfRegKey_Type();
			MessageElement[] elements = pcdItemTemplate.getCdmfRegKey().get_any();
			MessageElement[] rElements = new MessageElement[elements.length];
			keys = processKeyElements(rElements, elements, data, reload);
			cdmfRegKey.set_any(rElements);
			key.setCdmfRegKey(cdmfRegKey);
		}else if(pcdItemTemplate.getCdmfSimKey()!=null) {
			CdmfSimKey_Type cdmfSimKey = new CdmfSimKey_Type();
			MessageElement[] elements = pcdItemTemplate.getCdmfSimKey().get_any();
			MessageElement[] rElements = new MessageElement[elements.length];
			keys = processKeyElements(rElements, elements, data, reload);
			cdmfSimKey.set_any(rElements);
			key.setCdmfSimKey(cdmfSimKey);
		}
		if(reload && !failedItems.contains(keys)) {
			return null;
		}
		return key;
	}
	
	private void validateKeyElements(HashMap<String, String> data) {
		if(StringUtils.isEmpty(data.get("CdmfCCNum"))) {
			throw new BadRequestException("Invalid CC Number  -"+validationMessgae.append(", row#: ").append(errorRow));
		}

		if(StringUtils.isEmpty(data.get("CdmfFmt"))) {
			throw new BadRequestException("Invalid PCD Number - "+validationMessgae.append(", row#: ").append(errorRow));
		}

		if(StringUtils.isEmpty(data.get("CdmfFmtEffDt")) || !Utils.isValidDate(data.get("CdmfFmtEffDt"))) {
			throw new BadRequestException("Invalid Effective date - "+validationMessgae.append(", row#: ").append(errorRow));
		}

		if(!StringUtils.isEmpty(data.get("CdmfFmtExpDt")) && !Utils.isValidDate(data.get("CdmfFmtExpDt"))) {
				throw new BadRequestException("Invalid Expiry date - "+validationMessgae.append(", row#: ").append(errorRow));
		}

		if(StringUtils.isEmpty(data.get("CdmfOwnerApp"))) {
			throw new BadRequestException("Invalid Owner - "+validationMessgae.append(", row#: ").append(errorRow));
		}
	}

	private String processKeyElements(MessageElement[] rElements, MessageElement[] elements, HashMap<String, String> data, boolean reload) {
		String keys = Constants.EMPTY;
		StringBuffer keyValue = new StringBuffer();
		int r = 0;
		for (MessageElement node : elements) {
			MessageElement rNode = new MessageElement();
			rNode.setName(node.getName());
			rNode.setValue(data.get(node.getName()));
			rElements[r] = rNode;
			r++;
			if(reload) {
				String val = data.get(node.getName());
				if(val!=null && !"null".equalsIgnoreCase(val) && !Constants.EMPTY.equalsIgnoreCase(val)) {
					keyValue.append(val);
					keyValue.append("|");
				}
			}
		}
		if(reload) {
			if(keyValue.length()>0) {
				keys = keyValue.substring(0, keyValue.length()-1);
			}
		}
		return keys;
	}


	private void updateImportTaskStatus(String taskID) {
		List<ImportTaskReviewDetailEntity> reeviewList = importTaskReviewDetailsService.findPsetKeyByImportTaskUUIDAndStatus(String.valueOf(taskID), Constants.FAILED);
		log.debug("Import task Id :"+taskID+"--Failed records: "+ reeviewList.size());
		if(reeviewList.size()==0) {
			ImportTaskEntity entity = new ImportTaskEntity();
			entity.setUuid(taskID);
			entity.setStatus(Constants.COMPLETE);
			entity.setModifiedBy(SystemContext.getUser());
			getDAO().UpdateStatus(entity);
			log.debug("Import task Id :"+taskID+"--Status set to Complete: "+taskID);
		}
	}

	private ImportTaskBean entityToBean(ImportTaskEntity entity) {
		ImportTaskBean bean = new ImportTaskBean();
		bean.setCreatedBy(entity.getCreatedBy());
		bean.setCreatedOn(entity.getCreatedOn());
		bean.setInputFileName(entity.getInputFileName());
		bean.setModifiedBy(entity.getModifiedBy());
		bean.setModifiedOn(entity.getModifiedOn());
		bean.setName(entity.getName());
		bean.setStatus(entity.getStatus());
		bean.setType(entity.getType());
		bean.setUuid(entity.getUuid());
		return bean;
	}


	private ImportTaskEntity beanToEntity(ImportTaskBean bean) {
		ImportTaskEntity entity = new ImportTaskEntity();
		entity.setCreatedBy(bean.getCreatedBy());
		entity.setCreatedOn(bean.getCreatedOn());
		entity.setInputFileName(bean.getInputFileName());
		entity.setModifiedBy(bean.getModifiedBy());
		entity.setModifiedOn(bean.getModifiedOn());
		entity.setName(bean.getName());
		entity.setStatus(bean.getStatus());
		entity.setType(bean.getType());
		entity.setUuid(bean.getUuid());
		return entity;
	}

	private ImportTaskDAO_I getDAO(){
		String region = SystemContext.getRegion();
		if(region.equalsIgnoreCase(Constants.REGION_COR)) {
			return daoCOR;
		}else if(region.equalsIgnoreCase(Constants.REGION_TDA)) {
			return daoTDA;
		}else if(region.equalsIgnoreCase(Constants.REGION_PASCOR)) {
			return daoPASCOR;
		}else if(region.equalsIgnoreCase(Constants.REGION_PASTDA)) {
			return daoPASTDA;
		}{
			throw new SystemException("Invalid region :"+region);
		}
	}
}

