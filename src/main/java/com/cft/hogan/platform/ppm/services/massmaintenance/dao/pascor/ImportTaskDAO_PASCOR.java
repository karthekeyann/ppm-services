package com.cft.hogan.platform.ppm.services.massmaintenance.dao.pascor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cft.hogan.platform.ppm.services.massmaintenance.dao.ImportTaskDAO;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.ImportTaskDAO_I;
import com.cft.hogan.platform.ppm.services.massmaintenance.entity.ImportTaskEntity;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Constants;


@Repository
@Transactional("transactionManagerPASCOR")
public class ImportTaskDAO_PASCOR extends ImportTaskDAO implements ImportTaskDAO_I {

	@Autowired
	@PersistenceContext(unitName = Constants.DATASOURCE_PASCOR)
	private EntityManager entityManager;

	public ImportTaskEntity findByUUID(String uuid) {
		return findByUUID(uuid, entityManager);
	}

	public List<ImportTaskEntity> findByStatus(String status) {
		return findByStatus(status, entityManager);
	}

	public int UpdateStatus(ImportTaskEntity entity) {
		return UpdateStatus(entity, entityManager);

	}

	public String save(ImportTaskEntity entity) {
		return save(entity, entityManager);
	}

	public int delete(String uuid) {
		return delete(uuid, entityManager);
	}
}
