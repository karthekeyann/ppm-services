package com.cft.hogan.platform.ppm.api.facade.mm;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cft.hogan.platform.ppm.api.bean.mm.ScheduleBatchBean;
import com.cft.hogan.platform.ppm.api.bean.mm.ScheduleTaskBean;
import com.cft.hogan.platform.ppm.api.bean.mm.TemplateBean;
import com.cft.hogan.platform.ppm.api.dao.mm.ScheduleDAO_I;
import com.cft.hogan.platform.ppm.api.dao.mm.cor.ScheduleDAO_COR;
import com.cft.hogan.platform.ppm.api.dao.mm.pascor.ScheduleDAO_PASCOR;
import com.cft.hogan.platform.ppm.api.dao.mm.pastda.ScheduleDAO_PASTDA;
import com.cft.hogan.platform.ppm.api.dao.mm.tda.ScheduleDAO_TDA;
import com.cft.hogan.platform.ppm.api.entity.mm.ScheduleTaskEntity;
import com.cft.hogan.platform.ppm.api.exception.BadRequestException;
import com.cft.hogan.platform.ppm.api.exception.BusinessException;
import com.cft.hogan.platform.ppm.api.exception.ItemNotFoundException;
import com.cft.hogan.platform.ppm.api.exception.SystemException;
import com.cft.hogan.platform.ppm.api.util.Constants;
import com.cft.hogan.platform.ppm.api.util.DAY;
import com.cft.hogan.platform.ppm.api.util.Utils;

@Service
public class ScheduleTaskFacade {

	@Autowired
	private TemplateFacade templateFacade;

	@Autowired
	private ScheduleDAO_COR daoCOR;

	@Autowired
	private ScheduleDAO_TDA daoTDA;

	@Autowired
	private ScheduleDAO_PASCOR daoPASCOR;

	@Autowired
	private ScheduleDAO_PASTDA daoPASTDA;


	public ScheduleTaskBean findByUUID(String scheduleId) {
		ScheduleTaskBean bean = null;
		try {
			bean =  entityToBean(getDAO().findByUUID(scheduleId));
			if((Constants.EXPORT).equalsIgnoreCase(bean.getType()))	{
				try {
					setTemplateName(bean);
				}catch(Exception e) {
					Utils.handleException(e);
				}
			}
		} catch (Exception e) {
			Utils.handleException(e);
		}
		return bean;
	}


	public ScheduleTaskEntity findByTemplateUUID(String templateUUID) {
		ScheduleTaskEntity entity = null;
		try {
			entity =  getDAO().findByTemplateUUID(templateUUID);
		} catch (Exception e) {
			Utils.handleException(e);
		}
		return entity;
	}

	public List<ScheduleTaskBean> findByType(String type) {
		List<ScheduleTaskBean> beanList = new ArrayList<ScheduleTaskBean>();
		try {
			List<ScheduleTaskEntity> entityList = null;
			entityList =  getDAO().findByType(type);
			entityList.forEach((entity)->{
				ScheduleTaskBean bean =  entityToBean(entity);
				if(type.equalsIgnoreCase(Constants.EXPORT))	{
					try {
						setTemplateName(bean);
					}catch(Exception e) {
						Utils.handleException(e);
					}
				}
				beanList.add(bean);
			});
		} catch (Exception e) {
			Utils.handleException(e);
		}
		return beanList;	
	}

	public ScheduleTaskBean save(ScheduleTaskBean bean) {
		String uuid = null;
		try {
			validateEffectiveDate(bean.getEffectiveDate());
			bean.setCreatedBy(Utils.getUserIdInRequestHeader());
			uuid =  getDAO().save(beanToEntity(bean));
		} catch (Exception e) {
			Utils.handleException(e);
		}
		return findByUUID(uuid);
	}


	public ScheduleTaskBean update(ScheduleTaskBean bean, String scheduleId) {
		try {
			if(!Utils.isValidDate(bean.getEffectiveDate())) {
				throw new BusinessException("Invalid Start date: "+bean.getEffectiveDate(), false);
			}
			bean.setUuid(scheduleId);
			bean.setModifiedBy(Utils.getUserIdInRequestHeader());
			getDAO().update(beanToEntity(bean));
		} catch (Exception e) {
			Utils.handleException(e);
		}
		return findByUUID(bean.getUuid());
	}


	public void delete(String scheduleId) {
		try {
			ScheduleTaskBean schedule = findByUUID(scheduleId);
			if(schedule == null ) {
				throw new ItemNotFoundException();
			}
			if(!Utils.getUserIdInRequestHeader().equalsIgnoreCase(schedule.getCreatedBy())) {
				throw new BadRequestException("Schedule Task can be deleted by task creator/owner");
			}
			getDAO().delete(scheduleId);
		} catch (Exception e) {
			Utils.handleException(e);
		}
	}



	public List<ScheduleBatchBean> schedulesForBatch(Date bpDate, String type){
		List<ScheduleBatchBean> beanList = new ArrayList<ScheduleBatchBean>();
		try {
			List<ScheduleTaskEntity> entityList = null;
			if(bpDate==null) {
				bpDate = new Date(new java.util.Date().getTime());
			}
			entityList =  getDAO().findByStatus(Constants.ACTIVE, bpDate, type);
			final Date _bpDate = bpDate;
			entityList.forEach((entity)->{
				if(evaluateBatchCriteria(_bpDate, entity)) {
					ScheduleBatchBean bean = entityToBeanBatch(entity);
					if(bean.getType().equalsIgnoreCase(Constants.EXPORT)){
						try {
							setTemplate(bean);
						}catch(Exception e) {
							Utils.handleException(e);
						}
					}
					beanList.add(bean);
				}
			});
		} catch (Exception e) {
			Utils.handleException(e);
		}
		return beanList;	
	}


