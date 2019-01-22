package com.cft.hogan.platform.ppm.api.dao.mm;

import java.util.List;

import com.cft.hogan.platform.ppm.api.entity.mm.TemplateParameterEntity;

public interface TemplatePSetDAO_I {
	public int save(List<TemplateParameterEntity> psets);
	
	public List<TemplateParameterEntity> findByTemplateUUID(String templateUUID);

	public int deleteByTemplateUUID(String templateUUID);
}
