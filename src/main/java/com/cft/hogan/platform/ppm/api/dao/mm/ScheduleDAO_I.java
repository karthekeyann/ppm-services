package com.cft.hogan.platform.ppm.api.dao.mm;

import java.sql.Date;
import java.util.List;

import com.cft.hogan.platform.ppm.api.entity.mm.ScheduleEntity;

public interface ScheduleDAO_I {
	
 	public String save(ScheduleEntity entity);
 	
 	public ScheduleEntity findByUUID(String uuid);

 	public List<ScheduleEntity> findByType(String type);
 	
 	public List<ScheduleEntity> findByStatus(String status, Date date, String type);
 	
 	public int update(ScheduleEntity entity) ;

 	public int delete(String uuid);

	public ScheduleEntity findByTemplateUUID(String templateUUID);


}
