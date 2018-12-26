package com.cft.hogan.platform.ppm.services.massmaintenance.dao;

import java.util.List;

import com.cft.hogan.platform.ppm.services.massmaintenance.entity.TemplateEntity;

public interface TemplateDAO_I {
	public TemplateEntity findByUUID(String uuid) ;

	public List<TemplateEntity> findAll() ;

	public String save(TemplateEntity entity);

	public int update(TemplateEntity entity);

	public int delete(String uuid) ;

}
