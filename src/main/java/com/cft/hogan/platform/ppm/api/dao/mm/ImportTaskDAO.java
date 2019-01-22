package com.cft.hogan.platform.ppm.api.dao.mm;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.cft.hogan.platform.ppm.api.entity.mm.ImportTaskEntity;
import com.cft.hogan.platform.ppm.api.entity.mm.ScheduleTaskEntity;
import com.cft.hogan.platform.ppm.api.util.Utils;

@SuppressWarnings("unchecked")
abstract public class ImportTaskDAO  {

	protected ImportTaskEntity findByUUID(String uuid, EntityManager entityManager) {

		String sqlQuery = "SELECT * FROM CELPPM.PPM_MM_IMPORT_TASK "
				+ "WHERE UUID = ?";
		Query query = entityManager.createNativeQuery(sqlQuery, ImportTaskEntity.class);
		query.setParameter(1, uuid);
		return (ImportTaskEntity) query.getSingleResult();
	}

	protected List<ImportTaskEntity> findByStatus(String status, EntityManager entityManager) {

		String sqlQuery = "SELECT * FROM CELPPM.PPM_MM_IMPORT_TASK "
				+ "WHERE STATUS = ?  ORDER BY MODIFIED_TS DESC";
		Query query = entityManager.createNativeQuery(sqlQuery, ImportTaskEntity.class);
		query.setParameter(1, status);
		return query.getResultList();
	}

	protected int UpdateStatus(ImportTaskEntity entity, EntityManager entityManager) {

		String sqlQuery = "UPDATE CELPPM.PPM_MM_IMPORT_TASK SET "
				+ "STATUS = ? , MODIFIED_BY = ? , MODIFIED_TS = ? "
				+ "WHERE UUID = ?";
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

		String sqlQuery = "INSERT INTO CELPPM.PPM_MM_IMPORT_TASK " 
				+ "(UUID, TYPE, NAME, STATUS, INPUT_FILE_NAME, CREATED_BY, CREATED_TS , MODIFIED_BY, MODIFIED_TS) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
		
		String sqlQuery = "DELETE FROM CELPPM.PPM_MM_IMPORT_TASK " +
				"WHERE UUID = ?";
		Query query = entityManager.createNativeQuery(sqlQuery, ScheduleTaskEntity.class);
		//WHERE
		query.setParameter(1, uuid);
		return 	query.executeUpdate();
	}

}	
