package com.cft.hogan.platform.ppm.api.util.mm.excel;

public class WorkbookStyle {
	private CellStyle pcdNameandNumberStyle;
	private CellStyle headerRowReqFieldsStyle;
	private CellStyle headerRowNonReqFieldsStyle;
	private CellStyle headerRowDataFieldsStyle;
	private CellStyle dataRowEvenFieldsStyle;
	private CellStyle dataRowOddFieldsStyle;
	private CellStyle intRateMtrxExtndTierStyle;
	
	public WorkbookStyle(ExcelStyle styleXml) {
		this.pcdNameandNumberStyle = new CellStyle(styleXml.getPcdNameFontType(),styleXml.getPcdNameStyle(),Integer.parseInt(styleXml.getPcdNameFontSize()),styleXml.getPcdNameFontColor(),styleXml.getPcdNameCellColor());
		this.headerRowReqFieldsStyle = new CellStyle(styleXml.getHdrRowReqFldsFontType(),styleXml.getHdrRowReqFldsStyle(),Integer.parseInt(styleXml.getHdrRowReqFldsFontSize()),styleXml.getHdrRowReqFldsFontColor(),styleXml.getHdrRowReqFldsCellColor());
		this.headerRowNonReqFieldsStyle = new CellStyle(styleXml.getHdrRowNonReqFldsFontType(),styleXml.getHdrRowNonReqFldsStyle(),Integer.parseInt(styleXml.getHdrRowNonReqFldsFontSize()),styleXml.getHdrRowNonReqFldsFontColor(),styleXml.getHdrRowNonReqFldsCellColor());
		this.headerRowDataFieldsStyle = new CellStyle(styleXml.getHdrRowDataFldsFontType(),styleXml.getHdrRowDataFldsStyle(),Integer.parseInt(styleXml.getHdrRowDataFldsFontSize()),styleXml.getHdrRowDataFldsFontColor(),styleXml.getHdrRowDataFldsCellColor());
		this.dataRowEvenFieldsStyle = new CellStyle(styleXml.getDataRowEvenFontType(),styleXml.getDataRowEvenStyle(),Integer.parseInt(styleXml.getDataRowEvenFontSize()),styleXml.getDataRowEvenFontColor(),styleXml.getDataRowEvenCellColor());
		this.dataRowOddFieldsStyle = new CellStyle(styleXml.getDataRowOddFontType(),styleXml.getDataRowOddStyle(),Integer.parseInt(styleXml.getDataRowOddFontSize()),styleXml.getDataRowOddFontColor(),styleXml.getDataRowOddCellColor());
		this.intRateMtrxExtndTierStyle = new CellStyle(styleXml.getIntRtMtxExtendedTierFontType(),styleXml.getIntRtMtxExtendedTierStyle(),Integer.parseInt(styleXml.getIntRtMtxExtendedTierFontSize()),styleXml.getIntRtMtxExtendedTierFontColor(),styleXml.getIntRtMtxExtendedTierCellColor());
	}
	
	public CellStyle getPcdNameandNumberStyle() {
		return pcdNameandNumberStyle;
	}
	public void setPcdNameandNumberStyle(CellStyle pcdNameandNumberStyle) {
		this.pcdNameandNumberStyle = pcdNameandNumberStyle;
	}
	public CellStyle getHeaderRowReqFieldsStyle() {
		return headerRowReqFieldsStyle;
	}
	public void setHeaderRowReqFieldsStyle(CellStyle headerRowReqFieldsStyle) {
		this.headerRowReqFieldsStyle = headerRowReqFieldsStyle;
	}
	public CellStyle getHeaderRowNonReqFieldsStyle() {
		return headerRowNonReqFieldsStyle;
	}
	public void setHeaderRowNonReqFieldsStyle(CellStyle headerRowNonReqFieldsStyle) {
		this.headerRowNonReqFieldsStyle = headerRowNonReqFieldsStyle;
	}
	public CellStyle getHeaderRowDataFieldsStyle() {
		return headerRowDataFieldsStyle;
	}
	public void setHeaderRowDataFieldsStyle(CellStyle headerRowDataFieldsStyle) {
		this.headerRowDataFieldsStyle = headerRowDataFieldsStyle;
	}
	public CellStyle getDataRowEvenFieldsStyle() {
		return dataRowEvenFieldsStyle;
	}
	public void setDataRowEvenFieldsStyle(CellStyle dataRowEvenFieldsStyle) {
		this.dataRowEvenFieldsStyle = dataRowEvenFieldsStyle;
	}
	public CellStyle getDataRowOddFieldsStyle() {
		return dataRowOddFieldsStyle;
	}
	public void setDataRowOddFieldsStyle(CellStyle dataRowOddFieldsStyle) {
		this.dataRowOddFieldsStyle = dataRowOddFieldsStyle;
	}
	public CellStyle getIntRateMtrxExtndTierStyle() {
		return intRateMtrxExtndTierStyle;
	}
	public void setIntRateMtrxExtndTierStyle(CellStyle intRateMtrxExtndTierStyle) {
		this.intRateMtrxExtndTierStyle = intRateMtrxExtndTierStyle;
	}
}
