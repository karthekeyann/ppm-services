package com.cft.hogan.platform.ppm.api.util;


public abstract class Constants {

	public static final String EMPTY = "";
	public static final String SPACE = " ";
	public static final String UNDER_SCORE = "_";
	
	
	public static final int PAD_LIMIT = 8192;
	public static final String PCD_2598 = "2598";
	
	public static final String CDMF_FMT_COID = "CdmfFmtCoId";
	public static final String PCD_EFF_DATE = "CdmfFmtEffDt";
	public static final String PCD_EXP_DATE = "CdmfFmtExpDt";
	public static final String CDMF_OWNER_APP = "CdmfOwnerApp";
	public static final String CDMF_HIGH_USE_FLAG = "CdmfFmtHighUse";
	public static final String CDMF_CC_NUM = "CdmfCCNum";
	public static final String CDMF_CHG_TM = "CdmfChgTm";
	public static final String CDMF_CHG_DT = "CdmfChgDt";
	public static final String CDMF_CHG_BY = "CdmfChgBy";
	public static final String CDMFCDKKEY = "CdmfCdkKey";
	public static final String CDMF_ACTION = "CdmfAction";
	public static final String CDMF_FMT_ID = "CdmfFmtId";
	
	public static final String BRP_OWNER_CARD = "Cards";
    public static final String NS = "http://MessageView/";
    public static final String NS_PREFIX = "mes";
    
    
	public static final String ACTION = "Action";
	public static final String ST_CODE = "StatusCode";
	public static final String ACTION_SUCCESSFUL = "ACTION SUCCESSFUL";
	
	public static final String EXCEL_ACTION_ADD = "Add";
	public static final String EXCEL_ACTION_DELETE = "Delete";
	public static final String EXCEL_ACTION_CHANGE = "Change";
		
	public static final String ACTION_ADD = "ADD";
	public static final String ACTION_DELETE = "DEL";
	public static final String ACTION_CHANGE = "CHG";
	
    public static final String SUCCESS = "Success";
    public static final String FAILED = "Failed";
	public static final String COMPLETE = "Complete";
	public static final String INPROGRESS = "InProgress";
	public static final String CANCELLED = "Cancelled";
	
	public static final String DATASOURCE_COR = "DATASOURCE_COR";
	public static final String DATASOURCE_TDA = "DATASOURCE_TDA";
	public static final String DATASOURCE_PASCOR = "DATASOURCE_PASCOR";
	public static final String DATASOURCE_PASTDA = "DATASOURCE_PASTDA";
	
	public static final String PACKAGE_ENTITIES = "com.cft.hogan.platform.ppm.api.entity.mm";
	
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	
	public static final String PCD_SERVICE_ENDPOINT_TEST = "./config/system/pcd-service-endpoint-test.properties";
	public static final String PCD_SERVICE_ENDPOINT_QA = "./config/system/pcd-service-endpoint-qa.properties";
	public static final String PCD_SERVICE_ENDPOINT_PROD = "./config/system/pcd-service-endpoint-prod.properties";
	public static final String DATA_SOURCE_PROP_FILE_TEST = "./config/system/datasource-test.properties";
	public static final String DATA_SOURCE_PROP_FILE_QA = "./config/system/datasource-qa.properties";
	public static final String DATA_SOURCE_PROP_FILE_PROD = "./config/system/datasource-prod.properties";
	public static final String LABELS_PROP_PATH = "ppm.labels.base.path";
	public static final String PARMETER_CONFIG_PATH = "ppm.param.base.path";
	
	public static final String ENV_TEST = "TEST";
	public static final String ENV_QA = "QA";
	public static final String ENV_PROD = "PROD";
	
	public static final String REGION_COR = "COR";
	public static final String REGION_TDA = "TDA";
	public static final String REGION_PASCOR = "PASCOR";
	public static final String REGION_PASTDA = "PASTDA";
	
	public static final String ACTIVE = "ACTIVE";
	public static final String IN_PROGRESS = "IN PROGRESS";
	
	public static final String IMPORT = "IMPORT";
	public static final String EXPORT = "EXPORT";
}
