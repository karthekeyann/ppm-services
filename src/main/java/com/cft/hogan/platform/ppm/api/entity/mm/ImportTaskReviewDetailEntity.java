package com.cft.hogan.platform.ppm.api.entity.mm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="PPM_MM_IMPORT_TASK_DETAILS", schema="CELPPM")
public class ImportTaskReviewDetailEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID",
			strategy = "org.hibernate.id.UUIDGenerator")*/
	@Id
	@Column(name = "UUID")
	private String uuid;

	@Column(name = "IM_TASK_UUID")
	private String importTaskUUID;

	@Column(name = "APPLICATION_ID")
	private String applicationID;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "PSET_NUMBER")
	private String pSetNumber;

	@Column(name = "PSET_NAME")
	private String pSetName;

	@Column(name = "PSET_KEY")
	private String pSetKey;

	@Column(name = "COMPANY_ID")
	private String companyID;

	@Column(name = "EFF_DATE")
	private String effectiveDate;

	@Column(name = "EXP_DATE")
	private String expiryDate;

	@Column(name = "ACTION")
	private String action;

	@Column(name = "RESULT")
	private String result;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_TS")
	private String createdOn;
		
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name = "MODIFIED_TS")
	private String modifiedOn;

}