	private boolean evaluateBatchCriteria(Date bpDate, ScheduleTaskEntity entity) {
		//ONLY ONCE
		if("ONLY ONCE".equalsIgnoreCase(entity.getFrequency())){
			return true;
		}
		//DAILY
		else if("DAILY".equalsIgnoreCase(entity.getFrequency())) {
			return true;
		}
		//WEEKLY
		else if("WEEKLY".equalsIgnoreCase(entity.getFrequency())) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(bpDate);
			if(DAY.getDay(cal.get(Calendar.DAY_OF_WEEK)).equalsIgnoreCase(entity.getFreqPattern())) {
				return true;
			}
		}
		//MONTHLY
		else if("MONTHLY".equalsIgnoreCase(entity.getFrequency())) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(bpDate);
			if(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)).equalsIgnoreCase(entity.getFreqPattern())) {
				return true;
			}else if("MONTH END".equalsIgnoreCase(entity.getFreqPattern())) {
				if(cal.get(Calendar.DAY_OF_MONTH) == cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					return true;
				}
			}
		}
		return false;
	}


	private void setTemplateName(ScheduleTaskBean bean) {
		TemplateBean templateBean;
		try {
			templateBean = templateFacade.findByUUID(bean.getTemplateUUID());
			if(null != templateBean)	{
				bean.setTemplateName(templateBean.getName());
			}
		} catch (Exception e) {
			throw new BusinessException("Error setting Template Name in Schedule Task :"+bean.getName(), false);
		}
	}


	private void setTemplate(ScheduleBatchBean bean) {
		TemplateBean templateBean;
		try {
			templateBean = templateFacade.findByUUID(bean.getTemplateUUID());
			if(null != templateBean)	{
				bean.setTemplate(templateBean);
			}
		} catch (Exception e) {
			throw new BusinessException("Error setting Template in Schedule Batch response :"+bean.getName(), true);
		}
	}


	private ScheduleTaskBean entityToBean(ScheduleTaskEntity entity) {
		ScheduleTaskBean bean = new ScheduleTaskBean();
		bean.setCreatedBy(entity.getCreatedBy());
		bean.setCreatedOn(entity.getCreatedOn());
		bean.setEffectiveDate(entity.getEffectiveDate());
		bean.setFilePath(entity.getFilePath());
		bean.setFreqPattern(entity.getFreqPattern());
		bean.setFrequency(entity.getFrequency());
		bean.setModifiedBy(entity.getModifiedBy());
		bean.setModifiedOn(entity.getModifiedOn());
		bean.setName(entity.getName());
		bean.setRemarks(entity.getRemarks());
		bean.setStatus(entity.getStatus());
		if("singleTab".equalsIgnoreCase(entity.getTaskOptions())){
			bean.setSingleTab(true);
		}else {
			bean.setSingleTab(false);
		}
		bean.setTemplateUUID(String.valueOf(entity.getTemplateUUID()));
		bean.setType(entity.getType());
		bean.setUuid(entity.getUuid());
		return bean;
	}

	private ScheduleBatchBean entityToBeanBatch(ScheduleTaskEntity entity) {
		ScheduleBatchBean bean = new ScheduleBatchBean();
		bean.setCreatedBy(entity.getCreatedBy());
		bean.setCreatedOn(entity.getCreatedOn());
		bean.setEffectiveDate(entity.getEffectiveDate());
		bean.setFilePath(entity.getFilePath());
		bean.setFreqPattern(entity.getFreqPattern());
		bean.setFrequency(entity.getFrequency());
		bean.setModifiedBy(entity.getModifiedBy());
		bean.setModifiedOn(entity.getModifiedOn());
		bean.setName(entity.getName());
		bean.setRemarks(entity.getRemarks());
		bean.setStatus(entity.getStatus());
		if("singleTab".equalsIgnoreCase(entity.getTaskOptions())){
			bean.setSingleTab(true);
		}else {
			bean.setSingleTab(false);
		}
		bean.setTemplateUUID(String.valueOf(entity.getTemplateUUID()));
		bean.setType(entity.getType());
		bean.setUuid(entity.getUuid());
		return bean;
	}

	private ScheduleTaskEntity beanToEntity(ScheduleTaskBean bean) {
		ScheduleTaskEntity entity = new ScheduleTaskEntity();
		entity.setCreatedBy(bean.getCreatedBy());
		entity.setCreatedOn(bean.getCreatedOn());
		entity.setEffectiveDate(bean.getEffectiveDate());
		entity.setFilePath(bean.getFilePath());
		entity.setFreqPattern(bean.getFreqPattern());
		entity.setFrequency(bean.getFrequency());
		entity.setModifiedBy(bean.getModifiedBy());
		entity.setModifiedOn(bean.getModifiedOn());
		entity.setName(bean.getName());
		entity.setRemarks(bean.getRemarks());
		entity.setStatus(bean.getStatus());
		if(bean.isSingleTab()){
			entity.setTaskOptions("singleTab");
		}
		entity.setTemplateUUID(bean.getTemplateUUID());
		entity.setType(bean.getType());
		entity.setUuid(bean.getUuid());
		return entity;
	}


	private void validateEffectiveDate(String effectiveDate) throws ParseException {
		if(Utils.isValidDate(effectiveDate)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(Utils.convertStringToSQLDate(effectiveDate));
			if(!effectiveDate.equalsIgnoreCase(Utils.convertDateToString(new java.util.Date())) && 
					cal.compareTo(Calendar.getInstance()) < 0){
				throw new BadRequestException("Effective date should be current or future date.");
			}
		}else {
			throw new BadRequestException("Invalid Effective date");
		}
	}

	private ScheduleDAO_I getDAO(){
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
