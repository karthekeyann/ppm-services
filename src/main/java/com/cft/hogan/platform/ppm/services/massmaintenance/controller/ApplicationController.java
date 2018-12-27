package com.cft.hogan.platform.ppm.services.massmaintenance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.services.massmaintenance.service.ApplicationService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/v1/parameter/mass-maintenance/applications")
public class ApplicationController {
	
	@Autowired
	ApplicationService applicationService;

	@GetMapping
	public ResponseEntity<Object> get(){
		return ResponseEntity.ok().body(applicationService.getApplications());
	}

}
