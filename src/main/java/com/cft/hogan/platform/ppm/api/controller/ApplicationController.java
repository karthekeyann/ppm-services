package com.cft.hogan.platform.ppm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.api.facade.ApplicationFacade;

@RestController
@RequestMapping("/applications")
public class ApplicationController {
	
	@Autowired
	ApplicationFacade applicationFacade;

	@GetMapping
	public ResponseEntity<Object> get(){
		return ResponseEntity.ok().body(applicationFacade.getApplications());
	}

}
