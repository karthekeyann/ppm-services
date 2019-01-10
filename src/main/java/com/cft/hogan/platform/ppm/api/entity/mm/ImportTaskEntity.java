package com.cft.hogan.platform.ppm.api.entity.mm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="PPM_MM_IMPORT_TASK", schema="CELPPM")
public class ImportTaskEntity implements Serializable {
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

	@Column(name = "STATUS")
	private String status;

	@Column(name = "INPUT_FILE_NAME")
	private String inputFileName;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_TS")
	private String createdOn;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_TS")
	private String modifiedOn;

}
