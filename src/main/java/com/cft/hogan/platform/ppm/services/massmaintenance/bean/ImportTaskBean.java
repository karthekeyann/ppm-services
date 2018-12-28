package com.cft.hogan.platform.ppm.services.massmaintenance.bean;

import java.util.List;

import com.cft.hogan.platform.ppm.services.massmaintenance.entity.ImportTaskReviewDetailEntity;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ImportTaskBean  {

	private String uuid;

	private String name;

	private String status;

	private String inputFileName;

	private String type;
	
	private String createdBy;

	private String createdOn;
	
	private String modifiedBy;

	private String modifiedOn;
	
	private List<ImportTaskReviewDetailEntity> importTaskReviewDetails;
	
}
