package com.cft.hogan.platform.ppm.api.facade;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.cft.hogan.platform.ppm.api.bean.ApplicationBean;
import com.cft.hogan.platform.ppm.api.cache.ApplicationCache;
import com.cft.hogan.platform.ppm.api.config.context.ApplicationContext;
import com.cft.hogan.platform.ppm.api.exception.ExceptionHandler;
import com.cft.hogan.platform.ppm.api.exception.SystemError;
import com.cft.hogan.platform.ppm.api.util.Constants;

@Service
public class ApplicationFacade {

	@Autowired
	Environment env;

	public List<ApplicationBean> getApplications() {
		String region = ApplicationContext.getRegion();
		List<ApplicationBean> applicationsList = null;
		try {
			HashMap<String, List<ApplicationBean>> applicationsMap = ApplicationCache.getApplicationssMap();
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
			ExceptionHandler.handleException(e);
		}
		return applicationsList;
	}


	private Properties readApplications(String region) throws IOException {
		Properties applications = new Properties();
		File file = null;
		StringBuffer fileName = new StringBuffer()
				.append(env.getProperty(Constants.PARMETER_CONFIG_PATH))
				.append(region)
				.append("/parameter-applications.properties");

		file = new File(fileName.toString());

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
			throw new SystemError("Parameter applications properties file does not exists: "+file.getAbsolutePath());
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
