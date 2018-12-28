package com.cft.hogan.platform.ppm.services.massmaintenance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.services.massmaintenance.service.CompanyService;

@RestController
@RequestMapping("/v1/parameter/mass-maintenance/parameters/{id}/companies")
public class CompanyController {

	@Autowired
	CompanyService companyService;

	@GetMapping
	public ResponseEntity<Object> findByID(@PathVariable(value = "id") String pcdId){
		return ResponseEntity.ok().body(companyService.getCompanyDetails(pcdId));
	}
}
