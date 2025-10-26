package com.flipkart.dataFromExcel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class dataDriven {

    public  Object[][] getData(String testCaseName) throws IOException {
        //Identigy the testcases columns by accessing the 1st row

        FileInputStream fis = new FileInputStream("C:\\Users\\HP\\Downloads\\Book1.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(testCaseName);

        int rows = sheet.getPhysicalNumberOfRows();    // total rows
        int cols = sheet.getRow(0).getLastCellNum();   // total columns

        Object[][] data = new Object[rows - 1][cols];  // skip header row

        for (int i = 1; i < rows; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    data[i - 1][j] = "";
                } else if (cell.getCellType() == CellType.STRING) {
                    data[i - 1][j] = cell.getStringCellValue();
                } else {
                    data[i - 1][j] = String.valueOf(cell.getNumericCellValue());
                }
            }
        }
        workbook.close();
        return data;
    }
}
