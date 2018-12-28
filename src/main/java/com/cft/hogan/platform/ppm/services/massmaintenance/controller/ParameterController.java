package com.cft.hogan.platform.ppm.services.massmaintenance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.services.massmaintenance.service.ParameterService;

@RestController
@RequestMapping("/v1/parameter/mass-maintenance")
public class ParameterController {

	@Autowired
	ParameterService parameterService;

	@GetMapping("/applications/{applicationID}/parameters")
	public ResponseEntity<Object> get(@PathVariable(value = "applicationID") String applicationID){
		return ResponseEntity.ok().body(parameterService.getParameters(applicationID));
	}

}
