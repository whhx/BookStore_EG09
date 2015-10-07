package com.atguigu.ems.test;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

public class TestPOI {
	
	private static final String FILE_NAME = "f:\\workbook.xls";

	@Test
	public void test01() throws Exception {
		Workbook wb = new HSSFWorkbook();
	    FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
	    wb.write(fileOut);
	    fileOut.close();
	}
	
	@Test
	public void test02() throws IOException{
		Workbook wb = new HSSFWorkbook();
		// Workbook wb = new XSSFWorkbook();
		Sheet sheet1 = wb.createSheet("new sheet");
		Sheet sheet2 = wb.createSheet("second sheet");
		FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
		wb.write(fileOut);
		fileOut.close();
	}

}
