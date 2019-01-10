package com.cft.hogan.platform.ppm.api.config.context;

import java.io.IOException;

import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.cft.hogan.platform.ppm.api.exception.SystemException;
import com.cft.hogan.platform.ppm.api.util.Constants;
import com.cft.hogan.platform.ppm.api.util.Utils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class EnvironmentContext {

	private static Environment env;
	
	public static void setEnvironment(Environment lenv) {
		
		env = lenv;
	}
	
	public static int getPCDServiceUpdateRecordSize() {

		return Integer.parseInt(env.getProperty("pcd.service.update.record.size"));
	}

	public static int getDataBaseBatchUpdateSize() {

		return Integer.parseInt(env.getProperty("database.batch.update.size"));
	}

	public static String getEndPoint() throws IOException {

		String region = Utils.getRegion();
		String endPoint = null;
		if(region.equalsIgnoreCase(Constants.REGION_COR)) {
			endPoint = env.getProperty("pcd.service.endpoint.cor");
		}else if(region.equalsIgnoreCase(Constants.REGION_TDA)) {
			endPoint = env.getProperty("pcd.service.endpoint.tda");
		}else if(region.equalsIgnoreCase(Constants.REGION_PASCOR)) {
			endPoint = env.getProperty("pcd.service.endpoint.pascor");
		}else if(region.equalsIgnoreCase(Constants.REGION_PASTDA)) {
			endPoint = env.getProperty("pcd.service.endpoint.pastda");
		}
		if(endPoint==null || StringUtils.isEmpty(endPoint)) {
			throw new SystemException("Invalid PCD Service Endpoint :"+endPoint);
		}
		return endPoint;
	}
	
	public static String getLabelsBasePath() {
		return env.getProperty(Constants.LABELS_PROP_PATH);
	}


	public static void logDetails() {
		log.info("========================================");
		log.info(" System context details");
		log.info("========================================");
		log.info("Active Profile :"+env.getActiveProfiles()[0]);
		log.info("PCD Service End Points -COR :"+env.getProperty("pcd.service.endpoint.cor"));
		log.info("PCD Service End Points -TDA :"+env.getProperty("pcd.service.endpoint.tda"));
		log.info("PCD Service End Points -PASCOR :"+env.getProperty("pcd.service.endpoint.pascor"));
		log.info("PCD Service End Points -PASTDA :"+env.getProperty("pcd.service.endpoint.pastda"));
		log.info("PCD Service End Points -Update records size :"+env.getProperty("pcd.service.update.record.size"));
		log.info("Database -COR :"+env.getProperty("spring.datasource.url.cor"));
		log.info("Database -TDA :"+env.getProperty("spring.datasource.url.tda"));
		log.info("Database -PASCOR :"+env.getProperty("spring.datasource.url.pascor"));
		log.info("Database -PASTDA :"+env.getProperty("spring.datasource.url.pastda"));
		log.info("Database batch update size :"+env.getProperty("database.batch.update.size"));
		log.info("========================================");
	}

}
