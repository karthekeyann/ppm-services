package com.cft.hogan.platform.ppm.services.massmaintenance.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.axis.message.MessageElement;
import org.apache.axis.message.Text;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidationHelper;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.util.StringUtils;

import com.cft.hogan.platform.ppm.services.massmaintenance.bean.CompanyBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.bean.ExportTaskBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.bean.ParameterBean;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.BadRequestException;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.BusinessException;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Constants;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.PCDService;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Utils;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.excel.ExcelStyle;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.excel.PsetElementsInfo;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.excel.WorkbookStyle;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdEntry;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItem;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItemPcdItemKey;
import com.cft.hogan.platform.ppm.services.pcd.service.client.PcdXmlRs_Type;
import com.cft.hogan.platform.ppm.services.pcd.service.client.Status_Type;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExportService {

	private static final String MESSAGE_1 = "No PCD Selected For Export";
	private static final String RT = "_Rt";
	private static final String ZERO = "0";
	private static final String RATE_TERMS_RATE_TERM = "RateTerms_RateTerm";
	private static final String RATE_TERMS_SEQ_NUM = "RateTerms_SeqNum";
	private static final String TERM_CODE = "TermCode";
	private static final String MAX_TERM = "MaxTerm";
	private static final String MIN_TERM = "MinTerm";
	private static final String MAX_BAL = "MaxBal";
	private static final String MIN_BAL = "MinBal";
	private static final String TYPE_INDICATOR = "TypeIndicator";
	private static final String CURRENCY = "Currency";
	private static final String REGION = "Region";
	private static final String MATRIX_KEY = "MatrixKey";
	private static final String ACTION = "Action";

	private static final String TIER_DATA = "TierData";
	private static final String RATES2 = "Rates";
	private static final String BAL_AMT = "BalAmt";
	private static final String RATE_TERMS = "RateTerms";
	private static final String SEQ_NUM = "_SeqNum";
	private static final String RATES = "_Rates";
	private static final String BAL_DATA = "BalData";
	private static final String SEQUENCE = "Sequence";
	private static final String NUM_OF_TERMS = "NumOfTerms";
	private static final String NUM_OF_BALANCES = "NumOfBalances";
	private static final String NUM_OF_TIERS = "NumOfTiers";
	private static final String TIER_DATA_TIER_RATE = "TierData_TierRate";
	private static final String TIER_DATA_TIER_LIMIT = "TierData_TierLimit";
	private static final String TIER_DATA_TIER_POINTS = "TierData_TierPoints";
	private static final String TIER_DATA_TIER_BASIS_FACTOR = "TierData_TierBasisFactor";
	private static final String TIER_DATA_SEQ_NUM = "TierData_SeqNum";
	private static final String TIER_MAX_RATE = "TierMaxRate";
	private static final String TIER_MIN_RATE = "TierMinRate";
	private static final String UNDERSCORE = "_";
	private static final String CO_GROUP = "CoGroup";
	private static final String HYPHEN = "-";
	private static final String PCD_2598 = "2598";
	private static final String AMPERSHAND = "@";

	// Title Settings

	private static final short TITLE_FONT_COLOR = HSSFColor.DARK_TEAL.index;
	private static final short TITLE_CELL_COLOR = HSSFColor.DARK_GREEN.index;
	// Header Keys column Settings
	private static final short HEADER_KEY_COLUMN_FONT_COLOR = HSSFColor.TEAL.index;
	private static final short HEADER_KEY_COLUMN_CELL_COLOR = HSSFColor.AQUA.index;
	// Header Non Req Keys column Settings
	private static final short HEADER_NON_REQ_KEY_COLUMN_FONT_COLOR = HSSFColor.BLUE_GREY.index;
	private static final short HEADER_NON_REQ_KEY_COLUMN_CELL_COLOR = HSSFColor.BRIGHT_GREEN.index;
	// Header data column Settings
	private static final short HEADER_DATA_COLUMN_FONT_COLOR = HSSFColor.CORAL.index;
	private static final short HEADER_DATA_COLUMN_CELL_COLOR = HSSFColor.GREY_25_PERCENT.index;
	// ODD data row Settings
	private static final short ODD_ROW_COLUMN_FONT_COLOR = HSSFColor.GREY_40_PERCENT.index;
	private static final short ODD_ROW_COLUMN_CELL_COLOR = HSSFColor.GREY_50_PERCENT.index;
	// Even data row Settings
	private static final short EVEN_ROW_COLUMN_FONT_COLOR = HSSFColor.GREY_80_PERCENT.index;
	private static final short EVEN_ROW_COLUMN_CELL_COLOR = HSSFColor.CORNFLOWER_BLUE.index;
	//
	private static final short EXTENDED_TIER_HEADER_DATA_COLUMN_FONT_COLOR = HSSFColor.INDIGO.index;
	private static final short EXTENDED_TIER_HEADER_DATA_COLUMN_CELL_COLOR = HSSFColor.LAVENDER.index;

	private HSSFCellStyle titleCellStyle = null;
	private HSSFCellStyle headerKeyNonReqCellStyle = null;
	private HSSFCellStyle headerKeyReqCellStyle = null;
	private HSSFCellStyle headerDataColumnCellStyle = null;
	private HSSFCellStyle oddRowColumnCellStyle = null;
	private HSSFCellStyle evenRowColumnCellStyle = null;
	private HSSFCellStyle extendedTierheaderKeyColumnCellStyle = null;
	
	public byte[] save(ExportTaskBean exportTaskBean) {
		byte[] response =null;
		try {
			String logMsg =Utils.getLogMsg()+"--";
			log.debug(logMsg+"Export Task request "+"--SingleTab :"+exportTaskBean.getSingleTab());
			for(ParameterBean pset:exportTaskBean.getPsets()) {
				log.debug(logMsg+new StringBuffer().append("--PCD# :").append(pset.getNumber())
						.append("--").append(pset.getApplicationID())
						.append("--").append(pset.getCompanyID())
						.append("--").append(pset.getEffectiveDate()).toString());
			}
			HSSFWorkbook workbook = null;
			ByteArrayOutputStream outputStream = null;
			try {
				outputStream = new ByteArrayOutputStream();
				exportTaskBean.setPsets(handleAllCompany(exportTaskBean.getPsets()));
				workbook = createPsetWorkBook(exportTaskBean.getPsets(),exportTaskBean.getExcelSettings(),exportTaskBean.getSingleTab());
				workbook.write(outputStream);
				response = outputStream.toByteArray();
			} finally {
				if(workbook!=null) {
					workbook.close();
				}
				if(outputStream!=null) {
					outputStream.close();
				}
			}
		}catch (Exception e) {
			Utils.handleException(e);
		}
		return response;
	}

	private HSSFWorkbook createPsetWorkBook(List<ParameterBean> selectedExportList, ExcelStyle excelStyleSetting, Boolean singleTab) throws Exception {
		HSSFWorkbook workBook  = new HSSFWorkbook();;
		PCDService service = new PCDService();
		if (!isEmptySelectedExportList(selectedExportList)) {
			ExcelStyle excelStyle = getExcelStyleVar(excelStyleSetting);
			WorkbookStyle WorkbookStyle = new WorkbookStyle(excelStyle);
			loadWorkbookStyle(WorkbookStyle, workBook);
			Map<String, String> samePCDs = new HashMap<String, String>();
			int rowNumber = 0;
			for (ParameterBean parameterBean : selectedExportList) {
				if("all".equalsIgnoreCase(parameterBean.getCompanyID())) {
					//DO NOTHING
					/*pSetBean.setCompanyID(null);
					PcdCacheRs_Type workSheetData = serviceProvider.retrievePCDItemsOfAllCompanies(pSetBean);
					status = workSheetData.getXStatus();
					pcdItemList_Type = workSheetData.getPcdItemList();*/
				}else {
					rowNumber++;
					String pSetNum = parameterBean.getNumber();

					PcdXmlRs_Type template = service.getParameterXmlTemplate(pSetNum);

					PsetElementsInfo aPsetElementsInfo = new PsetElementsInfo();
					aPsetElementsInfo.setParameterNumber(pSetNum);
					aPsetElementsInfo.setAppName(parameterBean.getApplicationID());

					if ("0".equals(String.valueOf(template.getXStatus().getStatusCode()))) {

						// Process next service call only if get template passes
						if (PCD_2598.equals(pSetNum)) {
							populateInterestRateMatrixPsetElementsInfo(pSetNum, aPsetElementsInfo);
						} else {
							populatePsetElementsInfo(pSetNum, template.getPcdItemList().getPcdItem(0), aPsetElementsInfo);
						}

						PcdXmlRs_Type workSheetData = service.retrievePCDItems(parameterBean);						
						if(singleTab)
						{
							if(!samePCDs.containsKey(pSetNum))
							{
								String workSheetName = getWorkSheetName(parameterBean, rowNumber, true);
								samePCDs.put(pSetNum, workSheetName);
								Object worksheet = createSheet(workSheetName, workBook);
								pupulatePsetWorksheet(workSheetData.getXStatus(), workSheetData.getPcdItemList(), worksheet, aPsetElementsInfo, false, false);
							}
							else
							{
								Object worksheet = getSheet(samePCDs.get(pSetNum), workBook);
								pupulatePsetWorksheet(workSheetData.getXStatus(), workSheetData.getPcdItemList(), worksheet, aPsetElementsInfo, true, false);
							}
						}
						else
						{
							Object worksheet = createSheet(getWorkSheetName(parameterBean, rowNumber, false), workBook);
							pupulatePsetWorksheet(workSheetData.getXStatus(), workSheetData.getPcdItemList(), worksheet, aPsetElementsInfo, false, true);
						}


					} else {
						// Pass error data and create new sheet with error
						Object worksheet = createSheet(getWorkSheetName(parameterBean, rowNumber, false), workBook);
						pupulatePsetWorksheet(template.getXStatus(), template.getPcdItemList(), worksheet, aPsetElementsInfo, false, false);
					}
				}
			}
		}else {
			throw new BadRequestException(MESSAGE_1);
		}
		return workBook;
	}

	private void loadWorkbookStyle(WorkbookStyle aWorkbookStyle, HSSFWorkbook workBook) {
		if (workBook != null) {
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(TITLE_FONT_COLOR, aWorkbookStyle.getPcdNameandNumberStyle().getFontColorRGB()[0],
					aWorkbookStyle.getPcdNameandNumberStyle().getFontColorRGB()[1],
					aWorkbookStyle.getPcdNameandNumberStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(TITLE_CELL_COLOR, aWorkbookStyle.getPcdNameandNumberStyle().getCellColorRGB()[0],
					aWorkbookStyle.getPcdNameandNumberStyle().getCellColorRGB()[1],
					aWorkbookStyle.getPcdNameandNumberStyle().getCellColorRGB()[2]);
			palette.setColorAtIndex(HEADER_KEY_COLUMN_FONT_COLOR,
					aWorkbookStyle.getHeaderRowReqFieldsStyle().getFontColorRGB()[0],
					aWorkbookStyle.getHeaderRowReqFieldsStyle().getFontColorRGB()[1],
					aWorkbookStyle.getHeaderRowReqFieldsStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(HEADER_KEY_COLUMN_CELL_COLOR,
					aWorkbookStyle.getHeaderRowReqFieldsStyle().getCellColorRGB()[0],
					aWorkbookStyle.getHeaderRowReqFieldsStyle().getCellColorRGB()[1],
					aWorkbookStyle.getHeaderRowReqFieldsStyle().getCellColorRGB()[2]);
			palette.setColorAtIndex(HEADER_NON_REQ_KEY_COLUMN_FONT_COLOR,
					aWorkbookStyle.getHeaderRowNonReqFieldsStyle().getFontColorRGB()[0],
					aWorkbookStyle.getHeaderRowNonReqFieldsStyle().getFontColorRGB()[1],
					aWorkbookStyle.getHeaderRowNonReqFieldsStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(HEADER_NON_REQ_KEY_COLUMN_CELL_COLOR,
					aWorkbookStyle.getHeaderRowNonReqFieldsStyle().getCellColorRGB()[0],
					aWorkbookStyle.getHeaderRowNonReqFieldsStyle().getCellColorRGB()[1],
					aWorkbookStyle.getHeaderRowNonReqFieldsStyle().getCellColorRGB()[2]);
			palette.setColorAtIndex(HEADER_DATA_COLUMN_FONT_COLOR,
					aWorkbookStyle.getHeaderRowDataFieldsStyle().getFontColorRGB()[0],
					aWorkbookStyle.getHeaderRowDataFieldsStyle().getFontColorRGB()[1],
					aWorkbookStyle.getHeaderRowDataFieldsStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(HEADER_DATA_COLUMN_CELL_COLOR,
					aWorkbookStyle.getHeaderRowDataFieldsStyle().getCellColorRGB()[0],
					aWorkbookStyle.getHeaderRowDataFieldsStyle().getCellColorRGB()[1],
					aWorkbookStyle.getHeaderRowDataFieldsStyle().getCellColorRGB()[2]);
			palette.setColorAtIndex(ODD_ROW_COLUMN_FONT_COLOR,
					aWorkbookStyle.getDataRowOddFieldsStyle().getFontColorRGB()[0],
					aWorkbookStyle.getDataRowOddFieldsStyle().getFontColorRGB()[1],
					aWorkbookStyle.getDataRowOddFieldsStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(ODD_ROW_COLUMN_CELL_COLOR,
					aWorkbookStyle.getDataRowOddFieldsStyle().getCellColorRGB()[0],
					aWorkbookStyle.getDataRowOddFieldsStyle().getCellColorRGB()[1],
					aWorkbookStyle.getDataRowOddFieldsStyle().getCellColorRGB()[2]);
			palette.setColorAtIndex(EVEN_ROW_COLUMN_FONT_COLOR,
					aWorkbookStyle.getDataRowEvenFieldsStyle().getFontColorRGB()[0],
					aWorkbookStyle.getDataRowEvenFieldsStyle().getFontColorRGB()[1],
					aWorkbookStyle.getDataRowEvenFieldsStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(EVEN_ROW_COLUMN_CELL_COLOR,
					aWorkbookStyle.getDataRowEvenFieldsStyle().getCellColorRGB()[0],
					aWorkbookStyle.getDataRowEvenFieldsStyle().getCellColorRGB()[1],
					aWorkbookStyle.getDataRowEvenFieldsStyle().getCellColorRGB()[2]);
			palette.setColorAtIndex(EXTENDED_TIER_HEADER_DATA_COLUMN_FONT_COLOR,
					aWorkbookStyle.getIntRateMtrxExtndTierStyle().getFontColorRGB()[0],
					aWorkbookStyle.getIntRateMtrxExtndTierStyle().getFontColorRGB()[1],
					aWorkbookStyle.getIntRateMtrxExtndTierStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(EXTENDED_TIER_HEADER_DATA_COLUMN_CELL_COLOR,
					aWorkbookStyle.getIntRateMtrxExtndTierStyle().getCellColorRGB()[0],
					aWorkbookStyle.getIntRateMtrxExtndTierStyle().getCellColorRGB()[1],
					aWorkbookStyle.getIntRateMtrxExtndTierStyle().getCellColorRGB()[2]);

		}
		if (this.titleCellStyle == null) {
			this.titleCellStyle = getCellStyle(aWorkbookStyle.getPcdNameandNumberStyle().getFontType(),
					Integer.parseInt(aWorkbookStyle.getPcdNameandNumberStyle().getFontStyle()),
					aWorkbookStyle.getPcdNameandNumberStyle().getFontSize(), TITLE_FONT_COLOR, TITLE_CELL_COLOR, workBook);
		}
		if (this.headerKeyReqCellStyle == null) {
			this.headerKeyReqCellStyle = getCellStyle(aWorkbookStyle.getHeaderRowReqFieldsStyle().getFontType(),
					Integer.parseInt(aWorkbookStyle.getHeaderRowReqFieldsStyle().getFontStyle()),
					aWorkbookStyle.getHeaderRowReqFieldsStyle().getFontSize(), HEADER_KEY_COLUMN_FONT_COLOR,
					HEADER_KEY_COLUMN_CELL_COLOR, workBook);
		}
		if (this.headerKeyNonReqCellStyle == null) {
			this.headerKeyNonReqCellStyle = getCellStyle(aWorkbookStyle.getHeaderRowNonReqFieldsStyle().getFontType(),
					Integer.parseInt(aWorkbookStyle.getHeaderRowNonReqFieldsStyle().getFontStyle()),
					aWorkbookStyle.getHeaderRowNonReqFieldsStyle().getFontSize(), HEADER_NON_REQ_KEY_COLUMN_FONT_COLOR,
					HEADER_NON_REQ_KEY_COLUMN_CELL_COLOR, workBook);
		}
		if (this.headerDataColumnCellStyle == null) {
			this.headerDataColumnCellStyle = getCellStyle(aWorkbookStyle.getHeaderRowDataFieldsStyle().getFontType(),
					Integer.parseInt(aWorkbookStyle.getHeaderRowDataFieldsStyle().getFontStyle()),
					aWorkbookStyle.getHeaderRowDataFieldsStyle().getFontSize(), HEADER_DATA_COLUMN_FONT_COLOR,
					HEADER_DATA_COLUMN_CELL_COLOR, workBook);
		}
		if (this.oddRowColumnCellStyle == null) {
			this.oddRowColumnCellStyle = getCellStyle(aWorkbookStyle.getDataRowOddFieldsStyle().getFontType(),
					Integer.parseInt(aWorkbookStyle.getDataRowOddFieldsStyle().getFontStyle()),
					aWorkbookStyle.getDataRowOddFieldsStyle().getFontSize(), ODD_ROW_COLUMN_FONT_COLOR,
					ODD_ROW_COLUMN_CELL_COLOR, workBook);
		}
		if (this.evenRowColumnCellStyle == null) {
			this.evenRowColumnCellStyle = getCellStyle(aWorkbookStyle.getDataRowEvenFieldsStyle().getFontType(),
					Integer.parseInt(aWorkbookStyle.getDataRowEvenFieldsStyle().getFontStyle()),
					aWorkbookStyle.getDataRowEvenFieldsStyle().getFontSize(), EVEN_ROW_COLUMN_FONT_COLOR,
					EVEN_ROW_COLUMN_CELL_COLOR, workBook);
		}
		if (this.extendedTierheaderKeyColumnCellStyle == null) {
			this.extendedTierheaderKeyColumnCellStyle = getCellStyle(
					aWorkbookStyle.getIntRateMtrxExtndTierStyle().getFontType(),
					Integer.parseInt(aWorkbookStyle.getIntRateMtrxExtndTierStyle().getFontStyle()),
					aWorkbookStyle.getIntRateMtrxExtndTierStyle().getFontSize(),
					EXTENDED_TIER_HEADER_DATA_COLUMN_FONT_COLOR, EXTENDED_TIER_HEADER_DATA_COLUMN_CELL_COLOR, workBook);
		}
	}

	private ExcelStyle getExcelStyleVar(ExcelStyle customSettings) {
		ExcelStyle finalSettings = new ExcelStyle();
		if(customSettings!=null) {
			if(customSettings.getPcdNameFontType()!=null) {
				finalSettings.setPcdNameFontType(customSettings.getPcdNameFontType());
			}

			//TODO set other settings
		}
		return finalSettings;
	}

	private boolean isEmptySelectedExportList(List<ParameterBean> selectedExportList) {
		if (selectedExportList == null || selectedExportList.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * @param pset
	 * @param aPsetElementsInfo
	 */
	private void populateInterestRateMatrixPsetElementsInfo(String pset, PsetElementsInfo aPsetElementsInfo) {
		int columnNumber = 0;
		columnNumber = populatePsetInfoWithCommonElements(aPsetElementsInfo, columnNumber);

		// Add Key Elements to KeyList
		aPsetElementsInfo.addInKeyElements(MATRIX_KEY);
		aPsetElementsInfo.addInKeyElements(REGION);
		aPsetElementsInfo.addInKeyElements(CURRENCY);
		aPsetElementsInfo.addInKeyElements(SEQUENCE);
		aPsetElementsInfo.addInPcdKeyElements(MATRIX_KEY);
		aPsetElementsInfo.addInPcdKeyElements(REGION);
		aPsetElementsInfo.addInPcdKeyElements(CURRENCY);
		aPsetElementsInfo.addInPcdKeyElements(SEQUENCE);

		// Add non repeating Elements to non RepeatingList
		aPsetElementsInfo.addInNonRepeatingElements(TYPE_INDICATOR);
		aPsetElementsInfo.addInNonRepeatingElements(MIN_BAL);
		aPsetElementsInfo.addInNonRepeatingElements(MAX_BAL);
		aPsetElementsInfo.addInNonRepeatingElements(MIN_TERM);
		aPsetElementsInfo.addInNonRepeatingElements(MAX_TERM);
		aPsetElementsInfo.addInNonRepeatingElements(TERM_CODE);
		aPsetElementsInfo.addInNonRepeatingElements(NUM_OF_BALANCES);
		aPsetElementsInfo.addInNonRepeatingElements(NUM_OF_TERMS);
		aPsetElementsInfo.addInNonRepeatingElements(NUM_OF_TIERS);
		aPsetElementsInfo.addInNonRepeatingElements(TIER_MIN_RATE);
		aPsetElementsInfo.addInNonRepeatingElements(TIER_MAX_RATE);

		// Add Key Elements To Map
		aPsetElementsInfo.addInHeaderToColumnMap(MATRIX_KEY, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(REGION, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(CURRENCY, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(SEQUENCE, columnNumber++);

		// Add non repeating Elements of Default View To Map
		aPsetElementsInfo.addInHeaderToColumnMap(TYPE_INDICATOR, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(MIN_BAL, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(MAX_BAL, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(MIN_TERM, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(MAX_TERM, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(TERM_CODE, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(NUM_OF_BALANCES, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(NUM_OF_TERMS, columnNumber++);

		// Add Rate Terms To Map
		aPsetElementsInfo.addInHeaderToColumnMap(RATE_TERMS_SEQ_NUM, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(RATE_TERMS_RATE_TERM, columnNumber++);

		// Add Balance Data To Map
		for (int index = 1; index <= 25; index++) {
			String colIndex = Utils.leftPad(index + Constants.EMPTY, 2, ZERO);
			aPsetElementsInfo.addInHeaderToColumnMap(BAL_DATA + colIndex + BAL_AMT + colIndex, columnNumber++);
			aPsetElementsInfo.addInHeaderToColumnMap(BAL_DATA + colIndex + RATES + colIndex + SEQ_NUM, columnNumber++);
			aPsetElementsInfo.addInHeaderToColumnMap(BAL_DATA + colIndex + RATES + colIndex + RT + colIndex,
					columnNumber++);
		}

		// Add Non Repeating elements of Extended Tier To Map
		aPsetElementsInfo.addInHeaderToColumnMap(NUM_OF_TIERS, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(TIER_MIN_RATE, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(TIER_MAX_RATE, columnNumber++);

		// Add Repeating elements of Extended Tier To Map
		aPsetElementsInfo.addInHeaderToColumnMap(TIER_DATA_SEQ_NUM, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(TIER_DATA_TIER_BASIS_FACTOR, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(TIER_DATA_TIER_POINTS, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(TIER_DATA_TIER_LIMIT, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(TIER_DATA_TIER_RATE, columnNumber++);

		try {
			// Set Resource reader
			aPsetElementsInfo.setLables(getLabelsProperties(pset));
		}catch(Exception e) {
			Utils.handleException(e);
		}
	}

	/**
	 * @param aPsetElementsInfo
	 * @param columnNumber
	 * @return
	 */
	private int populatePsetInfoWithCommonElements(PsetElementsInfo aPsetElementsInfo, int columnNumber) {
		// Assign column numbers first
		aPsetElementsInfo.addInHeaderToColumnMap(ACTION, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(Constants.CDMF_FMT_COID, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(Constants.PCD_EFF_DATE, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(Constants.PCD_EXP_DATE, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(Constants.CDMF_OWNER_APP, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(Constants.CDMF_HIGH_USE_FLAG, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(Constants.CDMF_CC_NUM, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(Constants.CDMF_CHG_DT, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(Constants.CDMF_CHG_TM, columnNumber++);
		aPsetElementsInfo.addInHeaderToColumnMap(Constants.CDMF_CHG_BY, columnNumber++);
		// Add key to keylist
		aPsetElementsInfo.addInKeyElements(ACTION);
		aPsetElementsInfo.addInKeyElements(Constants.CDMF_FMT_COID);
		aPsetElementsInfo.addInKeyElements(Constants.PCD_EFF_DATE);
		aPsetElementsInfo.addInKeyElements(Constants.PCD_EXP_DATE);
		aPsetElementsInfo.addInKeyElements(Constants.CDMF_OWNER_APP);
		aPsetElementsInfo.addInKeyElements(Constants.CDMF_HIGH_USE_FLAG);
		aPsetElementsInfo.addInKeyElements(Constants.CDMF_CC_NUM);
		aPsetElementsInfo.addInKeyElements(Constants.CDMF_CHG_DT);
		aPsetElementsInfo.addInKeyElements(Constants.CDMF_CHG_TM);
		aPsetElementsInfo.addInKeyElements(Constants.CDMF_CHG_BY);
		return columnNumber;
	}


	/**
	 * @param pset
	 * @param pcdItem
	 * @param aPsetElementsInfo
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private void populatePsetElementsInfo(String pset,PcdItemList_TypePcdItem pcdItem , PsetElementsInfo aPsetElementsInfo) throws Exception {
		int columnNumber = 0;
		columnNumber = populatePsetInfoWithCommonElements(aPsetElementsInfo, columnNumber);
		MessageElement[] elements = getXMLKeyElements(pcdItem);
		if (elements != null) {
			for (MessageElement node : elements) {
				aPsetElementsInfo.addInHeaderToColumnMap(node.getName(), columnNumber++);
				aPsetElementsInfo.addInKeyElements(node.getName());
				aPsetElementsInfo.addInPcdKeyElements(node.getName());
			}
		}
		PcdEntry pcdEntry = pcdItem.getPcdEntry();
		if (pcdEntry != null) {
			MessageElement[] entryElements = pcdEntry.get_any();
			for (MessageElement node : entryElements) {
				List<MessageElement> childs = node.getChildren();
				if (childs != null && !childs.isEmpty() && childs.get(0) instanceof MessageElement) {
					aPsetElementsInfo.addInRepeatingElements(node.getName());
					for (MessageElement child : childs) {
						aPsetElementsInfo.addInHeaderToColumnMap(node.getName() + UNDERSCORE + child.getNodeName(), columnNumber++);
					}
				} else {
					aPsetElementsInfo.addInNonRepeatingElements(node.getName());
					aPsetElementsInfo.addInHeaderToColumnMap(node.getName(), columnNumber++);
				}
			}
		}
		try {
			// Set Resource reader
			aPsetElementsInfo.setLables(getLabelsProperties(pset));
		}catch(Exception e) {
			Utils.handleException(e);
		}
	}


	private MessageElement[] getXMLKeyElements(PcdItemList_TypePcdItem pcdItem) {
		if(pcdItem.getCdmfCdkKey()!=null) {
			return pcdItem.getCdmfCdkKey().get_any();
		}else if(pcdItem.getCdmfRegKey()!=null) {
			return pcdItem.getCdmfRegKey().get_any();
		}else if(pcdItem.getCdmfSimKey()!=null) {
			return pcdItem.getCdmfSimKey().get_any();
		}
		return null;
	}

	private MessageElement[] getKeyElements(PcdItemList_TypePcdItemPcdItemKey pcdItemKey) {
		if(pcdItemKey.getCdmfCdkKey()!=null) {
			return pcdItemKey.getCdmfCdkKey().get_any();
		}else if(pcdItemKey.getCdmfRegKey()!=null) {
			return pcdItemKey.getCdmfRegKey().get_any();
		}else if(pcdItemKey.getCdmfSimKey()!=null) {
			return pcdItemKey.getCdmfSimKey().get_any();
		}
		return null;
	}


	private String getWorkSheetName(ParameterBean parameterBean, int rowNumber, Boolean singleTab) {
		String parameterNumber = parameterBean.getNumber();
		String company = parameterBean.getCompanyID();
		String effectiveDate = String.valueOf(parameterBean.getEffectiveDate());
		StringBuffer sheetName = new StringBuffer(parameterNumber);
		if(!singleTab) {
			sheetName.append(getWorksheetNameSuffix(company));
		}
		sheetName.append(getWorksheetNameSuffix(effectiveDate)).append("(").append(rowNumber).append(")");
		return  sheetName.toString();
	}

	private String getWorksheetNameSuffix(String value) {
		if (null == value || "null".equalsIgnoreCase(value) || StringUtils.isEmpty(value)) {
			return Constants.EMPTY;
		}
		return UNDERSCORE + value;
	}

	/**
	 * @param fontName
	 * @param fontWeight
	 * @param fontSize
	 * @param fontColor
	 * @param cellColor
	 * @return
	 */
	private HSSFCellStyle getCellStyle(String fontName, int fontWeight, int fontSize, short fontColor,
			short cellColor, HSSFWorkbook workBook) {
		HSSFCellStyle dataCellStyle = workBook.createCellStyle();
		dataCellStyle.setFillForegroundColor(cellColor);
		dataCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dataCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dataCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dataCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		DataFormat fmt = workBook.createDataFormat();
		dataCellStyle.setDataFormat(fmt.getFormat(AMPERSHAND));
		HSSFFont font = workBook.createFont();
		font.setFontHeightInPoints((short) fontSize);
		font.setFontName(fontName);
		font.setColor(fontColor);
		font.setBoldweight((short) fontWeight);
		dataCellStyle.setFont(font);
		return dataCellStyle;
	}

	private HSSFSheet createSheet(String name, HSSFWorkbook workBook) {
		return workBook.createSheet(name);
	}

	private HSSFSheet getSheet(String name, HSSFWorkbook workBook) {
		return workBook.getSheet(name);
	}

	private void pupulatePsetWorksheet(Status_Type status, PcdItemList_Type pcdItemList_Type, Object worksheetObj, PsetElementsInfo aPsetElementsInfo, boolean singleTab, boolean noRecordsFlag) throws Exception {
		final int sheetTitleRow = 0;
		int elementRow = 1;
		final int labelRow = 2;
		int dataRowStartIndex = 2;
		HSSFSheet worksheet = (HSSFSheet) worksheetObj;

		if(singleTab) {
			dataRowStartIndex = worksheet.getLastRowNum();
		}
		if ("0".equals(String.valueOf(status.getStatusCode()))) {
			PcdItemList_TypePcdItem[] pcdItemList = pcdItemList_Type.getPcdItem();
			if(!singleTab) {
				// Write element names
				createHeaderRow(elementRow, worksheet, aPsetElementsInfo, false);
				// Write label names
				createHeaderRow(labelRow, worksheet, aPsetElementsInfo, true);
			}
			if (PCD_2598.equals(aPsetElementsInfo.getParameterNumber())) {
				setExtendedViewHeaderStyle(worksheet, 2, aPsetElementsInfo);
			}
			// Data row Start
			int styleIndex = 0;
			for (PcdItemList_TypePcdItem pcdItem : pcdItemList) {
				HSSFCellStyle style = null;
				if (styleIndex % 2 == 0) {
					style = oddRowColumnCellStyle;
				} else {
					style = evenRowColumnCellStyle;
				}
				if (PCD_2598.equals(aPsetElementsInfo.getParameterNumber())) {
					dataRowStartIndex = createInterestRateDataRow(worksheet, pcdItem, ++dataRowStartIndex,
							aPsetElementsInfo, style);
				} else {
					dataRowStartIndex = createDataRow(worksheet, pcdItem, ++dataRowStartIndex, aPsetElementsInfo,
							style);
				}
				styleIndex++;
			}

			// Set the width of woksheet column width based on the worksheet cells data
			HSSFRow headerRow = worksheet.getRow(1);
			int columnCount = headerRow.getPhysicalNumberOfCells();
			if(!singleTab) {
				// Write the title
				createTitleRow(sheetTitleRow, worksheet, 0, columnCount,
						aPsetElementsInfo.getParameterNumber() + HYPHEN + getLabel(aPsetElementsInfo, "parameterName"));
			}
			for (int column = 0; column < columnCount; column++) {
				worksheet.autoSizeColumn(column);
			}

			if(noRecordsFlag && pcdItemList.length==0) {
				createTitleRow(4, worksheet, 0, columnCount,"No records");
			}

		} else {
			// write title
			createTitleRow(sheetTitleRow, worksheet, 0, 10,
					aPsetElementsInfo.getParameterNumber() + HYPHEN + getLabel(aPsetElementsInfo, "parameterName"));
			// Write the error caught in service
			HSSFRow headerRow = worksheet.createRow(labelRow);
			createCell(headerRow, 0, status.getStatusDesc(), titleCellStyle);
		}
		// Create action coulmn
		createActionDropDown(worksheet, aPsetElementsInfo.getParameterNumber(), labelRow,
				worksheet.getPhysicalNumberOfRows());
		// Set Action header with required style
		worksheet.getRow(labelRow).getCell(0).setCellStyle(headerKeyReqCellStyle);
	}

	/**
	 * @param titleRowIndex
	 * @param worksheet
	 * @param startColumn
	 * @param endColumn
	 * @param title
	 */
	private void createTitleRow(int titleRowIndex, HSSFSheet worksheet, int startColumn, int endColumn, String title) {
		HSSFRow headerRow = worksheet.createRow(titleRowIndex);
		createCell(headerRow, 0, title, titleCellStyle);
		// selecting the region in Worksheet for merging data
		CellRangeAddress region = new CellRangeAddress(titleRowIndex, titleRowIndex, startColumn, endColumn - 1);
		// merging the region
		worksheet.addMergedRegion(region);

	}

	/**
	 * @param webAppAccess
	 * @param rowIndex
	 * @param worksheet
	 * @param aPsetElementsInfo
	 * @param isLabelRow
	 */
	private void createHeaderRow(int rowIndex, HSSFSheet worksheet, PsetElementsInfo aPsetElementsInfo,
			boolean isLabelRow) {
		Map<String, Integer> headerToColumnMap = aPsetElementsInfo.getHeaderToColumnMap();
		HSSFRow headerRow = worksheet.createRow(rowIndex);
		if (!isLabelRow) {
			headerRow.setZeroHeight(true);
		}
		boolean cards = false;
		if (Constants.BRP_OWNER_CARD.equals(aPsetElementsInfo.getAppName())) {
			cards = true;
		}
		for (Map.Entry<String, Integer> entry : headerToColumnMap.entrySet()) {
			short columnNumber = (short) ((int) entry.getValue());
			if (isLabelRow) {
				String label = getLabel(aPsetElementsInfo, entry.getKey());
				// override
				if (cards && Constants.CDMF_FMT_COID.equals(entry.getKey())) {
					label = getLabel(aPsetElementsInfo, CO_GROUP);
				}
				if (isKey(entry.getKey(), aPsetElementsInfo)) {
					if (Constants.CDMF_FMT_COID.equals(entry.getKey()) || Constants.PCD_EFF_DATE.equals(entry.getKey())
							|| Constants.CDMF_OWNER_APP.equals(entry.getKey())
							|| Constants.CDMF_CC_NUM.equals(entry.getKey())
							|| aPsetElementsInfo.getPcdKeyElements().contains(entry.getKey())) {
						createCell(headerRow, columnNumber, label, headerKeyReqCellStyle);
					} else {
						createCell(headerRow, columnNumber, label, headerKeyNonReqCellStyle);
					}
				} else {
					createCell(headerRow, columnNumber, label, headerDataColumnCellStyle);
				}
			} else {
				HSSFCellUtil.createCell(headerRow, columnNumber, entry.getKey());
			}
		}
	}

	/**
	 * @param elementName
	 * @param aPsetElementsInfo
	 * @return
	 */
	private boolean isKey(String elementName, PsetElementsInfo aPsetElementsInfo) {
		boolean isKey = false;
		if (aPsetElementsInfo.getKeyElements().contains(elementName)) {
			isKey = true;
		}
		return isKey;
	}

	/**
	 * @param worksheet
	 */
	private void createActionDropDown(HSSFSheet worksheet, String psetNumber, int startRow, int endRow) {
		// Set First row as header row
		DataValidation dataValidation = null;
		DataValidationConstraint constraint = null;
		DataValidationHelper validationHelper = new HSSFDataValidationHelper(worksheet);

		CellRangeAddressList addressList = new CellRangeAddressList(startRow, endRow, 0, 0);
		if (PCD_2598.equals(psetNumber)) {
			constraint = validationHelper.createExplicitListConstraint(new String[] { Constants.EXCEL_ACTION_ADD, Constants.EXCEL_ACTION_DELETE });
		} else {
			constraint = validationHelper.createExplicitListConstraint(new String[] { Constants.EXCEL_ACTION_ADD, Constants.EXCEL_ACTION_CHANGE, Constants.EXCEL_ACTION_DELETE });
		}
		dataValidation = validationHelper.createValidation(constraint, addressList);
		dataValidation.setSuppressDropDownArrow(false);
		worksheet.addValidationData(dataValidation);
	}

	/**
	 * @param aPsetElementsInfo
	 * @param entry
	 * @return
	 */
	private String getLabel(PsetElementsInfo aPsetElementsInfo, String element) {
		String label = null;
		if(element.contains(UNDERSCORE)) {
			element = element.split(UNDERSCORE)[1];
		}
		if(aPsetElementsInfo.getLables().get(element)!=null) {
			label = String.valueOf(aPsetElementsInfo.getLables().get(element));
		}
		return label != null ? label : element;
	}

	/**
	 * @param worksheet
	 * @param pcdItem
	 * @param rowIndex
	 * @param aPsetElementsInfo
	 * @param style
	 * @return
	 * @throws Exception 
	 */
	private int createDataRow(HSSFSheet worksheet, PcdItemList_TypePcdItem pcdItem, int rowIndex, PsetElementsInfo aPsetElementsInfo,
			HSSFCellStyle style) throws Exception {
		Map<String, Integer> headerToColumnMap = aPsetElementsInfo.getHeaderToColumnMap();
		int changedRowIndex = rowIndex;
		HSSFRow dataRow = worksheet.createRow((short) rowIndex);
		MessageElement[] elements = null;

		// update common key data in excel sheet
		if(pcdItem.getPcdItemKey()!=null && pcdItem.getPcdItemKey().length > 0) {
			populateKeyElements(pcdItem, worksheet, headerToColumnMap, dataRow, style);
			elements = getKeyElements(pcdItem.getPcdItemKey(0));
		}

		// update PCD key field data to worksheet column
		if(elements != null) {
			for (MessageElement node : elements) {
				populateCell(worksheet, headerToColumnMap, dataRow, node, style, null);
			}
		}

		// Process all non repeating Elements
		populateNonRepeatingElements(pcdItem, aPsetElementsInfo, changedRowIndex, dataRow, rowIndex, worksheet, style);


		// Process all repeating Elements
		populateRepeatingElements(pcdItem, aPsetElementsInfo, changedRowIndex, dataRow, rowIndex, worksheet, style);

		HSSFRow row = null;
		int columnCount = worksheet.getRow(1).getPhysicalNumberOfCells();
		for (int index = rowIndex; index <= changedRowIndex; index++) {
			row = worksheet.getRow(index);
			for (int column = 0; column < columnCount; column++) {
				if (row.getCell(column) == null) {
					createCell(row, column, "", style);
				}
			}
		}
		return changedRowIndex;
	}


	/**
	 * @param pcdItem
	 * @param aPsetElementsInfo
	 * @param changedRowIndex
	 * @param dataRow
	 * @param rowIndex
	 * @param worksheet
	 * @param style
	 */
	@SuppressWarnings("unchecked")
	private void populateNonRepeatingElements(PcdItemList_TypePcdItem pcdItem, PsetElementsInfo aPsetElementsInfo, int changedRowIndex, HSSFRow dataRow, int rowIndex, HSSFSheet worksheet, HSSFCellStyle style) {

		Map<String, Integer> headerToColumnMap = aPsetElementsInfo.getHeaderToColumnMap();
		List<String> nonRepeatingElements = aPsetElementsInfo.getNonRepeatingElements();

		// Process pcd data and create cells in Excel
		MessageElement[] pcdDataXml = null;
		if(pcdItem.getPcdData()!=null) {
			pcdDataXml = pcdItem.getPcdData().get_any();
		}else{
			pcdDataXml = pcdItem.getPcdEntry().get_any();
		}

		// Process all non repeating Elements
		if(pcdDataXml!=null) {
			for (String nodeName : nonRepeatingElements) {
				short columnNumber = (short) (int) headerToColumnMap.get(nodeName);
				for (MessageElement node : pcdDataXml) {
					List<MessageElement> childs = node.getChildren();
					if (childs != null && !childs.isEmpty() && childs.get(0) instanceof MessageElement) {
						for (MessageElement child : childs) {
							if(nodeName.equalsIgnoreCase(child.getName())) {
								createCell(dataRow, columnNumber, child.getValue(), style);
								break;
							}
						}
					} else {
						if(nodeName.equalsIgnoreCase(node.getName())) {
							createCell(dataRow, columnNumber, node.getValue(), style);
							break;
						}
					}
				}
			}
		}

	}


	/**
	 * @param pcdItem
	 * @param aPsetElementsInfo
	 * @param changedRowIndex
	 * @param dataRow
	 * @param rowIndex
	 * @param worksheet
	 * @param style
	 */
	@SuppressWarnings("unchecked")
	private void populateRepeatingElements(PcdItemList_TypePcdItem pcdItem, PsetElementsInfo aPsetElementsInfo, int changedRowIndex, HSSFRow dataRow, int rowIndex, HSSFSheet worksheet, HSSFCellStyle style) {

		Map<String, Integer> headerToColumnMap = aPsetElementsInfo.getHeaderToColumnMap();
		List<String> repeatingElements = aPsetElementsInfo.getRepeatingElements();

		// Process pcd data and create cells in Excel
		MessageElement[] pcdDataXml = null;
		if(pcdItem.getPcdData()!=null) {
			pcdDataXml = pcdItem.getPcdData().get_any();
		}else{
			pcdDataXml = pcdItem.getPcdEntry().get_any();
		}

		// Process all repeating Elements
		for (String repeatingElementName : repeatingElements) {
			int repeatingRowIndex = rowIndex;
			if (worksheet.getRow(rowIndex) == null) {
				dataRow = worksheet.createRow(rowIndex);
			} else {
				dataRow = worksheet.getRow(rowIndex);
			}

			if(pcdDataXml!=null) {
				for (MessageElement node : pcdDataXml) {
					if(repeatingElementName.equalsIgnoreCase(node.getName())) {
						List<MessageElement> repeatingDataXml = node.getChildren();
						if(repeatingDataXml!=null) {
							Iterator<MessageElement> repDataIterator = repeatingDataXml.iterator();
							while (repDataIterator.hasNext()) {
								List<Text> childs = ((MessageElement) repDataIterator.next()).getChildren();
								for (Text child : childs) {

									MessageElement element = new MessageElement();
									element.setName(child.getNodeName());
									element.setValue(child.getData());
									populateCell(worksheet, headerToColumnMap, dataRow, element, style, repeatingElementName);
								}
								if (repDataIterator.hasNext()) {
									short aRow = (short) ++repeatingRowIndex;
									if (worksheet.getRow(aRow) == null) {
										dataRow = worksheet.createRow(aRow);
									} else {
										dataRow = worksheet.getRow(aRow);
									}
								}
							}
						}
						break;
					}
				}
			}
			if (changedRowIndex < repeatingRowIndex) {
				changedRowIndex = repeatingRowIndex;
			}
		}
	}



	private void populateKeyElements(PcdItemList_TypePcdItem pcdItem, HSSFSheet worksheet, Map<String, Integer> headerToColumnMap, HSSFRow dataRow, HSSFCellStyle style) {

		PcdItemList_TypePcdItemPcdItemKey pcdItemKey = pcdItem.getPcdItemKey(0);
		if (null != pcdItemKey) {
			MessageElement key = new MessageElement();
			key.setName("CdmfFmtCoId"); 
			key.setValue(String.valueOf(pcdItemKey.getCdmfFmtCoId()));
			populateCell(worksheet, headerToColumnMap, dataRow, key, style, null);

			key = new MessageElement();
			key.setName("CdmfFmtEffDt"); 
			key.setValue(String.valueOf(pcdItemKey.getCdmfFmtEffDt()));
			populateCell(worksheet, headerToColumnMap, dataRow, key, style, null);

			key = new MessageElement();
			key.setName("CdmfFmtExpDt"); 
			key.setValue(String.valueOf(pcdItemKey.getCdmfFmtExpDt()));
			populateCell(worksheet, headerToColumnMap, dataRow, key, style, null);

			key = new MessageElement();
			key.setName("CdmfOwnerApp"); 
			key.setValue(String.valueOf(pcdItemKey.getCdmfOwnerApp()));
			populateCell(worksheet, headerToColumnMap, dataRow, key, style, null);

			key = new MessageElement();
			key.setName("CdmfFmtHighUse"); 
			key.setValue(String.valueOf(pcdItemKey.getCdmfFmtHighUse()));
			populateCell(worksheet, headerToColumnMap, dataRow, key, style, null);

			key = new MessageElement();
			key.setName("CdmfCCNum"); 
			key.setValue(String.valueOf(pcdItemKey.getCdmfCCNum()));
			populateCell(worksheet, headerToColumnMap, dataRow, key, style, null);

			key = new MessageElement();
			key.setName("CdmfChgDt"); 
			key.setValue(String.valueOf(pcdItemKey.getCdmfChgDt()));
			populateCell(worksheet, headerToColumnMap, dataRow, key, style, null);

			key = new MessageElement();
			key.setName("CdmfChgTm"); 
			key.setValue(String.valueOf(pcdItemKey.getCdmfChgTm()));
			populateCell(worksheet, headerToColumnMap, dataRow, key, style, null);

			key = new MessageElement();
			key.setName("CdmfChgBy"); 
			key.setValue(String.valueOf(pcdItemKey.getCdmfChgBy()));
			populateCell(worksheet, headerToColumnMap, dataRow, key, style, null);
		}
	}


	/**
	 * @param worksheet
	 * @param headerToColumnMap
	 * @param dataRow
	 * @param dataFieldXml
	 * @param style
	 * @param parent
	 */
	private void populateCell(HSSFSheet worksheet, Map<String, Integer> headerToColumnMap, HSSFRow dataRow,
			MessageElement dataFieldXml, HSSFCellStyle style, String parent) {
		if (dataFieldXml != null) {
			String headerName = dataFieldXml.getName();
			if (!StringUtils.isEmpty(parent)) {
				headerName = parent + UNDERSCORE + dataFieldXml.getName();
			}
			if (!StringUtils.isEmpty(headerName)) {
				Integer columnNumber = headerToColumnMap.get(headerName);
				if (columnNumber != null) {
					createCell(dataRow, columnNumber, dataFieldXml.getValue(), style);
				}
			}
		}
	}

	/**
	 * @param worksheet
	 * @param rowIndex
	 * @param aPsetElementsInfo
	 */
	private void setExtendedViewHeaderStyle(HSSFSheet worksheet, int rowIndex, PsetElementsInfo aPsetElementsInfo) {
		Map<String, Integer> headerToColumnMap = aPsetElementsInfo.getHeaderToColumnMap();
		HSSFRow row = worksheet.getRow(2);
		if (row != null) {
			String[] nodes = { TIER_MIN_RATE, TIER_MAX_RATE, TIER_DATA_SEQ_NUM, TIER_DATA_TIER_BASIS_FACTOR,
					TIER_DATA_TIER_POINTS, TIER_DATA_TIER_LIMIT, TIER_DATA_TIER_RATE };
			for (String node : nodes) {
				row.getCell(headerToColumnMap.get(node)).setCellStyle(extendedTierheaderKeyColumnCellStyle);
			}
		}

		// Hide Extra Columns from the sheet....
		worksheet.setColumnHidden(headerToColumnMap.get(NUM_OF_TIERS), true);
		worksheet.setColumnHidden(headerToColumnMap.get(NUM_OF_BALANCES), true);
		worksheet.setColumnHidden(headerToColumnMap.get(NUM_OF_TERMS), true);
		worksheet.setColumnHidden(headerToColumnMap.get(SEQUENCE), true);
		for (int balIndex = 1; balIndex <= 25; balIndex++) {
			String strIndex = Utils.leftPad(balIndex + "", 2, "0");
			worksheet.setColumnHidden(headerToColumnMap.get(BAL_DATA + strIndex + RATES + strIndex + SEQ_NUM), true);
		}
	}

	/**
	 * @param worksheet
	 * @param pcdItem
	 * @param rowIndex
	 * @param aPsetElementsInfo
	 * @param style
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private int createInterestRateDataRow(HSSFSheet worksheet, PcdItemList_TypePcdItem pcdItem, int rowIndex, PsetElementsInfo aPsetElementsInfo, HSSFCellStyle style) throws Exception {
		Map<String, Integer> headerToColumnMap = aPsetElementsInfo.getHeaderToColumnMap();
		List<String> nonRepeatingElements = aPsetElementsInfo.getNonRepeatingElements();
		int changedRowIndex = rowIndex;
		HSSFRow dataRow = worksheet.createRow((short) rowIndex);
		MessageElement[] elements = null;

		// update common key data in excel sheet
		if(pcdItem.getPcdItemKey()!=null && pcdItem.getPcdItemKey().length > 0) {
			populateKeyElements(pcdItem, worksheet, headerToColumnMap, dataRow, style);
			elements = getKeyElements(pcdItem.getPcdItemKey(0));
		}

		// update PCD key field data to worksheet column
		if(elements != null) {
			for (MessageElement node : elements) {
				populateCell(worksheet, headerToColumnMap, dataRow, node, style, null);
			}
		}

		// Process pcd data and create cells in Excel
		MessageElement[] pcdDataXml = null;
		if(pcdItem.getPcdData()!=null) {
			pcdDataXml = pcdItem.getPcdData().get_any();
		}else{
			pcdDataXml = pcdItem.getPcdEntry().get_any();
		}

		// Process all non repeating Elements
		for (String nodeName : nonRepeatingElements) {
			short columnNumber = (short) (int) headerToColumnMap.get(nodeName);
			for (MessageElement node : pcdDataXml) {
				List<MessageElement> childs = node.getChildren();
				if (childs != null && !childs.isEmpty() && childs.get(0) instanceof MessageElement) {
					for (MessageElement child : childs) {
						if(nodeName.equalsIgnoreCase(child.getName())) {
							createCell(dataRow, columnNumber, child.getValue(), style);
							break;
						}
					}
				} else {
					if(nodeName.equalsIgnoreCase(node.getName())) {
						createCell(dataRow, columnNumber, node.getValue(), style);
						break;
					}
				}
			}
		}

		// Process Terms and create cells in Excel
		int termRowIndex = rowIndex;
		for (MessageElement termChild : pcdDataXml) {
			if(RATE_TERMS.equalsIgnoreCase(termChild.getName())) {
				termRowIndex++;
				if (worksheet.getRow(termRowIndex) == null) {
					dataRow = worksheet.createRow(termRowIndex);
				} else {
					dataRow = worksheet.getRow(termRowIndex);
				}
				List<MessageElement> termChildList = termChild.getChildren();
				for (MessageElement child : termChildList) {
					populateCell(worksheet, headerToColumnMap, dataRow, child, style, RATE_TERMS);
				}
			}
		}

		if (changedRowIndex < termRowIndex) {
			changedRowIndex = termRowIndex;
		}


		// Process Balances and Rates and create cells in Excel
		for (int balIndex = 1; balIndex <= 25; balIndex++) {
			String strIndex = Utils.leftPad(balIndex + "", 2, "0");
			int balRowIndex = rowIndex;
			for (MessageElement termChild : pcdDataXml) {

				if(termChild.getName().startsWith(BAL_DATA)) {

					if((BAL_DATA + strIndex).equalsIgnoreCase(termChild.getName())) {

						List<MessageElement> balDataXml = termChild.getChildren();
						if (balDataXml != null) {
							for (MessageElement child : balDataXml) {
								if(child.getName().startsWith(BAL_AMT))
								{
									//									IXml balAmtXml = balDataXml.findElement(BAL_AMT + strIndex);
									if (child != null) {
										if (worksheet.getRow(rowIndex) == null) {
											dataRow = worksheet.createRow(rowIndex);
										} else {
											dataRow = worksheet.getRow(rowIndex);
										}
										populateCell(worksheet, headerToColumnMap, dataRow, child, style, BAL_DATA + strIndex);
									}
								}else if(child.getName().startsWith(RATES2)) {
									//List<IXml> balAmtList = balDataXml.getChildren(RATES2 + strIndex);
									//for (MessageElement rateChild : balAmtList) {
									balRowIndex++;
									if (worksheet.getRow(balRowIndex) == null) {
										dataRow = worksheet.createRow(balRowIndex);
									} else {
										dataRow = worksheet.getRow(balRowIndex);
									}
									List<MessageElement> rateChildList = child.getChildren();
									for (MessageElement rateChild : rateChildList) {
										populateCell(worksheet, headerToColumnMap, dataRow, rateChild, style,
												BAL_DATA + strIndex + RATES + strIndex);
									}
									//									}
								}
							}
						}
						break;
					}
				}
			}
			if (changedRowIndex < balRowIndex) {
				changedRowIndex = balRowIndex;
			}
		}

		// Process Extended Tier Table and create cells in Excel
		int tierRowIndex = rowIndex - 1;
		//		List<IXml> tierList = pcdDataXml.getChildren(TIER_DATA);
		for (MessageElement tierChild : pcdDataXml) {
			if(tierChild.getName().startsWith(TIER_DATA)) {
				tierRowIndex++;
				if (worksheet.getRow(tierRowIndex) == null) {
					dataRow = worksheet.createRow(tierRowIndex);
				} else {
					dataRow = worksheet.getRow(tierRowIndex);
				}
				List<MessageElement> tierChildList = tierChild.getChildren();
				for (MessageElement child : tierChildList) {
					populateCell(worksheet, headerToColumnMap, dataRow, child, style, TIER_DATA);
				}
			}
		}
		if (changedRowIndex < tierRowIndex) {
			changedRowIndex = tierRowIndex;
		}

		HSSFRow row = null;
		int columnCount = worksheet.getRow(1).getPhysicalNumberOfCells();
		for (int index = rowIndex; index <= changedRowIndex; index++) {
			row = worksheet.getRow(index);
			for (int column = 0; column < columnCount; column++) {
				if (row.getCell(column) == null) {
					createCell(row, column, "", style);
				}
			}
		}
		return changedRowIndex;
	}

	private Properties getLabelsProperties(String pcdNumber) {
		Properties labels = new Properties();
		try {
			StringBuffer path = new StringBuffer(Constants.LABELS_PROP_PATH);
			path.append(Utils.getRegion().toLowerCase()).append("/");
			String fileName = "ParameterKeyLabels.properties";
			File file = new File(path.toString() + fileName);

			BufferedReader inputStream = null;
			try {
				if(file.exists()) {
					inputStream = new BufferedReader(new FileReader(file));
					labels.load(inputStream);
				}else {
					throw new BusinessException("Error reading Label properties file in Export Service: "+file.getAbsolutePath(), true);
				}
			}catch(Exception e) {
				Utils.handleException(e);
			}finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}

			fileName = "Parameter"+pcdNumber+"Labels.properties";
			file = new File(path.toString() + fileName);
			try {
				if(file.exists()) {
					inputStream = new BufferedReader(new FileReader(file));
					labels.load(inputStream);
				}else {
					throw new BusinessException("Error reading Label properties file in Export Service: "+file.getAbsolutePath(), true);
				}
			}catch(Exception e) {
				Utils.handleException(e);
			}finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		} catch (Exception e) {
			throw new BusinessException("Error reading Label properties file in Export Service: "+e.getMessage(), true);
		}	
		return labels;
	}

	private List<ParameterBean> handleAllCompany(List<ParameterBean>  pSets) {
		CompanyService companyService = new CompanyService();
		List<ParameterBean>  pSetsExpandedList = new ArrayList<ParameterBean>(); 
		pSets.forEach(pSet->{
			if("all".equalsIgnoreCase(pSet.getCompanyID())){
				List<CompanyBean>  companies = companyService.getCompanyDetails(pSet.getNumber());
				companies.forEach(company ->{
					if(!"all".equalsIgnoreCase(company.getId())) {
						ParameterBean parameterBean = new ParameterBean();
						parameterBean.setApplicationID(pSet.getApplicationID());
						parameterBean.setCompanyID(company.getId());
						parameterBean.setEffectiveDate(pSet.getEffectiveDate());
						parameterBean.setName(pSet.getName());
						parameterBean.setNumber(pSet.getNumber());
						pSetsExpandedList.add(parameterBean);
					}
				});
			}else {
				pSetsExpandedList.add(pSet);
			}

		});
		return pSetsExpandedList;
	}

	private HSSFCell createCell(HSSFRow row, int column, String value, HSSFCellStyle style) {
		if(value ==null || "null".equalsIgnoreCase(value)) {
			value = Constants.EMPTY;
		}
		return HSSFCellUtil.createCell(row, column, value, style);
	}
}
