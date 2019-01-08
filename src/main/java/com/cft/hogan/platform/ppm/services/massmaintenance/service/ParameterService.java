package com.cft.hogan.platform.ppm.services.massmaintenance.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.cft.hogan.platform.ppm.services.cache.SystemCache;
import com.cft.hogan.platform.ppm.services.massmaintenance.bean.ParameterBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.SystemException;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Constants;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Utils;

@Service
public class ParameterService {

	public List<ParameterBean> getParameters(String applicationID) {
		String region = Utils.getRegion();
		List<ParameterBean> parametersList = null;
		try {
			HashMap<String, List<ParameterBean>> parametersMap = SystemCache.getParametersMap();
			String key = null;
			if(region.equalsIgnoreCase(Constants.REGION_COR)) {
				key = Constants.REGION_COR+applicationID;
				if(!parametersMap.containsKey(key)) {
					parametersMap.put(key, createList(readParameters(region, applicationID)));
				}
				parametersList = parametersMap.get(key);
			}else if(region.equalsIgnoreCase(Constants.REGION_TDA)) {
				key = Constants.REGION_TDA+applicationID;
				if(!parametersMap.containsKey(key)) {
					parametersMap.put(key, createList(readParameters(region, applicationID)));
				}
				parametersList = parametersMap.get(key);
			}else if(region.equalsIgnoreCase(Constants.REGION_PASCOR)) {
				key = Constants.REGION_PASCOR+applicationID;
				if(!parametersMap.containsKey(key)) {
					parametersMap.put(key, createList(readParameters(region, applicationID)));
				}
				parametersList = parametersMap.get(key);
			}else if(region.equalsIgnoreCase(Constants.REGION_PASTDA)) {
				key = Constants.REGION_PASTDA+applicationID;
				if(!parametersMap.containsKey(key)) {
					parametersMap.put(key, createList(readParameters(region, applicationID)));
				}
				parametersList = parametersMap.get(key);
			}
		}catch(Exception e) {
			Utils.handleException(e);
		}
		return parametersList;
	}


	private Properties readParameters(String region, String applicationID) throws IOException {
		Properties applications = new Properties();
		File file = null;
		String fileName = "/"+applicationID+"-parameters.properties";
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
			throw new SystemException("Parameters properties file does not exists: "+file.getAbsolutePath());
		}
		return applications;
	}

	private List<ParameterBean> createList(Properties parameters){
		ArrayList<ParameterBean> parametersList = new ArrayList<ParameterBean>();
		if(parameters!=null) {
			parameters.forEach((key, value)->{
				ParameterBean bean = new ParameterBean();
				bean.setNumber(String.valueOf(key).trim());
				bean.setName(String.valueOf(value).trim());
				parametersList.add(bean);
			});
		}
		Collections.sort(parametersList);
		return   parametersList;
	}
}
