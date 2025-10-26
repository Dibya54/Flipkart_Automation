package com.flipkart.Utility;

import com.flipkart.dataFromExcel.dataDriven;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class ExcelDataProvider {

    @DataProvider(name = "testCases")
    public Object[][] getAllTestCases() throws IOException {
        dataDriven excel = new dataDriven();
        return excel.getData("TestData"); // <-- matches the sheet name in Excel
    }
}
