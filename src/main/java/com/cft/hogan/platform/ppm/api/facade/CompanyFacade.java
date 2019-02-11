package com.cft.hogan.platform.ppm.api.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cft.hogan.platform.ppm.api.bean.CompanyBean;
import com.cft.hogan.platform.ppm.api.cache.ApplicationCache;
import com.cft.hogan.platform.ppm.api.config.context.ApplicationContext;
import com.cft.hogan.platform.ppm.api.exception.ExceptionHandler;
import com.cft.hogan.platform.ppm.api.pcd.service.PCDService;

@Service
public class CompanyFacade {

	public List<CompanyBean> getCompanyDetails(String pcdId) {
		List<CompanyBean> companies = null;
		try {

			HashMap<String, HashMap<String, List<CompanyBean>>> companiesMap = ApplicationCache.getCompaniesMap();
			String region = ApplicationContext.getRegion();
			if(!companiesMap.containsKey(region)) {
				PCDService service = new PCDService();
				companiesMap.put(region, service.getCompanyDetails());
			}
			companies = companiesMap.get(region).get(pcdId);

			if(companies == null) {
				companies = new ArrayList<CompanyBean>();
			}
			if(companies.size()>1) {
				Collections.sort(companies);
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
		return companies;
	}
}
