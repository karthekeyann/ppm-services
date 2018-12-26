package com.cft.hogan.platform.ppm.services.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.cft.hogan.platform.ppm.services.massmaintenance.bean.TemplateBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.controller.TemplateController;

@Component
public class TemplateResourceAssembler implements ResourceAssembler<TemplateBean, Resource<TemplateBean>> {

	@Override
	public Resource<TemplateBean> toResource(TemplateBean templateBean) {
			return new Resource<>(templateBean,
					linkTo(methodOn(TemplateController.class).findByUUID(templateBean.getUuid())).withSelfRel(),
					linkTo(methodOn(TemplateController.class).findAll()).withRel("templates"));
	}
}
