package com.cft.hogan.platform.ppm.api.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.cft.hogan.platform.ppm.api.bean.mm.ScheduleBean;
import com.cft.hogan.platform.ppm.api.controller.mm.ScheduleController;

@Component
public class ScheduleResourceAssembler implements ResourceAssembler<ScheduleBean, Resource<ScheduleBean>> {

	@Override
	public Resource<ScheduleBean> toResource(ScheduleBean scheduleBean) {
		return new Resource<>(scheduleBean,
				linkTo(methodOn(ScheduleController.class).findByUUID(scheduleBean.getUuid())).withSelfRel(),
				linkTo(methodOn(ScheduleController.class).findAll(scheduleBean.getType())).withRel("schedules"));
	}
}
