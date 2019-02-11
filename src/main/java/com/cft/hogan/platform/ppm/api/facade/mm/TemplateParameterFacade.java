package com.cft.hogan.platform.ppm.api.facade.mm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cft.hogan.platform.ppm.api.config.context.ApplicationContext;
import com.cft.hogan.platform.ppm.api.dao.mm.TemplatePSetDAO_I;
import com.cft.hogan.platform.ppm.api.dao.mm.cor.TemplatePSetDAO_COR;
import com.cft.hogan.platform.ppm.api.dao.mm.pascor.TemplatePSetDAO_PASCOR;
import com.cft.hogan.platform.ppm.api.dao.mm.pastda.TemplatePSetDAO_PASTDA;
import com.cft.hogan.platform.ppm.api.dao.mm.tda.TemplatePSetDAO_TDA;
import com.cft.hogan.platform.ppm.api.entity.mm.TemplateParameterEntity;
import com.cft.hogan.platform.ppm.api.exception.SystemError;
import com.cft.hogan.platform.ppm.api.util.Constants;

@Service
public class TemplateParameterFacade {

	@Autowired
	TemplatePSetDAO_COR daoCOR;

	@Autowired
	TemplatePSetDAO_TDA daoTDA;
	
	@Autowired
	TemplatePSetDAO_PASCOR daoPASCOR;

	@Autowired
	TemplatePSetDAO_PASTDA daoPASTDA;


	public void save(List<TemplateParameterEntity> psets) throws Exception {
		getDAO().save(psets);
	}

	public List<TemplateParameterEntity> findByTemplateUUID(String templateId) throws Exception {
		return getDAO().findByTemplateUUID(templateId);
	}

	public void deleteByTemplateUUID(String templateId) throws Exception {
		getDAO().deleteByTemplateUUID(templateId);
	}

	private TemplatePSetDAO_I getDAO(){
		String region = ApplicationContext.getRegion();
		if(region.equalsIgnoreCase(Constants.REGION_COR)) {
			return daoCOR;
		}else if(region.equalsIgnoreCase(Constants.REGION_TDA)) {
			return daoTDA;
		}else if(region.equalsIgnoreCase(Constants.REGION_PASCOR)) {
			return daoPASCOR;
		}else if(region.equalsIgnoreCase(Constants.REGION_PASTDA)) {
			return daoPASTDA;
		}{
			throw new SystemError("Invalid region :"+region);
		}
	}
}
