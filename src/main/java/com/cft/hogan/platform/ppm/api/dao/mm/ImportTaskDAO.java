package com.cft.hogan.platform.ppm.api.dao.mm;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.cft.hogan.platform.ppm.api.entity.mm.ImportTaskEntity;
import com.cft.hogan.platform.ppm.api.entity.mm.ScheduleTaskEntity;
import com.cft.hogan.platform.ppm.api.util.SqlQueries;
import com.cft.hogan.platform.ppm.api.util.Utils;

@SuppressWarnings("unchecked")
abstract public class ImportTaskDAO  {

	protected ImportTaskEntity findByUUID(String uuid, EntityManager entityManager) {

		String sqlQuery = SqlQueries.PPM_MM_IMPORT_TASK_FIND_BY_UUID;
		Query query = entityManager.createNativeQuery(sqlQuery, ImportTaskEntity.class);
		query.setParameter(1, uuid);
		return (ImportTaskEntity) query.getSingleResult();
	}

	protected List<ImportTaskEntity> findByStatus(String status, EntityManager entityManager) {

		String sqlQuery = SqlQueries.PPM_MM_IMPORT_TASK_FIND_BY_STATUS;
		Query query = entityManager.createNativeQuery(sqlQuery, ImportTaskEntity.class);
		query.setParameter(1, status);
		return query.getResultList();
	}

	protected int UpdateStatus(ImportTaskEntity entity, EntityManager entityManager) {

		String sqlQuery = SqlQueries.PPM_MM_IMPORT_TASK_UPDATE_BY_STATUS;
		Query query = entityManager.createNativeQuery(sqlQuery, ImportTaskEntity.class);
		//VALUES
		query.setParameter(1, entity.getStatus());
		query.setParameter(2, entity.getModifiedBy());
		query.setParameter(3, Utils.getCurrentTimeStamp());
		//WHERE
		query.setParameter(4, entity.getUuid());
		return query.executeUpdate();
	}

	protected String save(ImportTaskEntity entity, EntityManager entityManager) {

		String sqlQuery = SqlQueries.PPM_MM_IMPORT_TASK_SAVE;
		Query query = entityManager.createNativeQuery(sqlQuery, ImportTaskEntity.class);
		entity.setUuid(String.valueOf(UUID.randomUUID()));
		query.setParameter(1, entity.getUuid());
		query.setParameter(2, entity.getType());
		query.setParameter(3, entity.getName());
		query.setParameter(4, entity.getStatus());
		query.setParameter(5, entity.getInputFileName());
		query.setParameter(6, entity.getCreatedBy());
		query.setParameter(7, Utils.getCurrentTimeStamp());
		query.setParameter(8, entity.getCreatedBy());
		query.setParameter(9, Utils.getCurrentTimeStamp());
		query.executeUpdate();
		return entity.getUuid();
	}

	protected int delete(String uuid, EntityManager entityManager) {
		
		String sqlQuery = SqlQueries.PPM_MM_IMPORT_TASK_DELETE;
		Query query = entityManager.createNativeQuery(sqlQuery, ScheduleTaskEntity.class);
		//WHERE
		query.setParameter(1, uuid);
		return 	query.executeUpdate();
	}

}	
