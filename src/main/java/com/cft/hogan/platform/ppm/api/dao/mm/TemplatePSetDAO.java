package com.cft.hogan.platform.ppm.api.dao.mm;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.util.StringUtils;

import com.cft.hogan.platform.ppm.api.entity.mm.TemplateEntity;
import com.cft.hogan.platform.ppm.api.entity.mm.TemplateParameterEntity;
import com.cft.hogan.platform.ppm.api.util.SqlQueries;
import com.cft.hogan.platform.ppm.api.util.Utils;

@SuppressWarnings("unchecked")
abstract public class TemplatePSetDAO  {

	protected int save(List<TemplateParameterEntity> psets, EntityManager entityManager) {

		final String sqlQuerywithEffDate = SqlQueries.PPM_MM_TEMPLATE_PSET_INSERT_WITH_EFF_DATE;

		final String sqlQueryWithoutEffDate = SqlQueries.PPM_MM_TEMPLATE_PSET_INSERT_WITHOUT_EFF_DATE;

		psets.forEach((entity)->{
			if(StringUtils.isEmpty(entity.getEffectiveDate())) {
				save(entity, sqlQueryWithoutEffDate, entityManager, false);
			}else {
				save(entity, sqlQuerywithEffDate, entityManager, true);
			}
		});
		return psets.size();
	}

	private int save(TemplateParameterEntity entity, String sqlQuery, EntityManager entityManager, boolean isEffDate) {

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

		if(isEffDate) {
			query.setParameter(11, entity.getEffectiveDate());
		}
		return query.executeUpdate();
	}


	protected List<TemplateParameterEntity> findByTemplateUUID(String templateUUID, EntityManager entityManager) {

		String sqlQuery = SqlQueries.PPM_MM_TEMPLATE_PSET_FIND_BY_TEMPLATE_UUID;
		Query query = entityManager.createNativeQuery(sqlQuery, TemplateParameterEntity.class);
		query.setParameter(1, templateUUID);
		return query.getResultList();
	}

	protected int deleteByTemplateUUID(String templateUUID, EntityManager entityManager) {
		String sqlQuery = SqlQueries.PPM_MM_TEMPLATE_PSET_DELETE;
		Query query = entityManager.createNativeQuery(sqlQuery, TemplateEntity.class);
		//WHERE
		query.setParameter(1, templateUUID);
		return query.executeUpdate();
	}
}
