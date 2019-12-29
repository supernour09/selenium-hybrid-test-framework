package utils;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * using POI lib to read from Excel files 
 *
 *
 */
public class ExcelUtils {
	
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	static Logger logger = LogManager.getLogger(ExcelUtils.class);
	
	public ExcelUtils(String excelPath , String sheetName) {
		try {
			workbook = new XSSFWorkbook(excelPath);
			sheet = workbook.getSheet(sheetName);
		} catch (IOException e) {
			logger.error(e);
		}
		
	}
	
	public int getRowCount() {
		int rowCount = 0;
		try {
			rowCount = sheet.getPhysicalNumberOfRows();
		} catch (Exception e) {
			logger.error(e);
		}
		return rowCount;
	}
	
	public int getColCount() {
		int colCount = 0;
		try {
			colCount = sheet.getRow(0).getPhysicalNumberOfCells();
		} catch (Exception e) {
			logger.error(e);
		}
		return colCount;
	}
	
	public Object getCellDataString(int rowNo , int colNo) {
		Object cellData = "";
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
     
		try {
			Cell cell = sheet.getRow(rowNo).getCell(colNo); 
			switch (evaluator.evaluateInCell(cell).getCellType()) {
			
			case NUMERIC:
				//as test case supppose to only have strings but the phone
				//number in excel seems like number we have to convert it
				double num = cell.getNumericCellValue();
				Integer intNum = (int)num;
				cellData = intNum.toString();
				logger.debug(cellData);
				break;
			case STRING:
				cellData = cell.getStringCellValue();
				logger.debug(cellData);
				break;
			case BOOLEAN:
				cellData = cell.getBooleanCellValue();
				logger.debug(cellData);
				break;
			default:
				cellData = cell.getStringCellValue();
				logger.debug(cellData);
				break;
			}
		}catch(NullPointerException e) {
			cellData = "";
		}
		return cellData;
	}
	
	public int getCellDataNum(int rowNo , int colNo) {
		int num = 0;
		try {
			Cell cell = sheet.getRow(rowNo).getCell(colNo);
			num = (int) cell.getNumericCellValue();
		}catch(Exception e) {
			logger.error(e);
		}
		return num;
		
	}
	

}
