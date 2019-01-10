package com.cft.hogan.platform.ppm.api.bean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CompanyBean  implements Comparable<Object> {

	String id;

	String name;

	@Override
	public int compareTo(Object o) {
		int num = 0;
		try {
			num = Integer.parseInt(this.id) - Integer.parseInt(((CompanyBean) o).getId());
		}catch(Exception e) {
			//DO NOTHING to handle all scenario
		}
		return num;
	}
}
