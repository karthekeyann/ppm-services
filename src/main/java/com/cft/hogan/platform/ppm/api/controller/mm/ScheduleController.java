package com.cft.hogan.platform.ppm.api.controller.mm;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.api.assembler.ScheduleResourceAssembler;
import com.cft.hogan.platform.ppm.api.bean.mm.ScheduleBatchBean;
import com.cft.hogan.platform.ppm.api.bean.mm.ScheduleBean;
import com.cft.hogan.platform.ppm.api.facade.mm.ScheduleFacade;


@RestController
public class ScheduleController {

	@Autowired
	private ScheduleFacade scheduleFacade;

	@Autowired
	private ScheduleResourceAssembler assembler;

	@GetMapping("/v1/parameter/mass-maintenance/schedules")
	public Resources<Resource<ScheduleBean>> findAll(@RequestParam("type") String type) {
		List<Resource<ScheduleBean>> scheduleBeans = scheduleFacade.findByType(type).stream().map(assembler::toResource).collect(Collectors.toList());
		return new Resources<>(scheduleBeans, linkTo(methodOn(ScheduleController.class).findAll(type)).withSelfRel());
	}

	@GetMapping("/v1/parameter/mass-maintenance/schedules/{id}")
	public Resource<ScheduleBean> findByUUID(@PathVariable(value = "id") String scheduleId) {
		return assembler.toResource(scheduleFacade.findByUUID(scheduleId));
	}
	
	@GetMapping("/v1/parameter/mass-maintenance/schedules/batch")
	public List<ScheduleBatchBean> batch(@RequestParam("type") String type, @Nullable @RequestParam("bp-date") Date date) {
		return scheduleFacade.schedulesForBatch(date, type);
	}

	@PostMapping("/v1/parameter/mass-maintenance/schedules")
	public Resource<ScheduleBean> save(@Valid @RequestBody ScheduleBean scheduleBean) {
		return assembler.toResource(scheduleFacade.save(scheduleBean));
	}

	@PutMapping("/v1/parameter/mass-maintenance/schedules/{id}")
	public Resource<ScheduleBean> update(@PathVariable(value = "id") String scheduleId,	@Valid @RequestBody ScheduleBean scheduleBean) {
		return assembler.toResource(scheduleFacade.update(scheduleBean,scheduleId ));
	}

	@DeleteMapping("/v1/parameter/mass-maintenance/schedules/{id}")
	public ResponseEntity<?> deleteTask(@PathVariable(value = "id") String taskId) {
		scheduleFacade.delete(taskId);
		return ResponseEntity.noContent().build();	
	}

}
