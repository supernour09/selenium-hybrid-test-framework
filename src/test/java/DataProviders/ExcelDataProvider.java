package DataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utils.ExcelUtils;

public class ExcelDataProvider {
	
	private ExcelUtils excel ;
	private int row ;
	private int col ;
	private Object[][] data; 
	Logger logger = LogManager.getLogger(ExcelDataProvider.class);
	

	public ExcelDataProvider(String DataPath ,String SheetName ) {
		excel = new ExcelUtils(DataPath,SheetName );
		row = excel.getRowCount();
		col = excel.getColCount();
		//row-1 to remove the first row which have the column name
		data = new Object[row-1][col];
		this.getDataFromSheet();
		
	}
	
	public int getNumFromCell(int row , int col) {
		return excel.getCellDataNum(row, col);
	}
	
	public Object[] getRow(int row) {
		return data[row];
	}
	
	public int getRowCount() {
		return row;
	}
	
	public int getColCount() {
		return col;
	}
	
	public Object[][] getData() {
		return data;
	}
	/**
	 * used to get data from the excel sheet using the ExcelUtils Class
	 */
	private void getDataFromSheet() {
		logger.debug("No r: " + row + " Col: " + col);
		for (int i = 1; i < row; i++) {
			for (int j = 0; j < col; j++) {
				data[i-1][j] = excel.getCellDataString(i, j);
			}
		}
	}

}
