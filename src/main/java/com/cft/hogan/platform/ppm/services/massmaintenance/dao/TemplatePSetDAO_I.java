package com.cft.hogan.platform.ppm.services.massmaintenance.dao;

import java.util.List;

import com.cft.hogan.platform.ppm.services.massmaintenance.entity.TemplatePSetEntity;

public interface TemplatePSetDAO_I {
	public int save(List<TemplatePSetEntity> psets);
	
	public List<TemplatePSetEntity> findByTemplateUUID(String templateUUID);

	public int deleteByTemplateUUID(String templateUUID);
}
