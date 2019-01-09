package com.cft.hogan.platform.ppm.services.config.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

abstract public class DataSource_Base {
	
	@Autowired
	Environment env;
	
	protected Environment getProperties() throws Exception {
//		if(prop==null) {
//			SystemContext.loadPropertyContext(env.getActiveProfiles()[0]);
//			prop = SystemContext.getDataSourceProperties();
//		}
		return env;
	}
	
}
