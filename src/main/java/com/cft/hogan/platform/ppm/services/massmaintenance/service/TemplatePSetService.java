package com.cft.hogan.platform.ppm.services.massmaintenance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cft.hogan.platform.ppm.services.context.SystemContext;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.TemplatePSetDAO_I;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.cor.TemplatePSetDAO_COR;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.pascor.TemplatePSetDAO_PASCOR;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.pastda.TemplatePSetDAO_PASTDA;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.tda.TemplatePSetDAO_TDA;
import com.cft.hogan.platform.ppm.services.massmaintenance.entity.TemplatePSetEntity;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.SystemException;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Constants;

@Service
public class TemplatePSetService {

	@Autowired
	TemplatePSetDAO_COR daoCOR;

	@Autowired
	TemplatePSetDAO_TDA daoTDA;
	
	@Autowired
	TemplatePSetDAO_PASCOR daoPASCOR;

	@Autowired
	TemplatePSetDAO_PASTDA daoPASTDA;


	public void save(List<TemplatePSetEntity> psets) throws Exception {
		getDAO().save(psets);
	}

	public List<TemplatePSetEntity> findByTemplateUUID(String templateId) throws Exception {
		return getDAO().findByTemplateUUID(templateId);
	}

	public void deleteByTemplateUUID(String templateId) throws Exception {
		getDAO().deleteByTemplateUUID(templateId);
	}

	private TemplatePSetDAO_I getDAO(){
		String region = SystemContext.getRegion();
		if(region.equalsIgnoreCase(Constants.REGION_COR)) {
			return daoCOR;
		}else if(region.equalsIgnoreCase(Constants.REGION_TDA)) {
			return daoTDA;
		}else if(region.equalsIgnoreCase(Constants.REGION_PASCOR)) {
			return daoPASCOR;
		}else if(region.equalsIgnoreCase(Constants.REGION_PASTDA)) {
			return daoPASTDA;
		}{
			throw new SystemException("Invalid region :"+region);
		}
	}
}
