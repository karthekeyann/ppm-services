package com.cft.hogan.platform.ppm.api.facade.mm;

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

import com.cft.hogan.platform.ppm.api.bean.mm.ImportTaskBean;
import com.cft.hogan.platform.ppm.api.config.context.EnvironmentContext;
import com.cft.hogan.platform.ppm.api.dao.mm.ImportTaskDAO_I;
import com.cft.hogan.platform.ppm.api.dao.mm.cor.ImportTaskDAO_COR;
import com.cft.hogan.platform.ppm.api.dao.mm.pascor.ImportTaskDAO_PASCOR;
import com.cft.hogan.platform.ppm.api.dao.mm.pastda.ImportTaskDAO_PASTDA;
import com.cft.hogan.platform.ppm.api.dao.mm.tda.ImportTaskDAO_TDA;
import com.cft.hogan.platform.ppm.api.entity.mm.ImportTaskEntity;
import com.cft.hogan.platform.ppm.api.entity.mm.ImportTaskReviewDetailEntity;
import com.cft.hogan.platform.ppm.api.exception.BadRequestException;
import com.cft.hogan.platform.ppm.api.exception.BusinessException;
import com.cft.hogan.platform.ppm.api.exception.ItemNotFoundException;
import com.cft.hogan.platform.ppm.api.exception.SystemException;
import com.cft.hogan.platform.ppm.api.facade.ParameterFacade;
import com.cft.hogan.platform.ppm.api.pcd.service.client.CdmfCdkKey_Type;
import com.cft.hogan.platform.ppm.api.pcd.service.client.CdmfKeyInfo_Type;
import com.cft.hogan.platform.ppm.api.pcd.service.client.CdmfRegKey_Type;
import com.cft.hogan.platform.ppm.api.pcd.service.client.CdmfSimKey_Type;
import com.cft.hogan.platform.ppm.api.pcd.service.client.PcdEntry;
import com.cft.hogan.platform.ppm.api.pcd.service.client.PcdItemList_TypePcdItem;
import com.cft.hogan.platform.ppm.api.pcd.service.client.PcdXmlRs_Type;
import com.cft.hogan.platform.ppm.api.pcd.service.client.UpdPcdRecRq_Type;
import com.cft.hogan.platform.ppm.api.pcd.service.client.UpdPcdRecRs_Type;
import com.cft.hogan.platform.ppm.api.util.Constants;
import com.cft.hogan.platform.ppm.api.util.PCDService;
import com.cft.hogan.platform.ppm.api.util.Utils;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ImportTaskFacade {

	@Autowired
	private ImportTaskDAO_COR daoCOR;

	@Autowired
	private ImportTaskDAO_TDA daoTDA;

	@Autowired
	private ImportTaskDAO_PASCOR daoPASCOR;

	@Autowired
	private ImportTaskDAO_PASTDA daoPASTDA;

	@Autowired
	private ImportTaskDetailFacade importTaskDetailFacade;

	@Autowired
	private ParameterFacade parameterFacade;

	private StringBuffer validationMessgae = null;
	private int errorRow = 0; 

	public ImportTaskBean save(String taskName, String taskType, MultipartFile file){
		String logMsg =Utils.getLogMsg()+"--";
		log.debug(logMsg+"Processing Import Task request: TaskName-"+taskName+" Tasktype-"+taskType+" File:"+file.getOriginalFilename());
		String uuid = null;
		try {
			PCDService service = new PCDService();
			ImportTaskBean bean = new ImportTaskBean();
			bean.setName(taskName);
			bean.setType(taskType);
			bean.setCreatedBy(Utils.getUserIdInRequestHeader());		
			bean.setModifiedBy(Utils.getUserIdInRequestHeader());	
			bean.setStatus(Constants.INPROGRESS);
			bean.setInputFileName(file.getOriginalFilename());

			List<UpdPcdRecRq_Type> requestList = processWorkBook(file, null, false, service, logMsg);
			if(requestList.size()<1) {
				throw new BadRequestException("NO valid PCD records present in the input file to process");
			}
			uuid = getDAO().save(beanToEntity(bean));
			log.debug(logMsg+"Import task created :"+uuid+" --Total num of records to be udpated :"+requestList.size());
			int endIndex = 0;
			int savedItems = 0;
			int batchSize = EnvironmentContext.getPCDServiceUpdateRecordSize();
			for(int index=0; index < requestList.size();){
				endIndex  = index+batchSize;
				if(endIndex > requestList.size()) {
					endIndex = requestList.size();
				}
				savedItems += processUpdate(requestList.subList(index, endIndex), service, uuid);
				index = endIndex;
			}
			log.debug(logMsg+"Import task ID :"+uuid+" --Import Task Review Details updated records :"+savedItems);
			updateImportTaskStatus(uuid, logMsg);
		}catch(Exception e) {
			Utils.handleException(e);
		}
		return findByUUID(uuid);
	}


	public ImportTaskBean findByUUID(String taskId) {
		ImportTaskBean bean = null;
		try {
			bean = entityToBean(getDAO().findByUUID(taskId));
			bean.setImportTaskReviewDetails(importTaskDetailFacade.findByImportTaskUUID(taskId));
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
			if(!Utils.getUserIdInRequestHeader().equalsIgnoreCase(importTask.getCreatedBy())) {
				throw new BadRequestException("Import task can be deleted by task creator/owner");
			}

			List<ImportTaskReviewDetailEntity> taskDetailsList = importTaskDetailFacade.findPsetKeyByImportTaskUUIDAndStatus(taskId, Constants.SUCCESS);
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
			String logMsg = Utils.getLogMsg();
			List<ImportTaskReviewDetailEntity> resultSet = importTaskDetailFacade.findPsetKeyByImportTaskUUIDAndStatus(taskUUID, Constants.FAILED);
			log.debug(logMsg+"Import task ID :"+taskUUID+" --No of failed records retrieved for import task"+taskUUID +"  :"+ resultSet.size());
			if(resultSet!=null && resultSet.size()>0) {
				PCDService service = new PCDService();
				List<String> failedItems = new ArrayList<>();
				resultSet.forEach((result)->{
					failedItems.add(result.getPSetKey());
				});
				List<UpdPcdRecRq_Type> requestList = processWorkBook(file, failedItems, true, service, logMsg); 
				int endIndex = 0;
				int updatedItems = 0;
				int batchSize = EnvironmentContext.getPCDServiceUpdateRecordSize();
				for(int index=0; index < requestList.size();){
					endIndex  = index+batchSize;
					if(endIndex > requestList.size()) {
						endIndex = requestList.size();
					}
					updatedItems += processReloadUpdate(requestList.subList(index, endIndex), service, taskUUID);
					index = endIndex;
				}
				log.debug(logMsg+"Import task ID :"+taskUUID+" --Import Task Review Details updated records :"+updatedItems);
			}
			updateImportTaskStatus(taskUUID, logMsg);
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
					importTaskReviewDetail.setModifiedBy(Utils.getUserIdInRequestHeader());
					try {
						importTaskDetailFacade.Update(importTaskReviewDetail);
					} catch (Exception e) {
						StringBuffer message = new StringBuffer();
						message.append("Import task ID :").append(taskUUID);
						message.append(" --Error in update Import task review details --Key :").append(key);
						message.append(" StatusDesc :").append(response.getXStatus().getStatusDesc());
						message.append(" StatusCode :").append(response.getXStatus().getStatusCode());
						throw new BusinessException(message.toString(), true); 
					}
				}catch(Exception e) {
					Utils.handleException(e);
				}
			}
		});
		return responseList.size();
	}


	private int processUpdate(List<UpdPcdRecRq_Type> requestList, PCDService service, String uuid) throws RemoteException, Exception {

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
				input.setPSetName(parameterFacade.getParameterName(response.getCdmfKeyInfo().getCdmfOwnerApp(), String.valueOf(response.getCdmfKeyInfo().getCdmfFmt())));
				input.setPSetNumber(String.valueOf(response.getCdmfKeyInfo().getCdmfFmt()));
				input.setResult(response.getXStatus().getStatusDesc());
				input.setCreatedBy(Utils.getUserIdInRequestHeader());
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
					throw new BusinessException("Import task ID :"+uuid+" --Error occured prepare ImportTaskReviewDetailEntity PCD#/KEY :"+String.valueOf(response.getCdmfKeyInfo().getCdmfFmt())+"/"+key, true);
				}
			}catch(Exception e) {
				Utils.handleException(e);
			}
		});
		return importTaskDetailFacade.save(reviewList);
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


	private List<UpdPcdRecRq_Type>  processWorkBook(MultipartFile file, List<String> failedItems, boolean reload, PCDService service, String logMsg) throws Exception {
		List<UpdPcdRecRq_Type> requestList = new ArrayList<UpdPcdRecRq_Type>();
		HSSFWorkbook psetWorkbook = new HSSFWorkbook(file.getInputStream());
		try {
			int sheetCount = psetWorkbook.getNumberOfSheets();
			List<String> pcdKeys = new ArrayList<>();
			log.debug(logMsg+"Input file :"+file.getOriginalFilename()+" --No of sheets in the workbook: "+sheetCount);
			for(int sheetIndex = 0; sheetIndex<sheetCount; sheetIndex++) { 
				validationMessgae = new StringBuffer();
				validationMessgae.append("Workbook: ").append(file.getOriginalFilename());
				HSSFSheet hssfSheet = psetWorkbook.getSheetAt(sheetIndex);
				String worksheetName = hssfSheet.getSheetName();
				validationMessgae.append(", Sheet: ").append(worksheetName);
				log.debug(logMsg+"Input file :"+file.getOriginalFilename()+" --Processing worksheet: "+worksheetName);

				HSSFRow header = hssfSheet.getRow(0);
				String[] head = String.valueOf(header.getCell(0)).split("-");
				String parameterNum = head[0];

				PcdXmlRs_Type template = null;
				HSSFRow  labelRow = hssfSheet.getRow(1);
				String[] laebls = new String[labelRow.getLastCellNum()];
				boolean isRepeatingParameter = false;
				for(int r =0; r<labelRow.getLastCellNum(); r++) {
					laebls[r] = String.valueOf(labelRow.getCell(r));
					if(laebls[r].contains(Constants.UNDER_SCORE)) {
						isRepeatingParameter = true;
					}
				}
				int changedRecords = 0;
				UpdPcdRecRq_Type req = null;
				for(int i=3; i<=hssfSheet.getLastRowNum(); i++) {
					errorRow = i+1;
					HashMap<String, String> nonRepeatingDataMap = new HashMap<>();
					List<HashMap<String, String>> repeatingDataList = new ArrayList<>();
					nonRepeatingDataMap.put("CdmfFmt", parameterNum);
					HSSFRow  row = hssfSheet.getRow(i);
					if(isChangedRow(row)) {
						validateKeyElements(row, parameterNum);
						HashMap<String, String> repeatingDataMap = new HashMap<>();
						if(!Constants.PCD_2598.equalsIgnoreCase(parameterNum)) {
							repeatingDataList.add(repeatingDataMap);
						}
						prepareDataMap(row, laebls, nonRepeatingDataMap, repeatingDataMap, false);

						if(isRepeatingParameter) {
							for(int j = i+1; isRepeatingRow(hssfSheet.getRow(j)) ; j++) {
								repeatingDataMap = new HashMap<>();
								if(Constants.PCD_2598.equalsIgnoreCase(parameterNum) && j ==i+1) {
									prepareDataMap(row, laebls, nonRepeatingDataMap, repeatingDataMap, true);
								}
								i++;
								repeatingDataList.add(repeatingDataMap);
								prepareDataMap(hssfSheet.getRow(j), laebls, nonRepeatingDataMap, repeatingDataMap, true);
							}
						}

						if(template == null) {
							template = service.getParameterXmlTemplate(parameterNum);
						}
						req = prepareUpdateRequest(nonRepeatingDataMap, repeatingDataList, template, failedItems, reload);
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
				log.debug(logMsg+"Input file :"+file.getOriginalFilename()+" --Processing worksheet: "+worksheetName+" --Num of records in sheet :"+hssfSheet.getLastRowNum() +" --changed records :"+changedRecords);
			}
		}finally {
			if(psetWorkbook!=null) {
				psetWorkbook.close();
			}
		}
		return requestList;
	}

	private void prepareDataMap(HSSFRow row, String[] laebls, Map<String, String> nonRepeatingDataMap, Map<String, String> repeatingDataMap, boolean isRepeatingRow) {
		for(int cellIndex =0; cellIndex<row.getLastCellNum();cellIndex++) {
			HSSFCell val = row.getCell(cellIndex);
			if(val != null && !String.valueOf(val).equalsIgnoreCase("null") && !StringUtils.isEmpty(String.valueOf(val))) {
				if(laebls[cellIndex].contains(Constants.UNDER_SCORE))
				{
					repeatingDataMap.put(laebls[cellIndex], String.valueOf(val).trim());
				}else if(!isRepeatingRow){
					nonRepeatingDataMap.put(laebls[cellIndex], String.valueOf(val).trim());					
				}
			}
		}
	}


	private boolean isChangedRow(HSSFRow  row) {
		if(row!=null && row.getCell(0)!=null && !StringUtils.isEmpty(String.valueOf(row.getCell(0))) && 
				(Constants.EXCEL_ACTION_ADD.equalsIgnoreCase(String.valueOf(row.getCell(0))) || 
						Constants.EXCEL_ACTION_CHANGE.equalsIgnoreCase(String.valueOf(row.getCell(0))) ||
						Constants.EXCEL_ACTION_DELETE.equalsIgnoreCase(String.valueOf(row.getCell(0))))) {
			return true;
		}

		return false;
	}
	
	private void validateKeyElements(HSSFRow  row, String parameterNum) {
		if(StringUtils.isEmpty(String.valueOf(row.getCell(1)))) {
			throw new BadRequestException("Invalid CompanyId  -"+validationMessgae.append(", row#: ").append(errorRow));
		}
		
		if(StringUtils.isEmpty(String.valueOf(row.getCell(2))) || !Utils.isValidDate(String.valueOf(row.getCell(2)))) {
			throw new BadRequestException("Invalid Effective date - "+validationMessgae.append(", row#: ").append(errorRow));
		}

		if(!StringUtils.isEmpty(String.valueOf(row.getCell(3))) && !Utils.isValidDate(String.valueOf(row.getCell(3)))) {
			throw new BadRequestException("Invalid Expiry date - "+validationMessgae.append(", row#: ").append(errorRow));
		}

		if(StringUtils.isEmpty(String.valueOf(row.getCell(4))) || String.valueOf(row.getCell(4)).length()<3) {
			throw new BadRequestException("Invalid OwnerId - "+validationMessgae.append(", row#: ").append(errorRow));
		}
		
		if(StringUtils.isEmpty(String.valueOf(row.getCell(6)))) {
			throw new BadRequestException("Invalid CC Number  -"+validationMessgae.append(", row#: ").append(errorRow));
		}
		
		//validate the presence of parameter in the application file
		parameterFacade.getParameterName(String.valueOf(row.getCell(4)), parameterNum);
	}
	
	private boolean isRepeatingRow(HSSFRow  row) {
		if(row!=null && StringUtils.isEmpty(String.valueOf(row.getCell(1))) &&
				StringUtils.isEmpty(String.valueOf(row.getCell(2))) && StringUtils.isEmpty(String.valueOf(row.getCell(3)))) {
			return true;
		}

		return false;
	}


	private UpdPcdRecRq_Type prepareUpdateRequest(HashMap<String, String> nonRepeatingDataMap , List<HashMap<String, String>> repeatingDataList, PcdXmlRs_Type template, List<String> failedItems, boolean reload) throws SOAPException{
		UpdPcdRecRq_Type req = new UpdPcdRecRq_Type();
		CdmfKeyInfo_Type cdmfKeyInfo = setKeyElements(template.getPcdItemList().getPcdItem(0), nonRepeatingDataMap, failedItems, reload);
		if(cdmfKeyInfo!=null) {
			req.setCdmfKeyInfo(cdmfKeyInfo);
			req.setPcdEntry(setPcdEntry(template.getPcdItemList().getPcdItem(0), nonRepeatingDataMap, repeatingDataList));
		}else {
			req.setCdmfKeyInfo(null);
			req.setPcdEntry(null);
		}
		return req;
	}

	@SuppressWarnings("unchecked")
	private PcdEntry setPcdEntry(PcdItemList_TypePcdItem pcdItemTemplate, HashMap<String, String> nonRepeatingDataMap, List<HashMap<String, String>> repeatingDataList) throws SOAPException {
		PcdEntry req = new PcdEntry();
		PcdEntry template = pcdItemTemplate.getPcdEntry();
		if (template != null) {
			MessageElement[] parentTemplateElements = template.get_any();
			ArrayList<MessageElement> parentDataElementsList = new ArrayList<>();

			for (MessageElement parentTemplateElement : parentTemplateElements) {
				MessageElement parentDataElement = null;

				List<MessageElement> childTemplateElements = parentTemplateElement.getChildren();
				if (childTemplateElements != null && !childTemplateElements.isEmpty() && childTemplateElements.get(0) instanceof MessageElement) {
					for(int i=0; i < repeatingDataList.size(); i++){
						HashMap<String, String> repeatingDataMaplevel1 = repeatingDataList.get(i);

						/*
						 * to skip the duplicate elements of BalData
						 */
						if(parentTemplateElement.getName().startsWith("BalData")) {
							i = repeatingDataList.size();
						}
						parentDataElement = new MessageElement();
						parentDataElement.setName(parentTemplateElement.getName());
						parentDataElementsList.add(parentDataElement);

						for (MessageElement childTemplateElement : childTemplateElements) {
							MessageElement childDataElement = null;

							List<MessageElement> grandChildTemplateElements = childTemplateElement.getChildren();
							if (grandChildTemplateElements != null && !grandChildTemplateElements.isEmpty() && grandChildTemplateElements.get(0) instanceof MessageElement) {
								for(int i2= 0; i2 < repeatingDataList.size() ; i2++){
									HashMap<String, String> repeatingDataMaplevel2 = repeatingDataList.get(i2);
									childDataElement = new MessageElement();
									childDataElement.setName(childTemplateElement.getName());
									parentDataElement.addChild(childDataElement);

									for (MessageElement grandChildTemplateElement : grandChildTemplateElements) {
										MessageElement grandChildDataElement = new MessageElement();
										String elementName = parentTemplateElement.getName()+Constants.UNDER_SCORE+childTemplateElement.getName()+Constants.UNDER_SCORE+grandChildTemplateElement.getName();
										grandChildDataElement.setName(grandChildTemplateElement.getName());
										if(!StringUtils.isEmpty(repeatingDataMaplevel2.get(elementName))) {
											grandChildDataElement.setValue(repeatingDataMaplevel2.get(elementName));
										}else {
											grandChildDataElement.setValue(Constants.EMPTY);
										}
										childDataElement.addChild(grandChildDataElement);
									}

								}
							} else {
								String elementName = parentTemplateElement.getName()+Constants.UNDER_SCORE+childTemplateElement.getName();
								if(!StringUtils.isEmpty(repeatingDataMaplevel1.get(elementName))) {
									childDataElement = new MessageElement();
									childDataElement.setName(childTemplateElement.getName());
									childDataElement.setValue(repeatingDataMaplevel1.get(elementName));
									parentDataElement.addChild(childDataElement);
								}
							}
						}

					}
				} else {
					if(!StringUtils.isEmpty(nonRepeatingDataMap.get(parentTemplateElement.getName()))) {
						parentDataElement = new MessageElement();
						parentDataElement.setName(parentTemplateElement.getName());
						parentDataElement.setValue(nonRepeatingDataMap.get(parentTemplateElement.getName()));
						parentDataElementsList.add(parentDataElement);
					}
				}
			}
			req.set_any(parentDataElementsList.toArray(new MessageElement[parentDataElementsList.size()]));
		}
		return req;
	}


	private CdmfKeyInfo_Type setKeyElements(PcdItemList_TypePcdItem pcdItemTemplate, HashMap<String, String> data, List<String> failedItems, boolean reload) {

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


	private void updateImportTaskStatus(String taskID, String logMsg) {
		List<ImportTaskReviewDetailEntity> reeviewList = importTaskDetailFacade.findPsetKeyByImportTaskUUIDAndStatus(String.valueOf(taskID), Constants.FAILED);
		log.debug(logMsg+"Import task Id :"+taskID+"--Failed records: "+ reeviewList.size());
		if(reeviewList.size()==0) {
			ImportTaskEntity entity = new ImportTaskEntity();
			entity.setUuid(taskID);
			entity.setStatus(Constants.COMPLETE);
			entity.setModifiedBy(Utils.getUserIdInRequestHeader());
			getDAO().UpdateStatus(entity);
			log.debug(logMsg+"Import task Id :"+taskID+"--Status set to Complete: "+taskID);
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
		String region = Utils.getRegion();
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

