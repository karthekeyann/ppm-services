package com.cft.hogan.platform.ppm.api.dao.mm.pastda;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cft.hogan.platform.ppm.api.dao.mm.ScheduleDAO;
import com.cft.hogan.platform.ppm.api.dao.mm.ScheduleDAO_I;
import com.cft.hogan.platform.ppm.api.entity.mm.ScheduleTaskEntity;
import com.cft.hogan.platform.ppm.api.util.Constants;


@Repository
@Transactional("transactionManagerPASTDA")
public class ScheduleDAO_PASTDA extends ScheduleDAO implements ScheduleDAO_I {

	@Autowired
	@PersistenceContext(unitName = Constants.DATASOURCE_PASTDA)
	private EntityManager entityManager;

 	public ScheduleTaskEntity findByUUID(String uuid) {
 		return findByUUID(uuid, entityManager);
 	}

 	public List<ScheduleTaskEntity> findByType(String type) {
 		return findByType(type, entityManager);
 	}

 	public String save(ScheduleTaskEntity entity)  {
 		return save(entity, entityManager);
 	}
 	
 	public int update(ScheduleTaskEntity entity) {
 		return update(entity, entityManager);
 	}

 	public int delete(String uuid) {
 		return delete(uuid, entityManager);
 	}

	public ScheduleTaskEntity findByTemplateUUID(String templateUUID) {
		return findByTemplateUUID(templateUUID, entityManager);
	}
	
	@Override
	public List<ScheduleTaskEntity> findByStatus(String status, Date date, String type) {
		return findByStatus(status, date, type, entityManager);
	}

}
