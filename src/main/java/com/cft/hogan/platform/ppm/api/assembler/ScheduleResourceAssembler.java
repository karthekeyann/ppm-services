package com.cft.hogan.platform.ppm.api.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.cft.hogan.platform.ppm.api.bean.mm.ScheduleTaskBean;
import com.cft.hogan.platform.ppm.api.controller.mm.ScheduleTaskController;

@Component
public class ScheduleResourceAssembler implements ResourceAssembler<ScheduleTaskBean, Resource<ScheduleTaskBean>> {

	@Override
	public Resource<ScheduleTaskBean> toResource(ScheduleTaskBean scheduleTaskBean) {
		return new Resource<>(scheduleTaskBean,
				linkTo(methodOn(ScheduleTaskController.class).findByUUID(scheduleTaskBean.getUuid())).withSelfRel(),
				linkTo(methodOn(ScheduleTaskController.class).findAll(scheduleTaskBean.getType())).withRel("schedules"));
	}
}
