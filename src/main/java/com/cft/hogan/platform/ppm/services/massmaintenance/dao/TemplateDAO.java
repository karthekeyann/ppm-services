package com.cft.hogan.platform.ppm.services.massmaintenance.dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.cft.hogan.platform.ppm.services.massmaintenance.entity.TemplateEntity;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Utils;


@SuppressWarnings("unchecked")
public abstract class TemplateDAO  {

	protected TemplateEntity findByUUID(String uuid, EntityManager entityManager) {

		String sqlQuery = "SELECT * FROM CELPPM.PPM_MM_TEMPLATE " +
				"WHERE UUID = ?";
		Query query = entityManager.createNativeQuery(sqlQuery, TemplateEntity.class);
		query.setParameter(1, uuid);
		return (TemplateEntity)query.getSingleResult();
	}

	protected List<TemplateEntity> findAll(EntityManager entityManager) {

		String sqlQuery = "SELECT * FROM CELPPM.PPM_MM_TEMPLATE ORDER BY MODIFIED_TS DESC";
		Query query = entityManager.createNativeQuery(sqlQuery, TemplateEntity.class);
		return query.getResultList();
	}

	protected String save(TemplateEntity entity, EntityManager entityManager)  {

		String sqlQuery = "INSERT INTO CELPPM.PPM_MM_TEMPLATE " +
				"(UUID, NAME, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS) "+
				"VALUES (?, ?, ?, ?, ?, ?)";
		Query query = entityManager.createNativeQuery(sqlQuery, TemplateEntity.class);
		entity.setUuid(String.valueOf(UUID.randomUUID()));
		query.setParameter(1, entity.getUuid());
		query.setParameter(2, entity.getName());
		query.setParameter(3, entity.getCreatedBy());
		query.setParameter(4, Utils.getCurrentTimeStamp());
		query.setParameter(5, entity.getCreatedBy());
		query.setParameter(6, Utils.getCurrentTimeStamp());
		query.executeUpdate();
		return entity.getUuid();
	}


	protected int update(TemplateEntity entity, EntityManager entityManager)  {
		String sqlQuery = "UPDATE CELPPM.PPM_MM_TEMPLATE SET " +
				"MODIFIED_BY = ? , MODIFIED_TS = ? "+
				"WHERE UUID = ?";
		Query query = entityManager.createNativeQuery(sqlQuery, TemplateEntity.class);
		//VALUES
		query.setParameter(1, entity.getModifiedBy());
		query.setParameter(2, Utils.getCurrentTimeStamp());
		//WHERE
		query.setParameter(3, entity.getUuid());
		return query.executeUpdate();
	}


	protected int delete(String uuid, EntityManager entityManager)  {
		String sqlQuery = "DELETE FROM CELPPM.PPM_MM_TEMPLATE " +
				"WHERE UUID = ?";
		Query query = entityManager.createNativeQuery(sqlQuery, TemplateEntity.class);
		//WHERE
		query.setParameter(1, uuid);
		return 	query.executeUpdate();
	}
}
