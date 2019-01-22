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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.api.assembler.ScheduleResourceAssembler;
import com.cft.hogan.platform.ppm.api.bean.mm.ScheduleBatchBean;
import com.cft.hogan.platform.ppm.api.bean.mm.ScheduleTaskBean;
import com.cft.hogan.platform.ppm.api.facade.mm.ScheduleTaskFacade;


@RestController
@RequestMapping("/ppm/mass-maintenance/schedule-tasks")
public class ScheduleTaskController {

	@Autowired
	private ScheduleTaskFacade scheduleTaskFacade;

	@Autowired
	private ScheduleResourceAssembler assembler;

	@GetMapping
	public Resources<Resource<ScheduleTaskBean>> findAll(@RequestParam("type") String type) {
		List<Resource<ScheduleTaskBean>> scheduleTaskBeans = scheduleTaskFacade.findByType(type).stream().map(assembler::toResource).collect(Collectors.toList());
		return new Resources<>(scheduleTaskBeans, linkTo(methodOn(ScheduleTaskController.class).findAll(type)).withSelfRel());
	}

	@GetMapping("/{id}")
	public Resource<ScheduleTaskBean> findByUUID(@PathVariable(value = "id") String scheduleId) {
		return assembler.toResource(scheduleTaskFacade.findByUUID(scheduleId));
	}
	
	@GetMapping("/batch")
	public List<ScheduleBatchBean> batch(@RequestParam("type") String type, @Nullable @RequestParam("bp-date") Date date) {
		return scheduleTaskFacade.schedulesForBatch(date, type);
	}

	@PostMapping
	public Resource<ScheduleTaskBean> save(@Valid @RequestBody ScheduleTaskBean scheduleTaskBean) {
		return assembler.toResource(scheduleTaskFacade.save(scheduleTaskBean));
	}

	@PutMapping("/{id}")
	public Resource<ScheduleTaskBean> update(@PathVariable(value = "id") String scheduleId,	@Valid @RequestBody ScheduleTaskBean scheduleTaskBean) {
		return assembler.toResource(scheduleTaskFacade.update(scheduleTaskBean,scheduleId ));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTask(@PathVariable(value = "id") String taskId) {
		scheduleTaskFacade.delete(taskId);
		return ResponseEntity.noContent().build();	
	}

}
