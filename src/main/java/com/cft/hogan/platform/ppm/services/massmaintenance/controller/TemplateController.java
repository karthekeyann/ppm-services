package com.cft.hogan.platform.ppm.services.massmaintenance.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.services.assembler.TemplateResourceAssembler;
import com.cft.hogan.platform.ppm.services.massmaintenance.bean.TemplateBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.service.TemplateService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	@Autowired
	private TemplateResourceAssembler assembler;

	@GetMapping("/v1/parameter/mass-maintenance/templates")
	public Resources<Resource<TemplateBean>> findAll() {
		List<Resource<TemplateBean>> templateBeans = templateService.findAll().stream().map(assembler::toResource)
				.collect(Collectors.toList());
		return new Resources<>(templateBeans, linkTo(methodOn(TemplateController.class).findAll()).withSelfRel());
	}

	@GetMapping("/v1/parameter/mass-maintenance/templates/{id}")
	public Resource<TemplateBean> findByUUID(@PathVariable(value = "id") String templateId) {
		return assembler.toResource(templateService.findByUUID(templateId));
	}

	@PostMapping("/v1/parameter/mass-maintenance/templates")
	public Resource<TemplateBean> save(@Valid @RequestBody TemplateBean templateBean) {
		return assembler.toResource(templateService.save(templateBean));
	}

	@PutMapping("/v1/parameter/mass-maintenance/templates/{id}")
	public Resource<TemplateBean> update(@PathVariable(value = "id") String templateId,@Valid @RequestBody TemplateBean bean) {
		return assembler.toResource(templateService.update(templateId, bean));
	}

	@DeleteMapping("/v1/parameter/mass-maintenance/templates/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") String templateId) {
		templateService.delete(templateId);
		return ResponseEntity.noContent().build();
	}

}
