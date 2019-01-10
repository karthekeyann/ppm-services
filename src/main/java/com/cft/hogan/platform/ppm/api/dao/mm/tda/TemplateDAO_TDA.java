package com.cft.hogan.platform.ppm.api.dao.mm.tda;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cft.hogan.platform.ppm.api.dao.mm.TemplateDAO;
import com.cft.hogan.platform.ppm.api.dao.mm.TemplateDAO_I;
import com.cft.hogan.platform.ppm.api.entity.mm.TemplateEntity;
import com.cft.hogan.platform.ppm.api.util.Constants;


@Repository
@Transactional("transactionManagerTDA")
public class TemplateDAO_TDA extends TemplateDAO implements TemplateDAO_I {
	
    @Autowired
    @PersistenceContext(unitName = Constants.DATASOURCE_TDA)
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
