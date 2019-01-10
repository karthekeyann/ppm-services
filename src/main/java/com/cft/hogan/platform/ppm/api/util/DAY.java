package com.cft.hogan.platform.ppm.api.util;

public enum DAY {
	 SUNDAY("SUNDAY"),
	 MONDAY("MONDAY"), 
	 TUESDAY("TUESDAY"), 
	 WEDNESDAY("WEDNESDAY"), 
	 THURSDAY("THURSDAY"), 
	 FRIDAY("FRIDAY"), 
	 SATURDAY("SATURDAY"); 
	
	String day;
	
	DAY(String day){
		this.day = day;
	}
	
	
	public static String getDay(int i){
		switch(i){
			case 1: return DAY.SUNDAY.day;
			case 2: return DAY.MONDAY.day;
			case 3: return DAY.TUESDAY.day;
			case 4: return DAY.WEDNESDAY.day;
			case 5: return DAY.THURSDAY.day;
			case 6: return DAY.FRIDAY.day;
			case 7: return DAY.SATURDAY.day;
			default: return Constants.EMPTY;
		}
	}
}
