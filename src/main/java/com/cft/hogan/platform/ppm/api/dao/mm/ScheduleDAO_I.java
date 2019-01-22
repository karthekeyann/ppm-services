package com.cft.hogan.platform.ppm.api.dao.mm;

import java.sql.Date;
import java.util.List;

import com.cft.hogan.platform.ppm.api.entity.mm.ScheduleTaskEntity;

public interface ScheduleDAO_I {
	
 	public String save(ScheduleTaskEntity entity);
 	
 	public ScheduleTaskEntity findByUUID(String uuid);

 	public List<ScheduleTaskEntity> findByType(String type);
 	
 	public List<ScheduleTaskEntity> findByStatus(String status, Date date, String type);
 	
 	public int update(ScheduleTaskEntity entity) ;

 	public int delete(String uuid);

	public ScheduleTaskEntity findByTemplateUUID(String templateUUID);


}
