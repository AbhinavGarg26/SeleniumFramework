package com.framework.utility;

import com.framework.utils.excelutils.ExcelUtil;

import tests.BaseTest;

public class Temp {
	public static void main(String args[]) {
		new BaseTest();
		ExcelUtil.setExcelFileSheet("TestCaseData");
        
        ExcelUtil.hashMapTestData = ExcelUtil.fetchTestCaseInformation();
	}
}
