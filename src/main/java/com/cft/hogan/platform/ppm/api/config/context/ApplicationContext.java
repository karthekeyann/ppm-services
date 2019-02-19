package com.cft.hogan.platform.ppm.api.config.context;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cft.hogan.platform.ppm.api.exception.ExceptionHandler;
import com.cft.hogan.platform.ppm.api.exception.SystemError;
import com.cft.hogan.platform.ppm.api.util.Constants;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ApplicationContext {

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
		String region = ApplicationContext.getRegion();
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
			throw new SystemError("Invalid PCD Service Endpoint :"+endPoint);
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

	public static void logout() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	public static String getLoggedInUser() {
		if(SecurityContextHolder.getContext().getAuthentication()!=null) {
			return SecurityContextHolder.getContext().getAuthentication().getName();
		}
		return Constants.EMPTY;
	}

	public static HttpSession getSession() {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);
		return session;
	}

	public static String getSessionID() {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);
		if(session!=null) {
			return session.getId();
		}
		return Constants.EMPTY;
	}

	public static String getUserIdInRequestHeader() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String user = request.getHeader("X-user");
		if(user==null || StringUtils.isEmpty(user)) {
			throw new SystemError("Invalid request header - X-user missing");
		}
		return user;
	}

	public static String getRegion() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String region = request.getHeader("X-region");
		if(region==null || StringUtils.isEmpty(region) || 
				!(region.equalsIgnoreCase(Constants.REGION_COR) || region.equalsIgnoreCase(Constants.REGION_TDA) || 
						region.equalsIgnoreCase(Constants.REGION_PASCOR) || region.equalsIgnoreCase(Constants.REGION_PASTDA))) {
			throw new SystemError("Invalid request header - X-region :"+region);
		}
		return region.toLowerCase();
	}

}
