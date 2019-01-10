package com.cft.hogan.platform.ppm.api.dao.mm;

import java.util.List;

import com.cft.hogan.platform.ppm.api.entity.mm.TemplatePSetEntity;

public interface TemplatePSetDAO_I {
	public int save(List<TemplatePSetEntity> psets);
	
	public List<TemplatePSetEntity> findByTemplateUUID(String templateUUID);

	public int deleteByTemplateUUID(String templateUUID);
}
