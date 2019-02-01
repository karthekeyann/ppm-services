package com.cft.hogan.platform.ppm.api.dao.mm;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.cft.hogan.platform.ppm.api.entity.mm.TemplateEntity;
import com.cft.hogan.platform.ppm.api.util.SqlQueries;
import com.cft.hogan.platform.ppm.api.util.Utils;


@SuppressWarnings("unchecked")
public abstract class TemplateDAO  {

	protected TemplateEntity findByUUID(String uuid, EntityManager entityManager) {

		String sqlQuery = SqlQueries.PPM_MM_TEMPLATE_FIND_BY_UUID;
		Query query = entityManager.createNativeQuery(sqlQuery, TemplateEntity.class);
		query.setParameter(1, uuid);
		return (TemplateEntity)query.getSingleResult();
	}

	protected List<TemplateEntity> findAll(EntityManager entityManager) {

		String sqlQuery = SqlQueries.PPM_MM_TEMPLATE_FIND_ALL;
		Query query = entityManager.createNativeQuery(sqlQuery, TemplateEntity.class);
		return query.getResultList();
	}

	protected String save(TemplateEntity entity, EntityManager entityManager)  {

		String sqlQuery = SqlQueries.PPM_MM_TEMPLATE_INSERT;
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
		String sqlQuery = SqlQueries.PPM_MM_TEMPLATE_UPDATE;
		Query query = entityManager.createNativeQuery(sqlQuery, TemplateEntity.class);
		//VALUES
		query.setParameter(1, entity.getModifiedBy());
		query.setParameter(2, Utils.getCurrentTimeStamp());
		//WHERE
		query.setParameter(3, entity.getUuid());
		return query.executeUpdate();
	}


	protected int delete(String uuid, EntityManager entityManager)  {
		String sqlQuery = SqlQueries.PPM_MM_TEMPLATE_DELETE;
		Query query = entityManager.createNativeQuery(sqlQuery, TemplateEntity.class);
		//WHERE
		query.setParameter(1, uuid);
		return 	query.executeUpdate();
	}
}
