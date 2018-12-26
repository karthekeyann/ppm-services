package com.cft.hogan.platform.ppm.services.massmaintenance.bean;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cft.hogan.platform.ppm.services.massmaintenance.util.excel.ExcelStyle;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ExportTaskBean {
	
	@NotNull (message = "Invalid psets")
	private List<ParameterBean> psets;
	
	private ExcelStyle excelSettings;
	
	private Boolean singleTab = false;

}
