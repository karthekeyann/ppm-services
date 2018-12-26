package com.cft.hogan.platform.ppm.services.massmaintenance.dao.cor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cft.hogan.platform.ppm.services.massmaintenance.dao.TemplatePSetDAO;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.TemplatePSetDAO_I;
import com.cft.hogan.platform.ppm.services.massmaintenance.entity.TemplatePSetEntity;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Constants;


@Repository
@Transactional("transactionManagerCOR")
public class TemplatePSetDAO_COR extends TemplatePSetDAO implements TemplatePSetDAO_I {

	@Autowired
	@PersistenceContext(unitName = Constants.DATASOURCE_COR)
	private EntityManager entityManager;

	public int save(List<TemplatePSetEntity> psets) {
		return save(psets, entityManager);
	}

	public List<TemplatePSetEntity> findByTemplateUUID(String templateUUID) {
		return findByTemplateUUID(templateUUID, entityManager);
	}

	public int deleteByTemplateUUID(String templateUUID) {
		return deleteByTemplateUUID(templateUUID, entityManager);
	}
}
