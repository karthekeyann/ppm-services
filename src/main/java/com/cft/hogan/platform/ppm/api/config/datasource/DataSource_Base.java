package com.cft.hogan.platform.ppm.api.config.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

abstract public class DataSource_Base {
	
	@Autowired
	Environment env;
	
	protected Environment getProperties() throws Exception {
//		if(prop==null) {
//			EnvironmentContext.loadPropertyContext(env.getActiveProfiles()[0]);
//			prop = EnvironmentContext.getDataSourceProperties();
//		}
		return env;
	}
	
}