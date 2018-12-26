package com.cft.hogan.platform.ppm.services.massmaintenance.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="PPM_MM_TEMPLATE" , schema="CELPPM")
public class TemplateEntity implements Serializable {
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

	@Column(name = "NAME")
	private String name;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_TS")
	private String createdOn;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_TS")
	private String modifiedOn;

}
