package com.cft.hogan.platform.ppm.api.controller.mm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.api.assembler.TemplateResourceAssembler;
import com.cft.hogan.platform.ppm.api.bean.mm.TemplateBean;
import com.cft.hogan.platform.ppm.api.facade.mm.TemplateFacade;

@RestController
@RequestMapping("/ppm/mass-maintenance/templates")
public class TemplateController {

	@Autowired
	private TemplateFacade templateFacade;

	@Autowired
	private TemplateResourceAssembler assembler;

	@GetMapping
	public Resources<Resource<TemplateBean>> findAll() {
		List<Resource<TemplateBean>> templateBeans = templateFacade.findAll().stream().map(assembler::toResource)
				.collect(Collectors.toList());
		return new Resources<>(templateBeans, linkTo(methodOn(TemplateController.class).findAll()).withSelfRel());
	}

	@GetMapping("/{id}")
	public Resource<TemplateBean> findByUUID(@PathVariable(value = "id") String templateId) {
		return assembler.toResource(templateFacade.findByUUID(templateId));
	}

	@PostMapping
	public Resource<TemplateBean> save(@Valid @RequestBody TemplateBean templateBean) {
		return assembler.toResource(templateFacade.save(templateBean));
	}

	@PutMapping("/{id}")
	public Resource<TemplateBean> update(@PathVariable(value = "id") String templateId,@Valid @RequestBody TemplateBean bean) {
		return assembler.toResource(templateFacade.update(templateId, bean));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") String templateId) {
		templateFacade.delete(templateId);
		return ResponseEntity.noContent().build();
	}

}
