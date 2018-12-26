package com.cft.hogan.platform.ppm.services.massmaintenance.util.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PsetElementsInfo { 
	String parameterNumber = null;
	String appName = null;
	Map<String, Integer> headerToColumnMap;	
	List<String> repeatingElements;
	List<String> nonRepeatingElements;
	List<String> keyElements;
	List<String> pcdKeyElements;
	Properties lables = new Properties();
	
	public PsetElementsInfo(){
		headerToColumnMap  = new HashMap<String,Integer>();
		repeatingElements = new ArrayList<String>();
		nonRepeatingElements = new ArrayList<String>();
		keyElements = new ArrayList<String>();
		pcdKeyElements = new ArrayList<String>();
	}
	
	public Map<String, Integer> getHeaderToColumnMap() {
		return headerToColumnMap;
	}
	public void addInHeaderToColumnMap(String key, Integer data) {
		headerToColumnMap.put(key, data);
	}
	public List<String> getRepeatingElements() {
		return repeatingElements;
	}
	public void addInRepeatingElements(String data) {
		repeatingElements.add(data);
	}
	public List<String> getNonRepeatingElements() {
		return nonRepeatingElements;
	}
	public void addInNonRepeatingElements(String data) {
		nonRepeatingElements.add(data);
	}
	public List<String> getPcdKeyElements() {
		return pcdKeyElements;
	}
	public void addInPcdKeyElements(String data) {
		pcdKeyElements.add(data);
	}
	public List<String> getKeyElements() {
		return keyElements;
	}
	public void addInKeyElements(String data) {
		keyElements.add(data);
	}

	public String getParameterNumber() {
		return parameterNumber;
	}

	public void setParameterNumber(String parameterNumber) {
		this.parameterNumber = parameterNumber;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Properties getLables() {
		return lables;
	}

	public void setLables(Properties lables) {
		this.lables = lables;
	}
}
