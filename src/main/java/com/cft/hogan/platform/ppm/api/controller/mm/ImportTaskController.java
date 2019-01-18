package com.cft.hogan.platform.ppm.api.controller.mm;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cft.hogan.platform.ppm.api.assembler.ImportTaskResourceAssembler;
import com.cft.hogan.platform.ppm.api.bean.mm.ImportTaskBean;
import com.cft.hogan.platform.ppm.api.facade.mm.ImportTaskFacade;

@RestController
@RequestMapping("/mass-maintenance/imports")
public class ImportTaskController {

	@Autowired
	ImportTaskFacade taskService;

	@Autowired
	ImportTaskResourceAssembler assembler;

	@GetMapping
	public Resources<Resource<ImportTaskBean>> findAll(@RequestParam("status") String status) {
		List<Resource<ImportTaskBean>> importTaskBeans = taskService.findByStatus(status).stream()
				.map(assembler::toResource)
				.collect(Collectors.toList());	
		return new Resources<>(importTaskBeans, 
				linkTo(methodOn(ImportTaskController.class).findAll(status)).withSelfRel());
	}

	@GetMapping("/{id}")
	public Resource<ImportTaskBean> findByUUID(@PathVariable(value = "id") String taskId) {
		return assembler.toResource(taskService.findByUUID(taskId));
	}

	@PostMapping
	public Resource<ImportTaskBean> save( @RequestParam("taskName") String taskName,@RequestParam("taskType") String taskType, @RequestParam("file") MultipartFile file) {
		return assembler.toResource(taskService.save(taskName, taskType, file));
	}	
	

	@PutMapping("/{id}")
	public ResponseEntity<Object> reload(@PathVariable(value = "id") String taskId, @RequestParam("file") MultipartFile file) {
		return ResponseEntity.ok(assembler.toResource(taskService.reload(taskId, file, "user")));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTask(@PathVariable(value = "id") String taskId) {
		taskService.delete(taskId);
		return ResponseEntity.noContent().build();	
	}

}
