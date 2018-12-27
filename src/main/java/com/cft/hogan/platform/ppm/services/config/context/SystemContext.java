package com.cft.hogan.platform.ppm.services.config.context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cft.hogan.platform.ppm.services.massmaintenance.bean.ApplicationBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.bean.CompanyBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.bean.ParameterBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.SystemException;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Constants;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdXmlRs_Type;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SystemContext {

	//env
	private static String environment = null;

	//Service end point
	private static String pcd_service_endpoint_cor = null;
	private static String pcd_service_endpoint_tda = null;
	private static String pcd_service_endpoint_pascor = null;
	private static String pcd_service_endpoint_pastda = null;

	//pcd service update records
	private static int pcd_service_batch_update_size  = 1;

	//db batch update 
	private static int database_batch_update_size  = 1;

	private static Properties datasource = null;
	
	private static HashMap<String, List<ApplicationBean>> applicationsMap = new HashMap<>();
	
	private static HashMap<String, List<ParameterBean>> parametersMap = new HashMap<>();
	
	private static HashMap<String, HashMap<String, List<CompanyBean>>> companiesMap = new HashMap<>();
	
	private static HashMap<String, PcdXmlRs_Type> xmlTemplatesMap = new HashMap<>();
	
	
	public static String getEnvironment(){
		return environment;
	}
	
	
	public static HashMap<String, PcdXmlRs_Type> getXMLTemplatesMap(){
		return xmlTemplatesMap;
	}
	
	
	public static HashMap<String, HashMap<String, List<CompanyBean>>> getCompaniesMap(){
		return companiesMap;
	}
	
	public static HashMap<String, List<ApplicationBean>> getApplicationssMap(){
		return applicationsMap;
	}
	
	public static HashMap<String, List<ParameterBean>> getParametersMap(){
		return parametersMap;
	}

	public static void loadPropertyContext(String[] args) throws IOException{
		if(args.length==0 || StringUtils.isEmpty(args[0]) || 
				!(Constants.ENV_TEST.equalsIgnoreCase(args[0]) || Constants.ENV_QA.equalsIgnoreCase(args[0]) ||Constants.ENV_PROD.equalsIgnoreCase(args[0])) ) {
			throw new SystemException("Invalid argument #1(Environment) :"+args[0]);
		}else {
			environment = args[0];
		}
		readDataSourceProperties();
		readPCDServiceProperties();
	}

	public static Properties getDataSourceProperties() throws IOException {
		return datasource;
	}

	public static int getPCDServiceBatchUpdateSize() {

		return pcd_service_batch_update_size;
	}
	
	public static int getDataBaseBatchUpdateSize() {

		return database_batch_update_size;
	}

	public static String getEndPoint() throws IOException {
		String region = SystemContext.getRegion();
		String endPoint = null;
		if(region.equalsIgnoreCase(Constants.REGION_COR)) {
			endPoint = pcd_service_endpoint_cor;
		}else if(region.equalsIgnoreCase(Constants.REGION_TDA)) {
			endPoint = pcd_service_endpoint_tda;
		}else if(region.equalsIgnoreCase(Constants.REGION_PASCOR)) {
			endPoint = pcd_service_endpoint_pascor;
		}else if(region.equalsIgnoreCase(Constants.REGION_PASTDA)) {
			endPoint = pcd_service_endpoint_pastda;
		}
		if(endPoint==null || StringUtils.isEmpty(endPoint)) {
			throw new SystemException("Invalid PCD Service Endpoint :"+endPoint);
		}
		return endPoint;
	}
	
	public static String getRegion() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String region = request.getHeader("X-region");
		if(region==null || StringUtils.isEmpty(region) || 
				!(region.equalsIgnoreCase(Constants.REGION_COR) || region.equalsIgnoreCase(Constants.REGION_TDA) || 
						region.equalsIgnoreCase(Constants.REGION_PASCOR) || region.equalsIgnoreCase(Constants.REGION_PASTDA))) {
			throw new SystemException("Invalid request header - X-region :"+region);
		}
		return region;
	}
	
	public static String getUser() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String user = request.getHeader("X-user");
		if(user==null || StringUtils.isEmpty(user)) {
			throw new SystemException("Invalid request header - X-user missing");
		}
		return user;
	}

	private static void readDataSourceProperties() throws IOException {
		if(datasource==null) {
			datasource = new Properties();
		}
		File file = null;

		if(environment.equalsIgnoreCase(Constants.ENV_TEST)) {
			file = new File(Constants.DATA_SOURCE_PROP_FILE_TEST);
		}else if(environment.equalsIgnoreCase(Constants.ENV_QA)) {
			file = new File(Constants.DATA_SOURCE_PROP_FILE_QA);
		}else if(environment.equalsIgnoreCase(Constants.ENV_PROD)) {
			file = new File(Constants.DATA_SOURCE_PROP_FILE_PROD);
		}

		BufferedReader inputStream = null;
		try {
			if(file.exists()) {
				inputStream = new BufferedReader(new FileReader(file));
				datasource.load(inputStream);
				if(datasource.getProperty("database.batch.update.size") !=null) {
					database_batch_update_size = Integer.parseInt(datasource.getProperty("database.batch.update.size"));
				}
			}else {
				throw new SystemException("Datasource properties file does not exists: "+file.getAbsolutePath());
			}
		}finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
	
	private static void readPCDServiceProperties() throws IOException {
		Properties prop = new Properties();
		File file = null;
		if(environment.equalsIgnoreCase(Constants.ENV_TEST)) {
			file = new File(Constants.PCD_SERVICE_ENDPOINT_TEST);
		}else if(environment.equalsIgnoreCase(Constants.ENV_QA)) {
			file = new File(Constants.PCD_SERVICE_ENDPOINT_QA);
		}else if(environment.equalsIgnoreCase(Constants.ENV_PROD)) {
			file = new File(Constants.PCD_SERVICE_ENDPOINT_PROD);
		}
		if(file.exists()) {
			BufferedReader inputStream = new BufferedReader(new FileReader(file));
			try {
				prop.load(inputStream);
			}
			finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}else {
			throw new SystemException("Service End point properties file does not exists: "+file.getAbsolutePath());
		}

		pcd_service_endpoint_cor = prop.getProperty("pcd.service.endpoint.cor");
		pcd_service_endpoint_tda = prop.getProperty("pcd.service.endpoint.tda");
		pcd_service_endpoint_pascor = prop.getProperty("pcd.service.endpoint.pascor");
		pcd_service_endpoint_pastda = prop.getProperty("pcd.service.endpoint.pastda");
		pcd_service_batch_update_size =	Integer.parseInt(prop.getProperty("pcd.service.batch.update.size"));
		prop.clear();
	}
	
	
	public static void logDetails() {
		log.info("========================================");
		log.info(" System context details");
		log.info("========================================");
		log.info("Environment :"+SystemContext.environment);
		log.info("PCD Service End Points -COR :"+pcd_service_endpoint_cor);
		log.info("PCD Service End Points -TDA :"+pcd_service_endpoint_tda);
		log.info("PCD Service End Points -PASCOR :"+pcd_service_endpoint_pascor);
		log.info("PCD Service End Points -PASTDA :"+pcd_service_endpoint_pastda);
		log.info("PCD Service End Points -Update records size :"+pcd_service_batch_update_size);
		log.info("Database -COR :"+datasource.getProperty("spring.datasource.url.cor"));
		log.info("Database -TDA :"+datasource.getProperty("spring.datasource.url.tda"));
		log.info("Database -PASCOR :"+datasource.getProperty("spring.datasource.url.pascor"));
		log.info("Database -PASTDA :"+datasource.getProperty("spring.datasource.url.pastda"));
		log.info("Database batch update size :"+database_batch_update_size);
		log.info("========================================");
	}
	
}
