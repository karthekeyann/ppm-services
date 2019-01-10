package com.cft.hogan.platform.ppm.api.entity.mm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="PPM_MM_SCHEDULED_TASK" , schema="CELPPM")
public class ScheduleEntity implements Serializable {
	/**
	 * 
	 */
	static final long serialVersionUID = 1L;

	/*	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID",
			strategy = "org.hibernate.id.UUIDGenerator")*/
	@Id
	@Column(name = "UUID")
	private String uuid;

	@Column(name = "TEMPLATE_UUID")
	private String templateUUID;

	@Column(name = "NAME")
	private String name;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "EFF_DATE")
	private String effectiveDate;

	@Column(name = "FILE_PATH")
	private String filePath;

	@Column(name = "FREQUENCY")
	private String frequency;

	@Column(name = "FREQ_PATTERN")
	private String freqPattern;

	@Column(name = "TASK_OPTIONS")
	private String taskOptions;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "REMARKS")
	private String  remarks;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_TS")
	private String createdOn;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_TS")
	private String modifiedOn;
	
}
