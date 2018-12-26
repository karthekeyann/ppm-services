package com.cft.hogan.platform.ppm.services.massmaintenance.dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import com.cft.hogan.platform.ppm.services.context.SystemContext;
import com.cft.hogan.platform.ppm.services.massmaintenance.entity.ImportTaskReviewDetailEntity;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Utils;

@SuppressWarnings("unchecked")
abstract public class ImportTaskReviewDetailsDAO {

	protected List<ImportTaskReviewDetailEntity> findByImportTaskUUID(String importTaskUUID, EntityManager entityManager) {

		String sqlQuery = "SELECT * FROM CELPPM.PPM_MM_IMPORT_TASK_DETAILS " +
				"WHERE IM_TASK_UUID = ?  ORDER BY MODIFIED_TS DESC";
		Query query = entityManager.createNativeQuery(sqlQuery, ImportTaskReviewDetailEntity.class);
		query.setParameter(1, importTaskUUID);
		return query.getResultList();
	}

	protected List<ImportTaskReviewDetailEntity> findPsetKeyByImportTaskUUIDAndStatus(String importTaskUUID, String status, EntityManager entityManager) {

		String sqlQuery = "SELECT * FROM CELPPM.PPM_MM_IMPORT_TASK_DETAILS " +
				"WHERE IM_TASK_UUID = ? AND STATUS = ?  ORDER BY MODIFIED_TS DESC";
		Query query = entityManager.createNativeQuery(sqlQuery, ImportTaskReviewDetailEntity.class);
		query.setParameter(1, importTaskUUID);
		query.setParameter(2, status);
		return query.getResultList();
	}


	protected int Update(ImportTaskReviewDetailEntity importTaskReviewDetail, EntityManager entityManager) {

		String sqlQuery = "UPDATE CELPPM.PPM_MM_IMPORT_TASK_DETAILS SET "
				+ "ACTION = ? , STATUS = ? , RESULT = ? , COMPANY_ID = ? , APPLICATION_ID = ? "
				+ "WHERE IM_TASK_UUID = ? AND PSET_KEY = ?";
		Query query = entityManager.createNativeQuery(sqlQuery);
		//VALUES
		query.setParameter(1, importTaskReviewDetail.getAction());
		query.setParameter(2, importTaskReviewDetail.getStatus());
		query.setParameter(3, importTaskReviewDetail.getResult());
		query.setParameter(4, importTaskReviewDetail.getCompanyID());
		query.setParameter(5, importTaskReviewDetail.getApplicationID());
		//CONDITION
		query.setParameter(6, importTaskReviewDetail.getImportTaskUUID());
		query.setParameter(7, importTaskReviewDetail.getPSetKey());
		return query.executeUpdate();
	}

	protected int save(List<ImportTaskReviewDetailEntity> reviewList, EntityManager entityManager) {

		int recordCount = 0;
		if(reviewList.size()>0) {
			entityManager.setFlushMode(FlushModeType.COMMIT);
			for(ImportTaskReviewDetailEntity entity: reviewList) {
				entity.setUuid(String.valueOf(UUID.randomUUID()));
				entity.setCreatedOn(String.valueOf(Utils.getCurrentTimeStamp()));
				entityManager.persist(entity);
				if(recordCount % SystemContext.getDataBaseBatchUpdateSize() == 0) {
					entityManager.flush();
					entityManager.clear();
				}
				recordCount++;
			}
			entityManager.flush();
			entityManager.setFlushMode(FlushModeType.AUTO);
		}
		return recordCount;
	}

}
