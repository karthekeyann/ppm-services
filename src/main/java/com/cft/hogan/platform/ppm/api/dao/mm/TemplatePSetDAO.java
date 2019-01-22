package com.cft.hogan.platform.ppm.api.dao.mm;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.util.StringUtils;

import com.cft.hogan.platform.ppm.api.entity.mm.TemplateEntity;
import com.cft.hogan.platform.ppm.api.entity.mm.TemplateParameterEntity;
import com.cft.hogan.platform.ppm.api.util.Utils;

@SuppressWarnings("unchecked")
abstract public class TemplatePSetDAO  {

	protected int save(List<TemplateParameterEntity> psets, EntityManager entityManager) {

		final String sqlQuery = "INSERT INTO CELPPM.PPM_MM_TEMPLATE_PSET " +
				"(UUID, TEMPLATE_UUID, NUMBER, NAME, COMPANY_ID, APPLICATION_ID, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS, EFF_DATE) "+
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		final String sqlQueryWithoutEffDate = "INSERT INTO CELPPM.PPM_MM_TEMPLATE_PSET " +
				"(UUID, TEMPLATE_UUID, NUMBER, NAME, COMPANY_ID, APPLICATION_ID, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS) "+
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		psets.forEach((entity)->{
			if(StringUtils.isEmpty(entity.getEffectiveDate())) {
				save(entity, sqlQueryWithoutEffDate, entityManager);
			}else {
				save(entity, sqlQuery, entityManager);
			}
		});
		return psets.size();
	}

	private int save(TemplateParameterEntity entity, String sqlQuery, EntityManager entityManager) {

		Query query = entityManager.createNativeQuery(sqlQuery, TemplateParameterEntity.class);
		entity.setUuid(String.valueOf(UUID.randomUUID()));
		query.setParameter(1, entity.getUuid());
		query.setParameter(2, entity.getTemplateUUID());
		query.setParameter(3, entity.getNumber());
		query.setParameter(4, entity.getName());
		query.setParameter(5, entity.getCompanyID());
		query.setParameter(6, entity.getApplicationID());
		query.setParameter(7, entity.getCreatedBy());
		query.setParameter(8, Utils.getCurrentTimeStamp());
		query.setParameter(9, entity.getCreatedBy());
		query.setParameter(10, Utils.getCurrentTimeStamp());

		if(!StringUtils.isEmpty(entity.getEffectiveDate())) {
			query.setParameter(11, entity.getEffectiveDate());
		}
		return query.executeUpdate();
	}


	protected List<TemplateParameterEntity> findByTemplateUUID(String templateUUID, EntityManager entityManager) {

		String sqlQuery = "SELECT * FROM CELPPM.PPM_MM_TEMPLATE_PSET " +
				"WHERE TEMPLATE_UUID = ?  ORDER BY NUMBER";
		Query query = entityManager.createNativeQuery(sqlQuery, TemplateParameterEntity.class);
		query.setParameter(1, templateUUID);
		return query.getResultList();
	}

	protected int deleteByTemplateUUID(String templateUUID, EntityManager entityManager) {
		String sqlQuery = "DELETE FROM CELPPM.PPM_MM_TEMPLATE_PSET " +
				"WHERE TEMPLATE_UUID = ?";
		Query query = entityManager.createNativeQuery(sqlQuery, TemplateEntity.class);
		//WHERE
		query.setParameter(1, templateUUID);
		return query.executeUpdate();
	}
}
