package com.cft.hogan.platform.ppm.api.dao.mm;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.cft.hogan.platform.ppm.api.entity.mm.ScheduleTaskEntity;
import com.cft.hogan.platform.ppm.api.util.Utils;


@SuppressWarnings("unchecked")
abstract public class ScheduleDAO {

	protected ScheduleTaskEntity findByUUID(String uuid, EntityManager entityManager) {

		String sqlQuery = "SELECT * FROM CELPPM.PPM_MM_SCHEDULED_TASK " +
				"WHERE UUID = ?";
		Query query = entityManager.createNativeQuery(sqlQuery, ScheduleTaskEntity.class);
		query.setParameter(1, uuid);
		return (ScheduleTaskEntity) query.getSingleResult();
	}
	
	
	protected ScheduleTaskEntity findByTemplateUUID(String templateUUID, EntityManager entityManager) {

		String sqlQuery = "SELECT * FROM CELPPM.PPM_MM_SCHEDULED_TASK " +
				"WHERE TEMPLATE_UUID = ?";
		Query query = entityManager.createNativeQuery(sqlQuery, ScheduleTaskEntity.class);
		query.setParameter(1, templateUUID);
		return (ScheduleTaskEntity) query.getSingleResult();
	}
	

	protected List<ScheduleTaskEntity> findByType(String type, EntityManager entityManager) {

		String sqlQuery = "SELECT * FROM CELPPM.PPM_MM_SCHEDULED_TASK " +
				"WHERE type = ?  ORDER BY MODIFIED_TS DESC";
		Query query = entityManager.createNativeQuery(sqlQuery, ScheduleTaskEntity.class);
		query.setParameter(1, type);
		return query.getResultList();
	}
	
	protected List<ScheduleTaskEntity> findByStatus(String status, Date date, String type, EntityManager entityManager) {

		String sqlQuery = "SELECT * FROM CELPPM.PPM_MM_SCHEDULED_TASK " +
				"WHERE STATUS = ? AND EFF_DATE <= ? AND TYPE = ?  ORDER BY MODIFIED_TS DESC";
		Query query = entityManager.createNativeQuery(sqlQuery, ScheduleTaskEntity.class);
		query.setParameter(1, status);
		query.setParameter(2, date);
		query.setParameter(3, type);
		return query.getResultList();
	}

	protected String save(ScheduleTaskEntity entity, EntityManager entityManager) {

		String sqlQuery = "INSERT INTO CELPPM.PPM_MM_SCHEDULED_TASK " +
				"(UUID, TEMPLATE_UUID, NAME, TYPE, EFF_DATE, FILE_PATH, FREQUENCY, FREQ_PATTERN, TASK_OPTIONS, STATUS, REMARKS, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS) "+
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Query query = entityManager.createNativeQuery(sqlQuery, ScheduleTaskEntity.class);
		entity.setUuid(String.valueOf(UUID.randomUUID()));
		query.setParameter(1, entity.getUuid());
		query.setParameter(2, entity.getTemplateUUID());
		query.setParameter(3, entity.getName());
		query.setParameter(4, entity.getType());
		query.setParameter(5, entity.getEffectiveDate());
		query.setParameter(6, entity.getFilePath());
		query.setParameter(7, entity.getFrequency());
		query.setParameter(8, entity.getFreqPattern());
		query.setParameter(9, entity.getTaskOptions());
		query.setParameter(10, entity.getStatus());
		query.setParameter(11, entity.getRemarks());
		query.setParameter(12, entity.getCreatedBy());
		query.setParameter(13, Utils.getCurrentTimeStamp());
		query.setParameter(14, entity.getCreatedBy());
		query.setParameter(15, Utils.getCurrentTimeStamp());
		query.executeUpdate();
		return entity.getUuid();
	}


	protected int update(ScheduleTaskEntity entity, EntityManager entityManager) {
		String sqlQuery = "UPDATE CELPPM.PPM_MM_SCHEDULED_TASK SET " +
				"TEMPLATE_UUID = ? , NAME = ? , EFF_DATE = ? , FILE_PATH = ? , FREQUENCY = ? , FREQ_PATTERN = ? , TASK_OPTIONS = ? , STATUS = ? , REMARKS = ? , MODIFIED_BY = ? , MODIFIED_TS = ? "+
				"WHERE UUID = ?";
		Query query = entityManager.createNativeQuery(sqlQuery, ScheduleTaskEntity.class);
		//VALUES
		query.setParameter(1, entity.getTemplateUUID());
		query.setParameter(2, entity.getName());
		query.setParameter(3, entity.getEffectiveDate());
		query.setParameter(4, entity.getFilePath());
		query.setParameter(5, entity.getFrequency());
		query.setParameter(6, entity.getFreqPattern());
		query.setParameter(7, entity.getTaskOptions());
		query.setParameter(8, entity.getStatus());
		query.setParameter(9, entity.getRemarks());
		query.setParameter(10, entity.getModifiedBy());
		query.setParameter(11, Utils.getCurrentTimeStamp());
		//WHERE
		query.setParameter(12, entity.getUuid());
		return query.executeUpdate();
	}


	protected int delete(String uuid, EntityManager entityManager) {
		String sqlQuery = "DELETE FROM CELPPM.PPM_MM_SCHEDULED_TASK " +
				"WHERE UUID = ?";
		Query query = entityManager.createNativeQuery(sqlQuery, ScheduleTaskEntity.class);
		//WHERE
		query.setParameter(1, uuid);
		return query.executeUpdate();
	}

}
