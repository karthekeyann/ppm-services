package com.cft.hogan.platform.ppm.api.entity.mm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="PPM_MM_TEMPLATE_PSET", schema="CELPPM")
public class TemplatePSetEntity implements Serializable {
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
	String uuid;
	
	@Column(name = "TEMPLATE_UUID")
	String templateUUID;
	
	@Column(name = "NUMBER")
	@NotNull (message = "Invalid number")
	String number;
	
	@Column(name = "NAME")
	String name;
	
	@Column(name = "COMPANY_ID")
	String companyID;
	
	@Column(name = "APPLICATION_ID")
	@NotNull (message = "Invalid application ID")
	String applicationID;
	
	@Column(name = "EFF_DATE")
	String effectiveDate;
	
	@Column(name = "CREATED_BY")
	String createdBy;

	@Column(name = "CREATED_TS")
	String createdOn;

	@Column(name = "MODIFIED_BY")
	String modifiedBy;

	@Column(name = "MODIFIED_TS")
	String modifiedOn;
	
}
