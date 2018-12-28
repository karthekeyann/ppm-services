package com.cft.hogan.platform.ppm.services.massmaintenance.controller;

import java.util.Collections;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cft.hogan.platform.ppm.services.massmaintenance.bean.ExportTaskBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.service.ExportService;

@RestController
@RequestMapping("/v1/parameter/mass-maintenance/exports")
public class ExportController {
	
	@PostMapping
	public ResponseEntity<byte[]> getFile(@Valid @NotNull @RequestBody ExportTaskBean exportTaskBean) {
		
		ExportService taskService = new ExportService();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccessControlExposeHeaders(Collections.singletonList("Content-Disposition"));
		headers.set("Content-Disposition", "attachment; filename=download.xls");
		headers.setAccessControlExposeHeaders(Collections.singletonList("Content-Type"));
		headers.set("Content-Type","application/vnd.ms-excel");
		return new ResponseEntity<>(taskService.save(exportTaskBean), headers, HttpStatus.OK);
	}
}
