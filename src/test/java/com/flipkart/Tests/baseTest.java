package com.flipkart.Tests;

import com.flipkart.pageObjects.landingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class baseTest {

    public WebDriver driver;
    public landingPage land;

    public WebDriver initializeDriver() throws IOException {

        //Properties Class


        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/flipkart/resources/Global.properties");

        prop.load(fis);
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");
        //prop.getProperty("browser");

        if(browserName.contains("chrome")){

            // Setup ChromeDriver automatically
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
            options.addArguments("--start-maximized");

//            if(browserName.contains("headless")){
//                options.addArguments("headless");
//            }

            // Create a new ChromeDriver instance
            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440,900));//full screen

        } else if (browserName.equalsIgnoreCase("edge")) {
            // Setup EdgeDriver automatically
            WebDriverManager.edgedriver().setup();

            EdgeOptions options = new EdgeOptions();
            options.addArguments("--start-maximized");

            driver = new EdgeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;

    }

    @BeforeMethod(alwaysRun = true)
    public landingPage launchApplication() throws IOException {
        driver = initializeDriver();

        land = new landingPage(driver);

        land.goTo();

        return land;
    }

    public String takeScreenShot(String testCaseName, WebDriver driver){
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir")+"//reports//"+testCaseName+".png");

        try {
            FileUtils.copyFile(source, file);
            return System.getProperty("user.dir")+"//reports//"+testCaseName+".png";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){

        driver.close();
    }
}
