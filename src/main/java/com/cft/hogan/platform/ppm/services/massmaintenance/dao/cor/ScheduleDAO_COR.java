package com.cft.hogan.platform.ppm.services.massmaintenance.dao.cor;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cft.hogan.platform.ppm.services.massmaintenance.dao.ScheduleDAO;
import com.cft.hogan.platform.ppm.services.massmaintenance.dao.ScheduleDAO_I;
import com.cft.hogan.platform.ppm.services.massmaintenance.entity.ScheduleEntity;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Constants;


@Repository
@Transactional("transactionManagerCOR")
public class ScheduleDAO_COR extends ScheduleDAO implements ScheduleDAO_I {

    @Autowired
    @PersistenceContext(unitName = Constants.DATASOURCE_COR)
    private EntityManager entityManager;
    
    @Override
 	public ScheduleEntity findByUUID(String uuid) {
 		return findByUUID(uuid, entityManager);
 	}

 	@Override
 	public List<ScheduleEntity> findByType(String type) {
 		return findByType(type, entityManager);
 	}

 	@Override
 	public String save(ScheduleEntity entity)  {
 		return save(entity, entityManager);
 	}
 	
 	@Override
 	public int update(ScheduleEntity entity) {
 		return update(entity, entityManager);

 	}

 	@Override
 	public int delete(String uuid) {
 		return delete(uuid, entityManager);

 	}

 	@Override
	public ScheduleEntity findByTemplateUUID(String templateUUID) {
		return findByTemplateUUID(templateUUID, entityManager);
	}

	@Override
	public List<ScheduleEntity> findByStatus(String status, Date date, String type) {
		return findByStatus(status, date, type, entityManager);
	}

}
