package com.cft.hogan.platform.ppm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.api.facade.ParameterFacade;

@RestController
@RequestMapping("/applications/{applicationID}/parameters")
public class ParameterController {

	@Autowired
	ParameterFacade parameterFacade;

	@GetMapping
	public ResponseEntity<Object> get(@PathVariable(value = "applicationID") String applicationID){
		return ResponseEntity.ok().body(parameterFacade.getParameters(applicationID));
	}

}
