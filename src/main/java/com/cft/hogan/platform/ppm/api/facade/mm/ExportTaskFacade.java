package com.cft.hogan.platform.ppm.api.facade.mm;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cft.hogan.platform.ppm.api.bean.CompanyBean;
import com.cft.hogan.platform.ppm.api.bean.ParameterBean;
import com.cft.hogan.platform.ppm.api.bean.mm.ExportTaskBean;
import com.cft.hogan.platform.ppm.api.config.context.ApplicationContext;
import com.cft.hogan.platform.ppm.api.exception.BadRequest;
import com.cft.hogan.platform.ppm.api.exception.BusinessError;
import com.cft.hogan.platform.ppm.api.exception.ExceptionHandler;
import com.cft.hogan.platform.ppm.api.facade.CompanyFacade;
import com.cft.hogan.platform.ppm.api.facade.ParameterFacade;
import com.cft.hogan.platform.ppm.api.pcd.service.PCDService;
import com.cft.hogan.platform.ppm.api.pcd.service.client.PcdEntry;
import com.cft.hogan.platform.ppm.api.pcd.service.client.PcdItemList_Type;
import com.cft.hogan.platform.ppm.api.pcd.service.client.PcdItemList_TypePcdItem;
import com.cft.hogan.platform.ppm.api.pcd.service.client.PcdItemList_TypePcdItemPcdItemKey;
import com.cft.hogan.platform.ppm.api.pcd.service.client.PcdXmlRs_Type;
import com.cft.hogan.platform.ppm.api.pcd.service.client.Status_Type;
import com.cft.hogan.platform.ppm.api.util.Constants;
import com.cft.hogan.platform.ppm.api.util.Utils;
import com.cft.hogan.platform.ppm.api.util.excel.ExcelStyle;
import com.cft.hogan.platform.ppm.api.util.excel.PsetElementsInfo;
import com.cft.hogan.platform.ppm.api.util.excel.WorkbookStyle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExportTaskFacade {

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



	@Autowired
	private ParameterFacade parameterFacade;

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
				if(!StringUtils.isEmpty(pset.getEffectiveDate()) && !Utils.isValidDate(pset.getEffectiveDate())){
					throw new BusinessError("Invalid Parameter Effective date: #"+pset.getNumber(), false);
				}
			}
			HSSFWorkbook workbook = null;
			ByteArrayOutputStream outputStream = null;
			try {
				outputStream = new ByteArrayOutputStream();
				exportTaskBean.setPsets(handleAllCompany(exportTaskBean.getPsets()));
				workbook = createPsetWorkBook(exportTaskBean.getPsets(), exportTaskBean.getExcelSettings(), exportTaskBean.getSingleTab());
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
			ExceptionHandler.handleException(e);
		}
		return response;
	}

	private HSSFWorkbook createPsetWorkBook(List<ParameterBean> selectedExportList, ExcelStyle excelStyleSetting, Boolean singleTab) throws Exception {
		HSSFWorkbook workBook  = new HSSFWorkbook();;
		PCDService service = new PCDService();
		if (!isEmptySelectedExportList(selectedExportList)) {
			ExcelStyle excelStyle = getExcelStyleVar(excelStyleSetting);
			WorkbookStyle workbookStyle = new WorkbookStyle(excelStyle);
			loadWorkbookStyle(workbookStyle, workBook);
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
						if (Constants.PCD_2598.equals(pSetNum)) {
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
								HSSFSheet worksheet = createSheet(workSheetName, workBook);
								pupulatePsetWorksheet(workSheetData.getXStatus(), workSheetData.getPcdItemList(), worksheet, aPsetElementsInfo, false, false, workbookStyle, workBook);
							}
							else
							{
								HSSFSheet worksheet = getSheet(samePCDs.get(pSetNum), workBook);
								pupulatePsetWorksheet(workSheetData.getXStatus(), workSheetData.getPcdItemList(), worksheet, aPsetElementsInfo, true, false, workbookStyle, workBook);
							}
						}
						else
						{
							HSSFSheet worksheet = createSheet(getWorkSheetName(parameterBean, rowNumber, false), workBook);
							pupulatePsetWorksheet(workSheetData.getXStatus(), workSheetData.getPcdItemList(), worksheet, aPsetElementsInfo, false, true, workbookStyle, workBook);
						}


					} else {
						// Pass error data and create new sheet with error
						HSSFSheet worksheet = createSheet(getWorkSheetName(parameterBean, rowNumber, false), workBook);
						pupulatePsetWorksheet(template.getXStatus(), template.getPcdItemList(), worksheet, aPsetElementsInfo, false, false, workbookStyle, workBook);
					}
				}
			}
		}else {
			throw new BadRequest(MESSAGE_1);
		}
		return workBook;
	}

	private void loadWorkbookStyle(WorkbookStyle workbookStyle, HSSFWorkbook workBook) {
		if (workBook != null) {
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(TITLE_FONT_COLOR, workbookStyle.getPcdNameandNumberStyle().getFontColorRGB()[0],
					workbookStyle.getPcdNameandNumberStyle().getFontColorRGB()[1],
					workbookStyle.getPcdNameandNumberStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(TITLE_CELL_COLOR, workbookStyle.getPcdNameandNumberStyle().getCellColorRGB()[0],
					workbookStyle.getPcdNameandNumberStyle().getCellColorRGB()[1],
					workbookStyle.getPcdNameandNumberStyle().getCellColorRGB()[2]);
			palette.setColorAtIndex(HEADER_KEY_COLUMN_FONT_COLOR,
					workbookStyle.getHeaderRowReqFieldsStyle().getFontColorRGB()[0],
					workbookStyle.getHeaderRowReqFieldsStyle().getFontColorRGB()[1],
					workbookStyle.getHeaderRowReqFieldsStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(HEADER_KEY_COLUMN_CELL_COLOR,
					workbookStyle.getHeaderRowReqFieldsStyle().getCellColorRGB()[0],
					workbookStyle.getHeaderRowReqFieldsStyle().getCellColorRGB()[1],
					workbookStyle.getHeaderRowReqFieldsStyle().getCellColorRGB()[2]);
			palette.setColorAtIndex(HEADER_NON_REQ_KEY_COLUMN_FONT_COLOR,
					workbookStyle.getHeaderRowNonReqFieldsStyle().getFontColorRGB()[0],
					workbookStyle.getHeaderRowNonReqFieldsStyle().getFontColorRGB()[1],
					workbookStyle.getHeaderRowNonReqFieldsStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(HEADER_NON_REQ_KEY_COLUMN_CELL_COLOR,
					workbookStyle.getHeaderRowNonReqFieldsStyle().getCellColorRGB()[0],
					workbookStyle.getHeaderRowNonReqFieldsStyle().getCellColorRGB()[1],
					workbookStyle.getHeaderRowNonReqFieldsStyle().getCellColorRGB()[2]);
			palette.setColorAtIndex(HEADER_DATA_COLUMN_FONT_COLOR,
					workbookStyle.getHeaderRowDataFieldsStyle().getFontColorRGB()[0],
					workbookStyle.getHeaderRowDataFieldsStyle().getFontColorRGB()[1],
					workbookStyle.getHeaderRowDataFieldsStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(HEADER_DATA_COLUMN_CELL_COLOR,
					workbookStyle.getHeaderRowDataFieldsStyle().getCellColorRGB()[0],
					workbookStyle.getHeaderRowDataFieldsStyle().getCellColorRGB()[1],
					workbookStyle.getHeaderRowDataFieldsStyle().getCellColorRGB()[2]);
			palette.setColorAtIndex(ODD_ROW_COLUMN_FONT_COLOR,
					workbookStyle.getDataRowOddFieldsStyle().getFontColorRGB()[0],
					workbookStyle.getDataRowOddFieldsStyle().getFontColorRGB()[1],
					workbookStyle.getDataRowOddFieldsStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(ODD_ROW_COLUMN_CELL_COLOR,
					workbookStyle.getDataRowOddFieldsStyle().getCellColorRGB()[0],
					workbookStyle.getDataRowOddFieldsStyle().getCellColorRGB()[1],
					workbookStyle.getDataRowOddFieldsStyle().getCellColorRGB()[2]);
			palette.setColorAtIndex(EVEN_ROW_COLUMN_FONT_COLOR,
					workbookStyle.getDataRowEvenFieldsStyle().getFontColorRGB()[0],
					workbookStyle.getDataRowEvenFieldsStyle().getFontColorRGB()[1],
					workbookStyle.getDataRowEvenFieldsStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(EVEN_ROW_COLUMN_CELL_COLOR,
					workbookStyle.getDataRowEvenFieldsStyle().getCellColorRGB()[0],
					workbookStyle.getDataRowEvenFieldsStyle().getCellColorRGB()[1],
					workbookStyle.getDataRowEvenFieldsStyle().getCellColorRGB()[2]);
			palette.setColorAtIndex(EXTENDED_TIER_HEADER_DATA_COLUMN_FONT_COLOR,
					workbookStyle.getIntRateMtrxExtndTierStyle().getFontColorRGB()[0],
					workbookStyle.getIntRateMtrxExtndTierStyle().getFontColorRGB()[1],
					workbookStyle.getIntRateMtrxExtndTierStyle().getFontColorRGB()[2]);
			palette.setColorAtIndex(EXTENDED_TIER_HEADER_DATA_COLUMN_CELL_COLOR,
					workbookStyle.getIntRateMtrxExtndTierStyle().getCellColorRGB()[0],
					workbookStyle.getIntRateMtrxExtndTierStyle().getCellColorRGB()[1],
					workbookStyle.getIntRateMtrxExtndTierStyle().getCellColorRGB()[2]);

		}
	}

	private HSSFCellStyle getTitleCellStyle(WorkbookStyle workbookStyle, HSSFWorkbook workBook) {
		HSSFCellStyle titleCellStyle = null;
		titleCellStyle = getCellStyle(workbookStyle.getPcdNameandNumberStyle().getFontType(),
				Integer.parseInt(workbookStyle.getPcdNameandNumberStyle().getFontStyle()),
				workbookStyle.getPcdNameandNumberStyle().getFontSize(), TITLE_FONT_COLOR, TITLE_CELL_COLOR, workBook);
		return titleCellStyle;
	}


	private HSSFCellStyle getHeaderKeyNonReqCellStyle(WorkbookStyle workbookStyle, HSSFWorkbook workBook) {
		HSSFCellStyle headerKeyNonReqCellStyle = null;

		headerKeyNonReqCellStyle = getCellStyle(workbookStyle.getHeaderRowNonReqFieldsStyle().getFontType(),
				Integer.parseInt(workbookStyle.getHeaderRowNonReqFieldsStyle().getFontStyle()),
				workbookStyle.getHeaderRowNonReqFieldsStyle().getFontSize(), HEADER_NON_REQ_KEY_COLUMN_FONT_COLOR,
				HEADER_NON_REQ_KEY_COLUMN_CELL_COLOR, workBook);

		return headerKeyNonReqCellStyle;
	}


	private HSSFCellStyle getHeaderKeyReqCellStyle(WorkbookStyle workbookStyle, HSSFWorkbook workBook) {
		HSSFCellStyle headerKeyReqCellStyle = null;

		headerKeyReqCellStyle = getCellStyle(workbookStyle.getHeaderRowReqFieldsStyle().getFontType(),
				Integer.parseInt(workbookStyle.getHeaderRowReqFieldsStyle().getFontStyle()),
				workbookStyle.getHeaderRowReqFieldsStyle().getFontSize(), HEADER_KEY_COLUMN_FONT_COLOR,
				HEADER_KEY_COLUMN_CELL_COLOR, workBook);

		return headerKeyReqCellStyle;
	}


	private HSSFCellStyle getHeaderDataColumnCellStyle(WorkbookStyle workbookStyle, HSSFWorkbook workBook) {
		HSSFCellStyle headerDataColumnCellStyle = null;

		headerDataColumnCellStyle = getCellStyle(workbookStyle.getHeaderRowDataFieldsStyle().getFontType(),
				Integer.parseInt(workbookStyle.getHeaderRowDataFieldsStyle().getFontStyle()),
				workbookStyle.getHeaderRowDataFieldsStyle().getFontSize(), HEADER_DATA_COLUMN_FONT_COLOR,
				HEADER_DATA_COLUMN_CELL_COLOR, workBook);

		return headerDataColumnCellStyle;
	}

	private HSSFCellStyle getOddRowColumnCellStyle(WorkbookStyle workbookStyle, HSSFWorkbook workBook) {
		HSSFCellStyle oddRowColumnCellStyle = null;

		oddRowColumnCellStyle = getCellStyle(workbookStyle.getDataRowOddFieldsStyle().getFontType(),
				Integer.parseInt(workbookStyle.getDataRowOddFieldsStyle().getFontStyle()),
				workbookStyle.getDataRowOddFieldsStyle().getFontSize(), ODD_ROW_COLUMN_FONT_COLOR,
				ODD_ROW_COLUMN_CELL_COLOR, workBook);

		return oddRowColumnCellStyle;
	}

	private HSSFCellStyle getEvenRowColumnCellStyle(WorkbookStyle workbookStyle, HSSFWorkbook workBook) {
		HSSFCellStyle evenRowColumnCellStyle = null;

		evenRowColumnCellStyle = getCellStyle(workbookStyle.getDataRowEvenFieldsStyle().getFontType(),
				Integer.parseInt(workbookStyle.getDataRowEvenFieldsStyle().getFontStyle()),
				workbookStyle.getDataRowEvenFieldsStyle().getFontSize(), EVEN_ROW_COLUMN_FONT_COLOR,
				EVEN_ROW_COLUMN_CELL_COLOR, workBook);

		return evenRowColumnCellStyle;
	}


	private HSSFCellStyle getExtendedTierheaderKeyColumnCellStyle(WorkbookStyle workbookStyle, HSSFWorkbook workBook) {
		HSSFCellStyle extendedTierheaderKeyColumnCellStyle = null;

		extendedTierheaderKeyColumnCellStyle = getCellStyle(
				workbookStyle.getIntRateMtrxExtndTierStyle().getFontType(),
				Integer.parseInt(workbookStyle.getIntRateMtrxExtndTierStyle().getFontStyle()),
				workbookStyle.getIntRateMtrxExtndTierStyle().getFontSize(),
				EXTENDED_TIER_HEADER_DATA_COLUMN_FONT_COLOR, EXTENDED_TIER_HEADER_DATA_COLUMN_CELL_COLOR, workBook);

		return extendedTierheaderKeyColumnCellStyle;
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
			aPsetElementsInfo.addInHeaderToColumnMap(BAL_DATA + colIndex + "_"+BAL_AMT + colIndex, columnNumber++);
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
			ExceptionHandler.handleException(e);
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
			ExceptionHandler.handleException(e);
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

	private void pupulatePsetWorksheet(Status_Type status, PcdItemList_Type pcdItemList_Type, HSSFSheet worksheet, PsetElementsInfo pSetElementsInfo, boolean singleTab, boolean noRecordsFlag, WorkbookStyle workbookStyle, HSSFWorkbook workBook) throws Exception {
		final int sheetTitleRow = 0;
		int elementRow = 1;
		final int labelRow = 2;
		int dataRowStartIndex = 2;

		if(singleTab) {
			dataRowStartIndex = worksheet.getLastRowNum();
		}
		if ("0".equals(String.valueOf(status.getStatusCode()))) {

			/*
			 * add Header rows
			 */
			addHeaderRows(singleTab, elementRow, labelRow, worksheet, pSetElementsInfo, workbookStyle, workBook);

			/*
			 * add Data rows
			 */
			addDataRows(pcdItemList_Type, pSetElementsInfo, dataRowStartIndex, workbookStyle, worksheet, workBook);


			// Set the width of woksheet column width based on the worksheet cells data
			HSSFRow headerRow = worksheet.getRow(1);
			int columnCount = headerRow.getPhysicalNumberOfCells();
			if(!singleTab) {
				// Write the title
				createTitleRow(sheetTitleRow, worksheet, 0, columnCount,
						pSetElementsInfo.getParameterNumber() + HYPHEN + parameterFacade.getParameterName(pSetElementsInfo.getAppName(), pSetElementsInfo.getParameterNumber()), workbookStyle, workBook);
			}
			for (int column = 0; column < columnCount; column++) {
				worksheet.autoSizeColumn(column);
			}

			if(noRecordsFlag && pcdItemList_Type.getPcdItem().length==0) {
				createTitleRow(4, worksheet, 0, columnCount,"No records", workbookStyle, workBook);
			}

		} else {
			// write title
			createTitleRow(sheetTitleRow, worksheet, 0, 10,
					pSetElementsInfo.getParameterNumber() + HYPHEN + parameterFacade.getParameterName(pSetElementsInfo.getAppName(), pSetElementsInfo.getParameterNumber()), workbookStyle, workBook);
			// Write the error caught in service
			HSSFRow headerRow = worksheet.createRow(labelRow);
			createCell(headerRow, 0, status.getStatusDesc(), getTitleCellStyle(workbookStyle, workBook));
		}
		// Create action coulmn
		createActionDropDown(worksheet, pSetElementsInfo.getParameterNumber(), labelRow,
				worksheet.getPhysicalNumberOfRows());
		// Set Action header with required style
		worksheet.getRow(labelRow).getCell(0).setCellStyle(getHeaderKeyReqCellStyle(workbookStyle, workBook));
	}
	

	private void addHeaderRows(boolean singleTab, int elementRow, int labelRow, HSSFSheet worksheet, PsetElementsInfo pSetElementsInfo, WorkbookStyle workbookStyle, HSSFWorkbook workBook) {
		if(!singleTab) {
			// Write element names
			createHeaderRow(elementRow, worksheet, pSetElementsInfo, false, workbookStyle, workBook);
			// Write label names
			createHeaderRow(labelRow, worksheet, pSetElementsInfo, true, workbookStyle, workBook);
		}
		if (Constants.PCD_2598.equals(pSetElementsInfo.getParameterNumber())) {
			setExtendedViewHeaderStyle(worksheet, 2, pSetElementsInfo, workbookStyle, workBook);
		}
	}


	private void addDataRows(PcdItemList_Type pcdItemList_Type, PsetElementsInfo pSetElementsInfo, int dataRowStartIndex, WorkbookStyle workbookStyle, HSSFSheet worksheet, HSSFWorkbook workBook) throws Exception {
		int styleIndex = 0;
		PcdItemList_TypePcdItem[] pcdItemList = pcdItemList_Type.getPcdItem();

		HSSFCellStyle oddRowColumnCellStyle = getOddRowColumnCellStyle(workbookStyle, workBook);
		HSSFCellStyle evenRowColumnCellStyle = getEvenRowColumnCellStyle(workbookStyle, workBook);

		for (PcdItemList_TypePcdItem pcdItem : pcdItemList) {
			HSSFCellStyle style = null;
			if (styleIndex % 2 == 0) {
				style = oddRowColumnCellStyle;
			} else {
				style = evenRowColumnCellStyle;
			}
			if (Constants.PCD_2598.equals(pSetElementsInfo.getParameterNumber())) {
				dataRowStartIndex = createInterestRateDataRow(worksheet, pcdItem, ++dataRowStartIndex,
						pSetElementsInfo, style);
			} else {
				dataRowStartIndex = createDataRow(worksheet, pcdItem, ++dataRowStartIndex, pSetElementsInfo,
						style);
			}
			styleIndex++;
		}
	}


	/**
	 * @param titleRowIndex
	 * @param worksheet
	 * @param startColumn
	 * @param endColumn
	 * @param title
	 */
	private void createTitleRow(int titleRowIndex, HSSFSheet worksheet, int startColumn, int endColumn, String title, WorkbookStyle aWorkbookStyle, HSSFWorkbook workBook) {
		HSSFRow headerRow = worksheet.createRow(titleRowIndex);
		createCell(headerRow, 0, title, getTitleCellStyle(aWorkbookStyle, workBook));
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
			boolean isLabelRow, WorkbookStyle workbookStyle, HSSFWorkbook workBook) {
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
			int columnNumber = entry.getValue();
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
						createCell(headerRow, columnNumber, label, getHeaderKeyReqCellStyle(workbookStyle, workBook));
					} else {
						createCell(headerRow, columnNumber, label, getHeaderKeyNonReqCellStyle(workbookStyle, workBook));
					}
				} else {
					createCell(headerRow, columnNumber, label, getHeaderDataColumnCellStyle(workbookStyle, workBook));
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
		if (Constants.PCD_2598.equals(psetNumber)) {
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
		HSSFRow dataRow = worksheet.createRow(rowIndex);

		// update common key data in excel sheet
		if(pcdItem.getPcdItemKey()!=null && pcdItem.getPcdItemKey().length > 0) {
			//standard keys
			populateKeyElements(pcdItem, headerToColumnMap, dataRow, style);

			//PCD Keys
			MessageElement[] elements = getKeyElements(pcdItem.getPcdItemKey(0));
			// update PCD key field data to worksheet column
			if(elements != null) {
				for (MessageElement node : elements) {
					populateCell(headerToColumnMap, dataRow, node, style, null);
				}
			}
		}

		// Process all non repeating Elements
		populateNonRepeatingElements(pcdItem, aPsetElementsInfo, dataRow, style);

		// Process all repeating Elements
		rowIndex = populateRepeatingElements(pcdItem, aPsetElementsInfo, rowIndex, worksheet, style);

		return rowIndex;
	}


	/**
	 * @param pcdItem
	 * @param aPsetElementsInfo
	 * @param dataRow
	 * @param style
	 */
	private void populateNonRepeatingElements(PcdItemList_TypePcdItem pcdItem, PsetElementsInfo aPsetElementsInfo, HSSFRow dataRow, HSSFCellStyle style) {

		Map<String, Integer> headerToColumnMap = aPsetElementsInfo.getHeaderToColumnMap();
		List<String> nonRepeatingElements = aPsetElementsInfo.getNonRepeatingElements();

		// Process pcd data and create cells in Excel
		MessageElement[] pcdData = null;
		if(pcdItem.getPcdData()!=null) {
			pcdData = pcdItem.getPcdData().get_any();
		}else{
			pcdData = pcdItem.getPcdEntry().get_any();
		}

		// Process all non repeating Elements
		if(pcdData!=null) {
			for (String elementName : nonRepeatingElements) {
				for (MessageElement node : pcdData) {
					if(elementName.equalsIgnoreCase(node.getName())) {
						createCell(dataRow, headerToColumnMap.get(elementName), node.getValue(), style);
						break;
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
	private int populateRepeatingElements(PcdItemList_TypePcdItem pcdItem, PsetElementsInfo aPsetElementsInfo, int rowIndex, HSSFSheet worksheet, HSSFCellStyle style) {

		Map<String, Integer> headerToColumnMap = aPsetElementsInfo.getHeaderToColumnMap();
		List<String> repeatingElements = aPsetElementsInfo.getRepeatingElements();

		// Process pcd data and create cells in Excel
		MessageElement[] pcdData = null;
		if(pcdItem.getPcdData()!=null) {
			pcdData = pcdItem.getPcdData().get_any();
		}else{
			pcdData = pcdItem.getPcdEntry().get_any();
		}

		// Process all repeating Elements
		if(pcdData!=null) {
			int repeatingRowIndex = rowIndex;
			int repeatingRowLastIndex = rowIndex;
			for (String repeatingElementName : repeatingElements) {
				repeatingRowIndex = rowIndex;
				boolean firstInstance = true;
				for (MessageElement node : pcdData) {
					if(repeatingElementName.equalsIgnoreCase(node.getName())) {
						if(!firstInstance) {
							repeatingRowIndex++;
						}
						HSSFRow dataRow = null;
						if (worksheet.getRow(repeatingRowIndex) == null) {
							dataRow = worksheet.createRow(repeatingRowIndex);
						} else {
							dataRow = worksheet.getRow(repeatingRowIndex);
						}
						List<MessageElement> repeatingDataElements = node.getChildren();
						if(repeatingDataElements!=null) {
							Iterator<MessageElement> repDataIterator = repeatingDataElements.iterator();
							while (repDataIterator.hasNext()) {
								populateCell(headerToColumnMap, dataRow, repDataIterator.next(), style, repeatingElementName);

								//Fix for repeating elements(two dimension rows
								/*
								 * List<Text> childs = ((MessageElement) repDataIterator.next()).getChildren();
								 * for (Text child : childs) {
								 * 
								 * MessageElement element = new MessageElement();
								 * element.setName(child.getNodeName()); element.setValue(child.getData());
								 * populateCell(worksheet, headerToColumnMap, dataRow, element, style,
								 * repeatingElementName); }
								 */
								/*
								 * if (repDataIterator.hasNext()) { short aRow = (short) ++repeatingRowIndex; if
								 * (worksheet.getRow(aRow) == null) { dataRow = worksheet.createRow(aRow); }
								 * else { dataRow = worksheet.getRow(aRow); } }
								 */
							}
							firstInstance =false;
						}
					}
				}
				
				if(repeatingRowLastIndex < repeatingRowIndex) {
					repeatingRowLastIndex = repeatingRowIndex;
				}
			}

			HSSFRow row = null;
			int columnCount = worksheet.getRow(1).getPhysicalNumberOfCells();
			for (int index = rowIndex; index <= repeatingRowLastIndex; index++) {
				row = worksheet.getRow(index);
				if(row!=null) {
					for (int column = 0; column < columnCount; column++) {
						if (row.getCell(column) == null) {
							createCell(row, column, "", style);
						}
					}
				}
			}
			
			rowIndex = repeatingRowLastIndex;
		}
		return rowIndex;
	}



	private void populateKeyElements(PcdItemList_TypePcdItem pcdItem, Map<String, Integer> headerToColumnMap, HSSFRow dataRow, HSSFCellStyle style) {

		PcdItemList_TypePcdItemPcdItemKey pcdItemKey = pcdItem.getPcdItemKey(0);
		if (null != pcdItemKey) {
			MessageElement keyElement = new MessageElement();
			keyElement.setName("CdmfFmtCoId"); 
			keyElement.setValue(String.valueOf(pcdItemKey.getCdmfFmtCoId()));
			populateCell(headerToColumnMap, dataRow, keyElement, style, null);

			keyElement = new MessageElement();
			keyElement.setName("CdmfFmtEffDt"); 
			keyElement.setValue(String.valueOf(pcdItemKey.getCdmfFmtEffDt()));
			populateCell(headerToColumnMap, dataRow, keyElement, style, null);

			keyElement = new MessageElement();
			keyElement.setName("CdmfFmtExpDt"); 
			keyElement.setValue(String.valueOf(pcdItemKey.getCdmfFmtExpDt()));
			populateCell(headerToColumnMap, dataRow, keyElement, style, null);

			keyElement = new MessageElement();
			keyElement.setName("CdmfOwnerApp"); 
			keyElement.setValue(String.valueOf(pcdItemKey.getCdmfOwnerApp()));
			populateCell(headerToColumnMap, dataRow, keyElement, style, null);

			keyElement = new MessageElement();
			keyElement.setName("CdmfFmtHighUse"); 
			keyElement.setValue(String.valueOf(pcdItemKey.getCdmfFmtHighUse()));
			populateCell(headerToColumnMap, dataRow, keyElement, style, null);

			keyElement = new MessageElement();
			keyElement.setName("CdmfCCNum"); 
			keyElement.setValue(String.valueOf(pcdItemKey.getCdmfCCNum()));
			populateCell(headerToColumnMap, dataRow, keyElement, style, null);

			keyElement = new MessageElement();
			keyElement.setName("CdmfChgDt"); 
			keyElement.setValue(String.valueOf(pcdItemKey.getCdmfChgDt()));
			populateCell(headerToColumnMap, dataRow, keyElement, style, null);

			keyElement = new MessageElement();
			keyElement.setName("CdmfChgTm"); 
			keyElement.setValue(String.valueOf(pcdItemKey.getCdmfChgTm()));
			populateCell(headerToColumnMap, dataRow, keyElement, style, null);

			keyElement = new MessageElement();
			keyElement.setName("CdmfChgBy"); 
			keyElement.setValue(String.valueOf(pcdItemKey.getCdmfChgBy()));
			populateCell(headerToColumnMap, dataRow, keyElement, style, null);
		}
	}


	/**
	 * @param worksheet
	 * @param headerToColumnMap
	 * @param dataRow
	 * @param dataElement
	 * @param style
	 * @param parent
	 */
	private void populateCell(Map<String, Integer> headerToColumnMap, HSSFRow dataRow,
			MessageElement dataElement, HSSFCellStyle style, String parent) {
		if (dataElement != null) {
			String headerName = null;
			if (!StringUtils.isEmpty(parent)) {
				headerName = parent + UNDERSCORE + dataElement.getName();
			}else {
				headerName = dataElement.getName();
			}
			if (!StringUtils.isEmpty(headerName)) {
				Integer columnNumber = headerToColumnMap.get(headerName);
				if (columnNumber != null) {
					createCell(dataRow, columnNumber, dataElement.getValue(), style);
				}
			}
		}
	}

	/**
	 * @param worksheet
	 * @param rowIndex
	 * @param aPsetElementsInfo
	 */
	private void setExtendedViewHeaderStyle(HSSFSheet worksheet, int rowIndex, PsetElementsInfo aPsetElementsInfo, WorkbookStyle aWorkbookStyle, HSSFWorkbook workBook) {
		Map<String, Integer> headerToColumnMap = aPsetElementsInfo.getHeaderToColumnMap();
		HSSFRow row = worksheet.getRow(2);
		if (row != null) {
			String[] nodes = { TIER_MIN_RATE, TIER_MAX_RATE, TIER_DATA_SEQ_NUM, TIER_DATA_TIER_BASIS_FACTOR,
					TIER_DATA_TIER_POINTS, TIER_DATA_TIER_LIMIT, TIER_DATA_TIER_RATE };
			for (String node : nodes) {
				row.getCell(headerToColumnMap.get(node)).setCellStyle(getExtendedTierheaderKeyColumnCellStyle(aWorkbookStyle, workBook));
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
		int changedRowIndex = rowIndex;
		HSSFRow dataRow = worksheet.createRow(rowIndex);
		MessageElement[] elements = null;

		// update common key data in excel sheet
		if(pcdItem.getPcdItemKey()!=null && pcdItem.getPcdItemKey().length > 0) {
			populateKeyElements(pcdItem, headerToColumnMap, dataRow, style);
			elements = getKeyElements(pcdItem.getPcdItemKey(0));
		}

		// update PCD key field data to worksheet column
		if(elements != null) {
			for (MessageElement node : elements) {
				populateCell(headerToColumnMap, dataRow, node, style, null);
			}
		}

		// Process pcd data and create cells in Excel
		MessageElement[] pcdDataElements = null;
		if(pcdItem.getPcdData()!=null) {
			pcdDataElements = pcdItem.getPcdData().get_any();
		}else{
			pcdDataElements = pcdItem.getPcdEntry().get_any();
		}

		// Process all non repeating Elements
		populateNonRepeatingElements(pcdItem, aPsetElementsInfo, dataRow, style);

		// Process Terms and create cells in Excel
		int termRowIndex = rowIndex;
		for (MessageElement termChild : pcdDataElements) {
			if(RATE_TERMS.equalsIgnoreCase(termChild.getName())) {
				termRowIndex++;
				if (worksheet.getRow(termRowIndex) == null) {
					dataRow = worksheet.createRow(termRowIndex);
				} else {
					dataRow = worksheet.getRow(termRowIndex);
				}
				List<MessageElement> termChildList = termChild.getChildren();
				for (MessageElement child : termChildList) {
					populateCell(headerToColumnMap, dataRow, child, style, RATE_TERMS);
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
			for (MessageElement termChild : pcdDataElements) {

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
										populateCell(headerToColumnMap, dataRow, child, style, BAL_DATA + strIndex);
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
										populateCell(headerToColumnMap, dataRow, rateChild, style,
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
		for (MessageElement tierChild : pcdDataElements) {
			if(tierChild.getName().startsWith(TIER_DATA)) {
				tierRowIndex++;
				if (worksheet.getRow(tierRowIndex) == null) {
					dataRow = worksheet.createRow(tierRowIndex);
				} else {
					dataRow = worksheet.getRow(tierRowIndex);
				}
				List<MessageElement> tierChildList = tierChild.getChildren();
				for (MessageElement child : tierChildList) {
					populateCell(headerToColumnMap, dataRow, child, style, TIER_DATA);
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
			StringBuffer path = new StringBuffer(ApplicationContext.getLabelsBasePath());
			path.append(ApplicationContext.getRegion().toLowerCase()).append("/");
			String fileName = "ParameterKeyLabels.properties";
			File file = new File(path.toString() + fileName);

			BufferedReader inputStream = null;
			try {
				if(file.exists()) {
					inputStream = new BufferedReader(new FileReader(file));
					labels.load(inputStream);
				}else {
					throw new BusinessError("Error reading Label properties file in Export Service: "+file.getAbsolutePath(), true);
				}
			}catch(Exception e) {
				ExceptionHandler.handleException(e);
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
					throw new BusinessError("Error reading Label properties file in Export Service: "+file.getAbsolutePath(), true);
				}
			}catch(Exception e) {
				ExceptionHandler.handleException(e);
			}finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		} catch (Exception e) {
			throw new BusinessError("Error reading Label properties file in Export Service: "+e.getMessage(), true);
		}	
		return labels;
	}

	private List<ParameterBean> handleAllCompany(List<ParameterBean>  pSets) {
		CompanyFacade companyFacade = new CompanyFacade();
		List<ParameterBean>  pSetsExpandedList = new ArrayList<ParameterBean>(); 
		pSets.forEach(pSet->{
			if("all".equalsIgnoreCase(pSet.getCompanyID())){
				List<CompanyBean>  companies = companyFacade.getCompanyDetails(pSet.getNumber());
				if(companies.size() == 0) {
					throw new BusinessError("Invalid Parameter :"+pSet.getNumber(), false);
				}
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
