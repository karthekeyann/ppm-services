package com.cft.hogan.platform.ppm.api.controller.mm;

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

import com.cft.hogan.platform.ppm.api.bean.mm.ExportTaskBean;
import com.cft.hogan.platform.ppm.api.facade.mm.ExportTaskFacade;

@RestController
@RequestMapping("/ppm/mass-maintenance/export-tasks")
public class ExportTaskController {
	
	@PostMapping
	public ResponseEntity<byte[]> getFile(@Valid @NotNull @RequestBody ExportTaskBean exportTaskBean) {
		
		ExportTaskFacade taskService = new ExportTaskFacade();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccessControlExposeHeaders(Collections.singletonList("Content-Disposition"));
		headers.set("Content-Disposition", "attachment; filename=download.xls");
		headers.setAccessControlExposeHeaders(Collections.singletonList("Content-Type"));
		headers.set("Content-Type","application/vnd.ms-excel");
		return new ResponseEntity<>(taskService.save(exportTaskBean), headers, HttpStatus.OK);
	}
}
