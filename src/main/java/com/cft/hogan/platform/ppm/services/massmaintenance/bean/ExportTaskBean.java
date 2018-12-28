package com.cft.hogan.platform.ppm.services.massmaintenance.bean;

import java.util.List;

import com.cft.hogan.platform.ppm.services.massmaintenance.util.excel.ExcelStyle;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ExportTaskBean {
	
	private List<ParameterBean> psets;
	
	private ExcelStyle excelSettings;
	
	private Boolean singleTab = false;

}
