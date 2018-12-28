package com.cft.hogan.platform.ppm.services.massmaintenance.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.cft.hogan.platform.ppm.services.config.context.SystemContext;
import com.cft.hogan.platform.ppm.services.massmaintenance.bean.ApplicationBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.SystemException;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Constants;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Utils;

@Service
public class ApplicationService {

	public List<ApplicationBean> getApplications() {
		String region = Utils.getRegion();
		List<ApplicationBean> applicationsList = null;
		try {
			HashMap<String, List<ApplicationBean>> applicationsMap = SystemContext.getApplicationssMap();
			if(region.equalsIgnoreCase(Constants.REGION_COR)) {
				if(!applicationsMap.containsKey(Constants.REGION_COR)) {
					applicationsMap.put(Constants.REGION_COR, createList(readApplications(region)));
				}
				applicationsList = applicationsMap.get(Constants.REGION_COR);
			}else if(region.equalsIgnoreCase(Constants.REGION_TDA)) {
				if(!applicationsMap.containsKey(Constants.REGION_TDA)) {
					applicationsMap.put(Constants.REGION_TDA, createList(readApplications(region)));
				}
				applicationsList = applicationsMap.get(Constants.REGION_TDA);
			}else if(region.equalsIgnoreCase(Constants.REGION_PASCOR)) {
				if(!applicationsMap.containsKey(Constants.REGION_PASCOR)) {
					applicationsMap.put(Constants.REGION_PASCOR, createList(readApplications(region)));
				}
				applicationsList = applicationsMap.get(Constants.REGION_PASCOR);
			}else if(region.equalsIgnoreCase(Constants.REGION_PASTDA)) {
				if(!applicationsMap.containsKey(Constants.REGION_PASTDA)) {
					applicationsMap.put(Constants.REGION_PASTDA, createList(readApplications(region)));
				}
				applicationsList = applicationsMap.get(Constants.REGION_PASTDA);
			}
		}catch(Exception e) {
			Utils.handleException(e);
		}
		return applicationsList;
	}


	private Properties readApplications(String region) throws IOException {
		Properties applications = new Properties();
		File file = null;
		String fileName = "/pcd-applications.properties";
		if(region.equalsIgnoreCase(Constants.REGION_COR)) {
			file = new File(Constants.PARMETER_CONFIG_PATH + Constants.REGION_COR +fileName);
		}else if(region.equalsIgnoreCase(Constants.REGION_TDA)) {
			file = new File(Constants.PARMETER_CONFIG_PATH + Constants.REGION_TDA +fileName);
		}else if(region.equalsIgnoreCase(Constants.REGION_PASCOR)) {
			file = new File(Constants.PARMETER_CONFIG_PATH + Constants.REGION_PASCOR +fileName);
		}else if(region.equalsIgnoreCase(Constants.REGION_PASTDA)) {
			file = new File(Constants.PARMETER_CONFIG_PATH + Constants.REGION_PASTDA +fileName);
		}

		if(file.exists()) {
			BufferedReader inputStream = new BufferedReader(new FileReader(file));
			try {
				applications.load(inputStream);
			}
			finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}else {
			throw new SystemException("Applications properties file does not exists: "+file.getAbsolutePath());
		}
		return applications;
	}

	private List<ApplicationBean> createList(Properties parameters){
		List<ApplicationBean> applicationsList = new ArrayList<ApplicationBean>();
		if(parameters!=null) {
			parameters.forEach((key, value)->{
				ApplicationBean bean = new ApplicationBean();
				bean.setId(String.valueOf(key).trim());
				bean.setName(String.valueOf(value).trim());
				applicationsList.add(bean);
			});
		}
		return applicationsList;
	}

}
