package com.framework.utils.excelutils;

import static tests.BaseTest.testDataExcelFileName;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Platform;

/**
 * Created by Abhinav.
 */
public class ExcelUtil {
    //Main Directory of the project
    public static final String currentDir = System.getProperty("user.dir");

    //Location of Test data excel file
    public static String testDataExcelPath = null;

    //Excel WorkBook
    private static XSSFWorkbook excelWBook;

    //Excel Sheet
    private static XSSFSheet excelWSheet;

    //Excel cell
    private static XSSFCell cell;

    //Excel row
    private static XSSFRow row;

    //Row Number
    public static int rowNumber;

    //Column Number
    public static int columnNumber;
    
    //HashMap fetching all test sheet name
    public static HashMap<String, HashMap<String, String>> hashMapTestData;
 
    //Setter and Getters of row and columns
    public static void setRowNumber(int pRowNumber) {
        rowNumber = pRowNumber;
    }

    public static int getRowNumber() {
        return rowNumber;
    }

    public static void setColumnNumber(int pColumnNumber) {
        columnNumber = pColumnNumber;
    }

    public static int getColumnNumber() {
        return columnNumber;
    }

    // This method has two parameters: "Test data excel file name" and "Excel sheet name"
    // It creates FileInputStream and set excel file and excel sheet to excelWBook and excelWSheet variables.
	public static void setExcelFileSheet(String sheetName) {
        //MAC or Windows Selection for excel path
        if (Platform.getCurrent().toString().equalsIgnoreCase("MAC")) {
            testDataExcelPath = currentDir + "//src//test//java//resources//";
        } else if (Platform.getCurrent().toString().contains("WIN")) {
            testDataExcelPath = currentDir + "\\src\\test\\java\\resources\\";
        }
        try {
            // Open the Excel file	
            FileInputStream ExcelFile = new FileInputStream(testDataExcelPath + testDataExcelFileName);
            excelWBook = new XSSFWorkbook(ExcelFile);
            excelWSheet = excelWBook.getSheet(sheetName);
            excelWSheet = excelWBook.getSheet("LoginData");
        } catch (Exception e) {
            try {
                throw (e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
	
	/**
	 * This function will fetch data from excel file and put into hash map
	 * @return 
	 */
	public static HashMap<String, HashMap<String, String>> fetchTestCaseInformation() {
		HashMap<String, HashMap<String, String>> hashMapTestData = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> hashMapData = new HashMap<String, String>();
		ArrayList<String> array = new ArrayList<String>();
		DataFormatter formatter = new DataFormatter();
		try {
			int noOfColumns = excelWSheet.getRow(0).getLastCellNum();
			int noOfRows = excelWSheet.getPhysicalNumberOfRows();
			for(int i = 1; i < noOfColumns; i++ ) {
				array.add(formatter.formatCellValue(excelWSheet.getRow(0).getCell(i)));
			}
			
			for(int i = 1; i < noOfRows; i++ ) {
				hashMapData.clear();
				int j = 1;
				for(String data : array) {
					hashMapData.put(data, formatter.formatCellValue(excelWSheet.getRow(i).getCell(j)));
					j++;
				}
				String testCaseName = formatter.formatCellValue(excelWSheet.getRow(i).getCell(0));
				hashMapTestData.put(testCaseName, hashMapData);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
		return hashMapTestData;
	}

    //This method reads the test data from the Excel cell.
    //We are passing row number and column number as parameters.
    public static String getCellData(int RowNum, int ColNum) {
        try {
            cell = excelWSheet.getRow(RowNum).getCell(ColNum);
            DataFormatter formatter = new DataFormatter();
            String cellData = formatter.formatCellValue(cell);
            return cellData;
        } catch (Exception e) {
            throw (e);
        }
    }

    //This method takes row number as a parameter and returns the data of given row number.
    public static XSSFRow getRowData(int RowNum) {
        try {
            row = excelWSheet.getRow(RowNum);
            return row;
        } catch (Exception e) {
            throw (e);
        }
    }

    //This method gets excel file, row and column number and set a value to the that cell.
    public static void setCellData(String value, int RowNum, int ColNum) {
        try {
            row = excelWSheet.getRow(RowNum);
            cell = row.getCell(ColNum);
            if (cell == null) {
                cell = row.createCell(ColNum);
                cell.setCellValue(value);
            } else {
                cell.setCellValue(value);
            }
            // Constant variables Test Data path and Test Data file name
            FileOutputStream fileOut = new FileOutputStream(testDataExcelPath + testDataExcelFileName);
            excelWBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            try {
                throw (e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

	
}
