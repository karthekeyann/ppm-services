package com.cft.hogan.platform.ppm.services.massmaintenance.bean;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cft.hogan.platform.ppm.services.massmaintenance.entity.TemplatePSetEntity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TemplateBean {
	
	private String uuid;

	@NotNull (message = "Invalid name")
	private String name;
	
	@Valid @NotNull (message = "Invalid psets")
	private List<TemplatePSetEntity> psets;

	private String createdBy;

	private String createdOn;

	private String modifiedBy;

	private String modifiedOn;
	
}
