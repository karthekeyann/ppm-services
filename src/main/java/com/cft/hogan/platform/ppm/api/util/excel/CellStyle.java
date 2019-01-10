package com.cft.hogan.platform.ppm.api.util.excel;

public class CellStyle {
	
	private String fontType;
	private String fontStyle;
	private int fontSize;
	private String fontColor;
	private String cellColor;
	
	public CellStyle(String fontType, String fontStyle, int fontSize,String fontColor, String cellColor) {
		this.fontType = fontType;
		this.fontStyle = fontStyle;
		this.fontSize = fontSize;
		this.fontColor = fontColor;
		this.cellColor = cellColor;
	}

	public String getFontType() {
		return fontType;
	}

	public void setFontType(String fontType) {
		this.fontType = fontType;
	}

	public String getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public String getFontColor() {
		return fontColor;
	}
	public byte[] getFontColorRGB() {
		byte [] colorArr=new byte[3];
		colorArr[0]=(byte)(int)Integer.valueOf(fontColor.substring( 1, 3 ), 16 );
		colorArr[1]=(byte)(int)Integer.valueOf(fontColor.substring( 3, 5 ), 16 );
		colorArr[2]=(byte)(int)Integer.valueOf(fontColor.substring( 5, 7 ), 16 );
		return colorArr;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getCellColor() {
		return cellColor;
	}
	public byte[] getCellColorRGB() {
		byte [] colorArr=new byte[3];
		colorArr[0]=(byte)(int)Integer.valueOf(cellColor.substring( 1, 3 ), 16 );
		colorArr[1]=(byte)(int)Integer.valueOf(cellColor.substring( 3, 5 ), 16 );
		colorArr[2]=(byte)(int)Integer.valueOf(cellColor.substring( 5, 7 ), 16 );
		return colorArr;
	}	
	public void setCellColor(String cellColor) {
		this.cellColor = cellColor;
	}
	

}
