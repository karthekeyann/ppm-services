package com.cft.hogan.platform.ppm.services.massmaintenance.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cft.hogan.platform.ppm.services.cache.SystemCache;
import com.cft.hogan.platform.ppm.services.massmaintenance.bean.CompanyBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.PCDService;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Utils;

@Service
public class CompanyService {

	public List<CompanyBean> getCompanyDetails(String pcdId) {
		List<CompanyBean> companies = null;
		try {

			HashMap<String, HashMap<String, List<CompanyBean>>> companiesMap = SystemCache.getCompaniesMap();
			String region = Utils.getRegion();
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
			Utils.handleException(e);
		}
		return companies;
	}
}
