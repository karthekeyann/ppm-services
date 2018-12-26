package com.cft.hogan.platform.ppm.services.massmaintenance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cft.hogan.platform.ppm.services.context.SystemContext;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.ImportTaskReviewDetailsDAO_I;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.cor.ImportTaskReviewDetailsDAO_COR;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.pascor.ImportTaskReviewDetailsDAO_PASCOR;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.pastda.ImportTaskReviewDetailsDAO_PASTDA;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.tda.ImportTaskReviewDetailsDAO_TDA;
import com.cft.hogan.platform.ppm.services.massmaintenance.entity.ImportTaskReviewDetailEntity;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.SystemException;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Constants;


@Service
public class ImportTaskReviewDetailsService {

	@Autowired
	ImportTaskReviewDetailsDAO_COR daoCOR;

	@Autowired
	ImportTaskReviewDetailsDAO_TDA daoTDA;
	
	@Autowired
	ImportTaskReviewDetailsDAO_PASCOR daoPASCOR;

	@Autowired
	ImportTaskReviewDetailsDAO_PASTDA daoPASTDA;

	public int save(List<ImportTaskReviewDetailEntity> reviewList) {

		int recordCount = 0;
		recordCount = getDAO().save(reviewList);
		return recordCount;
	}

	public List<ImportTaskReviewDetailEntity> findPsetKeyByImportTaskUUIDAndStatus(String taskID, String status) {

		List<ImportTaskReviewDetailEntity> savedList = null;
		savedList = getDAO().findPsetKeyByImportTaskUUIDAndStatus(taskID, status);
		return savedList;
	}

	public List<ImportTaskReviewDetailEntity> findByImportTaskUUID(String taskID) throws Exception {

		List<ImportTaskReviewDetailEntity> taskDetailsList = null;
		taskDetailsList = getDAO().findByImportTaskUUID(taskID);
		return taskDetailsList;
	}

	public void Update(ImportTaskReviewDetailEntity importTaskReviewDetail) throws Exception {
		getDAO().Update(importTaskReviewDetail);
	}

	private ImportTaskReviewDetailsDAO_I getDAO(){
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

