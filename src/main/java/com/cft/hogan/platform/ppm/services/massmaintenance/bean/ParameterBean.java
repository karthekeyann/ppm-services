package com.cft.hogan.platform.ppm.services.massmaintenance.bean;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ParameterBean implements Comparable<Object> {
	
	@NotNull (message = "Invalid number")
	private String number;
	
	private String name;
	
	private String companyID;
	
	@NotNull (message = "Invalid applicationID")
	private String applicationID;
	
	private String effectiveDate;

	@Override
	public int compareTo(Object o) {
		return Integer.parseInt(this.number) - Integer.parseInt(((ParameterBean) o).getNumber());
	}

}
