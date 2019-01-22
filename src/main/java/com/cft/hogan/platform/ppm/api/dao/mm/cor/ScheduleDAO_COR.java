package com.cft.hogan.platform.ppm.api.dao.mm.cor;

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
@Transactional("transactionManagerCOR")
public class ScheduleDAO_COR extends ScheduleDAO implements ScheduleDAO_I {

    @Autowired
    @PersistenceContext(unitName = Constants.DATASOURCE_COR)
    private EntityManager entityManager;
    
    @Override
 	public ScheduleTaskEntity findByUUID(String uuid) {
 		return findByUUID(uuid, entityManager);
 	}

 	@Override
 	public List<ScheduleTaskEntity> findByType(String type) {
 		return findByType(type, entityManager);
 	}

 	@Override
 	public String save(ScheduleTaskEntity entity)  {
 		return save(entity, entityManager);
 	}
 	
 	@Override
 	public int update(ScheduleTaskEntity entity) {
 		return update(entity, entityManager);

 	}

 	@Override
 	public int delete(String uuid) {
 		return delete(uuid, entityManager);

 	}

 	@Override
	public ScheduleTaskEntity findByTemplateUUID(String templateUUID) {
		return findByTemplateUUID(templateUUID, entityManager);
	}

	@Override
	public List<ScheduleTaskEntity> findByStatus(String status, Date date, String type) {
		return findByStatus(status, date, type, entityManager);
	}

}
