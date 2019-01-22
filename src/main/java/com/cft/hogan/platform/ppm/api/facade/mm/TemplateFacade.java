package com.cft.hogan.platform.ppm.api.facade.mm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cft.hogan.platform.ppm.api.bean.mm.TemplateBean;
import com.cft.hogan.platform.ppm.api.dao.mm.TemplateDAO_I;
import com.cft.hogan.platform.ppm.api.dao.mm.cor.TemplateDAO_COR;
import com.cft.hogan.platform.ppm.api.dao.mm.pascor.TemplateDAO_PASCOR;
import com.cft.hogan.platform.ppm.api.dao.mm.pastda.TemplateDAO_PASTDA;
import com.cft.hogan.platform.ppm.api.dao.mm.tda.TemplateDAO_TDA;
import com.cft.hogan.platform.ppm.api.entity.mm.ScheduleTaskEntity;
import com.cft.hogan.platform.ppm.api.entity.mm.TemplateEntity;
import com.cft.hogan.platform.ppm.api.entity.mm.TemplateParameterEntity;
import com.cft.hogan.platform.ppm.api.exception.BadRequestException;
import com.cft.hogan.platform.ppm.api.exception.ItemNotFoundException;
import com.cft.hogan.platform.ppm.api.exception.SystemException;
import com.cft.hogan.platform.ppm.api.util.Constants;
import com.cft.hogan.platform.ppm.api.util.Utils;


@Service
public class TemplateFacade {

	@Autowired
	private TemplateDAO_COR daoCOR;

	@Autowired
	private TemplateDAO_TDA daoTDA;
	
	@Autowired
	private TemplateDAO_PASCOR daoPASCOR;

	@Autowired
	private TemplateDAO_PASTDA daoPASTDA;

	@Autowired
	private TemplateParameterFacade templateParameterFacade;

	@Autowired
	private ScheduleTaskFacade scheduleTaskFacade;

	public TemplateBean save(TemplateBean input) {
		String uuid = null;
		try {
			validatePSets(input.getPsets());
			input.setCreatedBy(Utils.getUserIdInRequestHeader());
			uuid = getDAO().save(beanToEntity(input));
			final String templateUUID = uuid;
			input.getPsets().forEach((pset) -> {
				if(!StringUtils.isEmpty(pset.getEffectiveDate()) && !Utils.isValidDate(pset.getEffectiveDate())) {
					throw new BadRequestException("Invalid Effective date. Parameter# -"+pset.getNumber());
				}
				pset.setTemplateUUID(templateUUID);
				pset.setCreatedBy(Utils.getUserIdInRequestHeader());
			});
			templateParameterFacade.save(input.getPsets());
		} catch (Exception e) {
			Utils.handleException(e);
		}
		return findByUUID(uuid);
	}

	public List<TemplateBean> findAll() {
		List<TemplateBean> beanList = new ArrayList<TemplateBean>();
		try {
			List<TemplateEntity> entityList = null;
			entityList = getDAO().findAll();
			entityList.forEach((entity)->{
				beanList.add(entityToBean(entity));
			});
		} catch (Exception e) {
			Utils.handleException(e);
		}
		return beanList;
	}

	public TemplateBean findByUUID(String templateId) {
		TemplateBean bean = null;
		try {
			bean = entityToBean(getDAO().findByUUID(templateId));
			bean.setPsets(templateParameterFacade.findByTemplateUUID(templateId));
		} catch (Exception e) {
			Utils.handleException(e);
		}
		return bean;
	}

	public TemplateBean update(String templateId, TemplateBean input) {
		try {
			checkOwner(templateId);
			validatePSets(input.getPsets());
			input.setUuid(templateId);
			input.setModifiedBy(Utils.getUserIdInRequestHeader());
			input.getPsets().forEach((pset) -> {
				if(!StringUtils.isEmpty(pset.getEffectiveDate()) && !Utils.isValidDate(pset.getEffectiveDate())) {
					throw new BadRequestException("Invalid Effective date. Parameter# -"+pset.getNumber());
				}
				pset.setTemplateUUID(templateId);
				pset.setCreatedBy(Utils.getUserIdInRequestHeader());;
			});
			templateParameterFacade.deleteByTemplateUUID(input.getUuid());
			templateParameterFacade.save(input.getPsets());
			
			//update template table
			getDAO().update(beanToEntity(input));
		} catch (Exception e) {
			Utils.handleException(e);
		}		
		return findByUUID(input.getUuid());
	}

	public void delete(String uuid) {
		try {
			try {
				checkOwner(uuid);
				ScheduleTaskEntity schedule = scheduleTaskFacade.findByTemplateUUID(uuid);
				if(schedule !=null) {
					throw new BadRequestException("Template linked to Schedule task. Unlink and try again: Ref Schedule Task :"+schedule.getName());
				}
			}catch(EmptyResultDataAccessException | ItemNotFoundException e) {
				//Delete template when no ref in Schedule Task
				templateParameterFacade.deleteByTemplateUUID(uuid);
				getDAO().delete(uuid);
			}
		} catch (Exception e) {
			Utils.handleException(e);
		}
	}
	
	private void checkOwner(String uuid) {
		TemplateBean template = findByUUID(uuid);
		if(template == null ) {
			throw new ItemNotFoundException();
		}
		if(!Utils.getUserIdInRequestHeader().equalsIgnoreCase(template.getCreatedBy())) {
			throw new BadRequestException("Template can be edited by template creator/owner");
		}
	}
	
	private void validatePSets(List<TemplateParameterEntity> psets) {
		if(psets == null || psets.size()==0) {
			throw new BadRequestException("Parameter not selected");
		}
	}

	private TemplateBean entityToBean(TemplateEntity entity) {
		TemplateBean bean = new TemplateBean();
		bean.setCreatedBy(entity.getCreatedBy());
		bean.setCreatedOn(entity.getCreatedOn());
		bean.setModifiedBy(entity.getModifiedBy());
		bean.setModifiedOn(entity.getModifiedOn());
		bean.setName(entity.getName());
		bean.setUuid(entity.getUuid());
		return bean;
	}

	private TemplateEntity beanToEntity(TemplateBean bean) {
		TemplateEntity entity = new TemplateEntity();
		entity.setCreatedBy(bean.getCreatedBy());
		entity.setCreatedOn(bean.getCreatedOn());
		entity.setModifiedBy(bean.getModifiedBy());
		entity.setModifiedOn(bean.getModifiedOn());
		entity.setName(bean.getName());
		entity.setUuid(bean.getUuid());
		return entity;
	}

	private TemplateDAO_I getDAO(){
		String region = Utils.getRegion();
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
