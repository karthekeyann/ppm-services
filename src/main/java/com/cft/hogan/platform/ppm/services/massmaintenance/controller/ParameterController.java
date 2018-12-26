package com.cft.hogan.platform.ppm.services.massmaintenance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.services.massmaintenance.service.ParameterService;

@RestController
@CrossOrigin
public class ParameterController {

	@Autowired
	ParameterService parameterService;

	@GetMapping("/v1/parameter/mass-maintenance/applications/{applicationID}/parameters")
	public ResponseEntity<Object> get(@PathVariable(value = "applicationID") String applicationID){
		return ResponseEntity.ok().body(parameterService.getParameters(applicationID));
	}

}
