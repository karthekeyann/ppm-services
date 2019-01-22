package com.cft.hogan.platform.ppm.api.bean.mm;

import java.util.List;

import com.cft.hogan.platform.ppm.api.entity.mm.TemplateParameterEntity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TemplateBean {
	
	private String uuid;

	private String name;
	
	private List<TemplateParameterEntity> psets;

	private String createdBy;

	private String createdOn;

	private String modifiedBy;

	private String modifiedOn;
	
}
