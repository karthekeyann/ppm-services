package com.cft.hogan.platform.ppm.services.massmaintenance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.services.massmaintenance.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/cft/hogan/platform/ppm/services/user")
public class User {

	@Autowired
	UserService service;

	@GetMapping
	public ResponseEntity<Object> get(){
		return ResponseEntity.ok().body(service.user());
	}


	@DeleteMapping
	public ResponseEntity<Object> logOut(){
		service.logout();
		return ResponseEntity.noContent().build();
	}
}
