package com.cft.hogan.platform.ppm.services.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.cft.hogan.platform.ppm.services.massmaintenance.bean.ImportTaskBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.controller.ImportTaskController;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Constants;

@Component
public class ImportTaskResourceAssembler implements ResourceAssembler<ImportTaskBean, Resource<ImportTaskBean>> {

	@Override
	public Resource<ImportTaskBean> toResource(ImportTaskBean task) {
		return new Resource<>(task,
				linkTo(methodOn(ImportTaskController.class).findByUUID(task.getUuid())).withSelfRel(),
				linkTo(methodOn(ImportTaskController.class).findAll(Constants.INPROGRESS)).withRel("importTasks"));
	}
}
