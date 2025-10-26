package com.flipkart.TestComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.flipkart.Tests.baseTest;
import com.flipkart.resources.extentReporterNg;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListerForReports extends baseTest implements ITestListener {

    ExtentTest test;

    ExtentReports extent = extentReporterNg.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();


    @Override
    public void onTestStart(ITestResult result) {

        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test); // Unique thread id
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        extentTest.get().log(Status.PASS, "Test Case Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().
                    getField("driver").get(result.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String filePath = takeScreenShot(result.getMethod().getMethodName(), driver);
        extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
        // You can add code to capture screenshot here
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        System.out.println("Test Skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Test Failed but within success %: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {

        System.out.println("Execution Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();
    }

}
