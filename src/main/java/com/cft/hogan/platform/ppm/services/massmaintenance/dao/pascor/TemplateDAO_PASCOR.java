package com.cft.hogan.platform.ppm.services.massmaintenance.dao.pascor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cft.hogan.platform.ppm.services.massmaintenance.dao.TemplateDAO;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.TemplateDAO_I;
import com.cft.hogan.platform.ppm.services.massmaintenance.entity.TemplateEntity;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Constants;


@Repository
@Transactional("transactionManagerPASCOR")
public class TemplateDAO_PASCOR extends TemplateDAO implements TemplateDAO_I {

	@Autowired
	@PersistenceContext(unitName = Constants.DATASOURCE_PASCOR)
	private EntityManager entityManager;

	public TemplateEntity findByUUID(String uuid) {
		return findByUUID(uuid, entityManager);
	}

	public List<TemplateEntity> findAll() {
		return findAll(entityManager);
	}

	public String save(TemplateEntity entity)  {
		return save(entity, entityManager);
	}

	public int update(TemplateEntity entity)  {
		return update(entity, entityManager);
	}

	public int delete(String uuid)  {
		return delete(uuid, entityManager);
	}

}
