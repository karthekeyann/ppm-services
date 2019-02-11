package com.cft.hogan.platform.ppm.api.cache;

import java.util.HashMap;
import java.util.List;

import com.cft.hogan.platform.ppm.api.bean.ApplicationBean;
import com.cft.hogan.platform.ppm.api.bean.CompanyBean;
import com.cft.hogan.platform.ppm.api.bean.ParameterBean;
import com.cft.hogan.platform.ppm.api.pcd.service.client.PcdXmlRs_Type;

public class ApplicationCache {
	
	private static HashMap<String, List<ApplicationBean>> applicationsMap = new HashMap<>();
	private static HashMap<String, List<ParameterBean>> parametersMap = new HashMap<>();
	private static HashMap<String, HashMap<String, List<CompanyBean>>> companiesMap = new HashMap<>();
	private static HashMap<String, PcdXmlRs_Type> xmlTemplatesMap = new HashMap<>();

	public static HashMap<String, PcdXmlRs_Type> getXMLTemplatesMap(){
		return ApplicationCache.xmlTemplatesMap;
	}

	public static HashMap<String, HashMap<String, List<CompanyBean>>> getCompaniesMap(){
		return ApplicationCache.companiesMap;
	}

	public static HashMap<String, List<ApplicationBean>> getApplicationssMap(){
		return ApplicationCache.applicationsMap;
	}

	public static HashMap<String, List<ParameterBean>> getParametersMap(){
		return ApplicationCache.parametersMap;
	}

}
