package com.cft.hogan.platform.ppm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.api.facade.CompanyFacade;

@RestController
@RequestMapping("/ppm/applications/{appID}/parameters/{id}/companies")
public class CompanyController {

	@Autowired
	CompanyFacade companyFacade;

	@GetMapping
	public ResponseEntity<Object> findByID(@PathVariable(value = "id") String pcdId){
		return ResponseEntity.ok().body(companyFacade.getCompanyDetails(pcdId));
	}
}
