package com.cft.hogan.platform.ppm.services.massmaintenance.bean;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ParameterBean implements Comparable<Object> {
	
	private String number;
	
	private String name;
	
	private String companyID;
	
	private String applicationID;
	
	private String effectiveDate;

	@Override
	public int compareTo(Object o) {
		return Integer.parseInt(this.number) - Integer.parseInt(((ParameterBean) o).getNumber());
	}

}
