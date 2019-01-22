package com.cft.hogan.platform.ppm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.api.facade.mm.TokenFacade;

@RestController
@RequestMapping("/ppm/user/token")
public class TokenController {

	@Autowired
	TokenFacade service;

	@GetMapping
	public ResponseEntity<Object> getUser(){
		return ResponseEntity.ok().body(service.getUser());
	}
	
	@PostMapping
	public ResponseEntity<Object> getToken(){
		return ResponseEntity.ok().body(service.getToken());
	}


	@DeleteMapping
	public ResponseEntity<Object> logOut(){
		service.logout();
		return ResponseEntity.noContent().build();
	}
}
