package com.cft.hogan.platform.ppm.api.dao.mm;

import java.util.List;

import com.cft.hogan.platform.ppm.api.entity.mm.TemplateEntity;

public interface TemplateDAO_I {
	public TemplateEntity findByUUID(String uuid) ;

	public List<TemplateEntity> findAll() ;

	public String save(TemplateEntity entity);

	public int update(TemplateEntity entity);

	public int delete(String uuid) ;

}
