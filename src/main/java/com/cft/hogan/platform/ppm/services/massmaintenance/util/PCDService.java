package com.cft.hogan.platform.ppm.services.massmaintenance.util;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.axis.message.MessageElement;
import org.springframework.util.StringUtils;

import com.cft.hogan.platform.ppm.services.config.context.SystemContext;
import com.cft.hogan.platform.ppm.services.massmaintenance.bean.CompanyBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.bean.ParameterBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.SystemException;
import com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfCdkKey_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfRegKey_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.CeleritiPcd_PortType;
import com.cft.hogan.platform.ppm.services.pcd.service.client.CeleritiPcd_ServiceLocator;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItem;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItemPcdItemKey;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdXmlRq_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdXmlRs_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.Status_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.UpdPcdRecRq_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.UpdPcdRecRs_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.UpdatePcdRq_Type;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PCDService {

	private CeleritiPcd_PortType port = null;

	public PcdXmlRs_Type getParameterXmlTemplate(String parameterNum) throws Exception {
		String key = SystemContext.getRegion()+parameterNum;
		HashMap<String, PcdXmlRs_Type> xmlTemplatesMap = SystemContext.getXMLTemplatesMap();
		if(xmlTemplatesMap.containsKey(key)) {
			log.debug("PCD XML Template retrieved from cache :"+key);
			return xmlTemplatesMap.get(key);
		}
		PcdXmlRq_Type pcdXmlRq = new PcdXmlRq_Type();
		CdmfKeyInfo_Type cdmfKeyInfo = new CdmfKeyInfo_Type();
		CdmfRegKey_Type cdmfRegKey = new CdmfRegKey_Type();

		MessageElement[] eleArray = new MessageElement[1];
		MessageElement ele = new MessageElement();
		ele.setName(Constants.CDMF_FMT_ID);
		ele.setValue(parameterNum);
		eleArray[0]=ele;
		cdmfRegKey.set_any(eleArray);
		cdmfKeyInfo.setCdmfFmt(830100);
		cdmfKeyInfo.setCdmfAction("INQXML");
		cdmfKeyInfo.setCdmfRegKey(cdmfRegKey);
		pcdXmlRq.setCdmfKeyInfo(cdmfKeyInfo);
		PcdXmlRs_Type response = getPCDService().processPcd(pcdXmlRq);
		if(!"0".equals(String.valueOf(response.getXStatus().getStatusCode()))) {
			throw new SystemException("Error in PCD service- XML template not retrieved: PCD#"+parameterNum+" Error-"+response.getXStatus().getStatusDesc());
		}
		xmlTemplatesMap.put(key, response);
		log.debug("PCD XML Template retrieved from service :"+key);
		return response;
	}

	public PcdXmlRs_Type retrievePCDItems(ParameterBean parameterBean) throws Exception {
		log.debug("Retrieve PCD details PCD#: "+parameterBean.getNumber() + " CompanyID: "+parameterBean.getCompanyID() +
				" ApplicationID: "+parameterBean.getApplicationID()	+ " EffectiveDate: "+parameterBean.getEffectiveDate());
		PcdXmlRs_Type response = new PcdXmlRs_Type();
		List<PcdItemList_TypePcdItem> allRecords = new ArrayList<>();
		PcdXmlRq_Type pcdXmlRq = new PcdXmlRq_Type();
		CdmfKeyInfo_Type cdmfKeyInfo = new CdmfKeyInfo_Type();

		cdmfKeyInfo.setCdmfFmt(Integer.parseInt(parameterBean.getNumber()));
		cdmfKeyInfo.setCdmfAction("GETALL");
		if(parameterBean.getCompanyID()!=null && !StringUtils.isEmpty(parameterBean.getCompanyID())) {
			cdmfKeyInfo.setCdmfFmtCoId(Long.parseLong(parameterBean.getCompanyID()));
		}
		
		if(parameterBean.getEffectiveDate()!=null && Utils.isValidDate(parameterBean.getEffectiveDate())) {
			cdmfKeyInfo.setCdmfFmtEffDt(parameterBean.getEffectiveDate());
		}
		
		if ("Cards".equals(parameterBean.getApplicationID())) {
			log.debug("Setting request for Cards");
			CdmfCdkKey_Type cdmfCdkKey = new CdmfCdkKey_Type();
			MessageElement[] eleArray = new MessageElement[1];
			MessageElement ele = new MessageElement();
			ele.setName("CdkSubsys");
			ele.setValue("*");
			eleArray[0]=ele;
			cdmfCdkKey.set_any(eleArray);
			cdmfKeyInfo.setCdmfCdkKey(cdmfCdkKey);
		}

		pcdXmlRq.setCdmfKeyInfo(cdmfKeyInfo);

		CeleritiPcd_PortType service = getPCDService();
		PcdXmlRs_Type pcdXmlRs = service.processPcd(pcdXmlRq);
		if ("0".equals(String.valueOf(pcdXmlRs.getXStatus().getStatusCode()))) {
			PcdItemList_TypePcdItem[] pcdItemList = pcdXmlRs.getPcdItemList().getPcdItem();

			if(pcdItemList!=null) {
				mergePCDItems(allRecords, pcdItemList);
				while ("Y".equalsIgnoreCase(pcdXmlRs.getPcdItemList().getMoreItem())) {
					log.debug("Continue for more Items PCD#: "+parameterBean.getNumber() + " CompanyID: "+parameterBean.getCompanyID() +
							" ApplicationID: "+parameterBean.getApplicationID()	+ " EffectiveDate: "+parameterBean.getEffectiveDate());
					pcdXmlRs = service.processPcd(getNextItemsRequest(pcdXmlRs, pcdXmlRq));
					if ("0".equals(String.valueOf(pcdXmlRs.getXStatus().getStatusCode()))) {
						if (pcdXmlRs.getPcdItemList() != null	&& pcdXmlRs.getPcdItemList().getPcdItem() != null) {
							pcdItemList = pcdXmlRs.getPcdItemList().getPcdItem();
							mergePCDItems(allRecords, pcdItemList);
						} else {
							break;
						}
					} else {
						break;
					}
				}
			}
		}else {
			throw new SystemException("Error in PCD service- PCD details not retrieved: status-"+response.getXStatus().getStatusDesc());
		}
		response.setPcdItemList(new PcdItemList_Type(allRecords.toArray(new PcdItemList_TypePcdItem[allRecords.size()]), "N", String.valueOf(allRecords.size())));
		response.setXStatus(getSuccessStatus());
		return response;
	}

	public UpdPcdRecRs_Type[] updatePcd(List<UpdPcdRecRq_Type> requestList) throws RemoteException, Exception {
		UpdatePcdRq_Type updatePcdRq = new UpdatePcdRq_Type();
		UpdPcdRecRs_Type[] response = null;
		updatePcdRq.setUpdPcdRecRq(requestList.toArray(new UpdPcdRecRq_Type[requestList.size()]));
		response = getPCDService().updatePcd(updatePcdRq);
		return response;
	}


	public HashMap<String, List<CompanyBean>> getCompanyDetails() throws RemoteException, Exception {
		PcdXmlRq_Type pcdXmlRq = new PcdXmlRq_Type();
		CdmfKeyInfo_Type cdmfKeyInfo = new CdmfKeyInfo_Type();
		cdmfKeyInfo.setCdmfFmt(48010);
		cdmfKeyInfo.setCdmfAction("GETALL");
		pcdXmlRq.setCdmfKeyInfo(cdmfKeyInfo);
		PcdXmlRs_Type pcdXmlRs = getPCDService().processPcd(pcdXmlRq);
		return processXmlResponse(pcdXmlRs);
	}

	private void mergePCDItems(List<PcdItemList_TypePcdItem> oldData, PcdItemList_TypePcdItem[] pcdItemList) {
		for (PcdItemList_TypePcdItem newItem : pcdItemList)
			oldData.add(newItem);
	}

	private Status_Type getSuccessStatus() {
		Status_Type xStatus = new Status_Type();
		xStatus.setStatusCode(0);
		xStatus.setServerStatusCode("0");
		xStatus.setSeverity("Info");
		xStatus.setStatusDesc(Constants.ACTION_SUCCESSFUL);
		return xStatus;
	}

	private PcdXmlRq_Type getNextItemsRequest(PcdXmlRs_Type pcdXMLRs, PcdXmlRq_Type pcdXmlRq) {
		CdmfKeyInfo_Type newKey = pcdXmlRq.getCdmfKeyInfo();
		int lastItem = pcdXMLRs.getPcdItemList().getPcdItem().length -1;
		PcdItemList_TypePcdItemPcdItemKey lastItemKey = pcdXMLRs.getPcdItemList().getPcdItem(lastItem).getPcdItemKey(0);
		newKey.setCdmfAction("NXTALL");
		newKey.setCdmfFmtCoId(lastItemKey.getCdmfFmtCoId());
		newKey.setCdmfFmtEffDt(lastItemKey.getCdmfFmtEffDt());
		newKey.setCdmfFmtExpDt(lastItemKey.getCdmfFmtExpDt());
		newKey.setCdmfCdkKey(lastItemKey.getCdmfCdkKey());
		newKey.setCdmfRegKey(lastItemKey.getCdmfRegKey());
		newKey.setCdmfSimKey(lastItemKey.getCdmfSimKey());
		return pcdXmlRq;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, List<CompanyBean>> processXmlResponse(PcdXmlRs_Type pcdXmlRs) {
		PcdItemList_Type pcdItemList = pcdXmlRs.getPcdItemList();
		HashMap<String, List<CompanyBean>> companiesMap = new HashMap<>();
		List<CompanyBean> companyList = new ArrayList<CompanyBean>();
		int count = Integer.parseInt(pcdItemList.getItemCnt());
		for (int i = 0; i < count; i++) {
			if(pcdItemList.getPcdItem(i)!=null &&
					pcdItemList.getPcdItem(i).getPcdItemKey(0) != null &&
					pcdItemList.getPcdItem(i).getPcdItemKey(0).getCdmfRegKey() != null &&
					pcdItemList.getPcdItem(i).getPcdItemKey(0).getCdmfRegKey().get_any() != null &&
					pcdItemList.getPcdItem(i).getPcdItemKey(0).getCdmfRegKey().get_any()[0] != null) {
				String pcdId = pcdItemList.getPcdItem(i).getPcdItemKey(0).getCdmfRegKey().get_any()[0].getValue();
				if(pcdId != null && !StringUtils.isEmpty(pcdId)) {
					companyList = new ArrayList<CompanyBean>();
					List<MessageElement> childs = pcdItemList.getPcdItem(i).getPcdData().get_any()[0].getChildren();
					if(childs.size()>1) {
						int noOfcompanies = childs.size()/2;
						for(int c=0; c < noOfcompanies; c++) {
							CompanyBean bean = new CompanyBean();
							bean.setId(childs.get(c).getValue());
							bean.setName(childs.get(c).getValue()+" "+childs.get(noOfcompanies+c).getValue());
							companyList.add(bean);
						}
						CompanyBean bean = new CompanyBean();
						bean.setId("all");
						bean.setName("All");
						companyList.add(bean);
					}
					companiesMap.put(pcdId, companyList);
				}
			}
		}
		return companiesMap;
	}

	private CeleritiPcd_PortType getPCDService() throws IOException, ServiceException {
		if(port==null) {
			URL portAddress = new URL(SystemContext.getEndPoint());
			CeleritiPcd_ServiceLocator service = new CeleritiPcd_ServiceLocator();
			port = service.getCeleritiPcd(portAddress);
		}
		return port;
	}
}
