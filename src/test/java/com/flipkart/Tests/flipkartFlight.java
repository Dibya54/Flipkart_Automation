package com.flipkart.Tests;

import com.flipkart.pageObjects.landingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class flipkartFlight  {

    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        // Navigate to Flipkart Flight Booking Page
        driver.get("https://www.flipkart.com/travel/flights");

        // Close login popup if it appears
//        try {
//            WebElement closeBtn = driver.findElement(By.xpath("//button[contains(text(),'✕')]"));
//            closeBtn.click();
//        } catch (Exception e) {
//            System.out.println("Login popup not displayed.");
//        }


        // Select Round Trip
        WebElement roundTrip = driver.findElement(By.xpath("(//div[@class='qsHXPi'])[2]"));
        roundTrip.click();

        // Select From city using dropdown
        Actions a = new Actions(driver);
        WebElement fromInput = driver.findElement(By.xpath("//input[@name='0-departcity']"));
        fromInput.click();
        fromInput.sendKeys("Bangalore");
        Thread.sleep(2000);
        // press down and enter using Actions
        a.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();

        // Select To city using dropdown
        WebElement toInput = driver.findElement(By.xpath("//input[@name='0-arrivalcity']"));
        toInput.click();
        toInput.sendKeys("Kolkata");
        Thread.sleep(2000);
        a.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();

        // Select Departure Date
        driver.findElement(By.xpath("//input[@name='0-datefrom']")).click();
        Thread.sleep(1000);
        WebElement departureDate = driver.findElement(By.xpath("(//button[contains(text(),'14')])[1]"));
        departureDate.click();

        // Select Return Date
        WebElement returnDate = driver.findElement(By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(2) > tr:nth-child(3) > td:nth-child(6) > div:nth-child(1) > button:nth-child(1)"));
        returnDate.click();

        // Open Traveller dropdown
        driver.findElement(By.cssSelector("input[value='1 Traveller | Economy ']")).click();
        Thread.sleep(1000);

        // Increase Adult count to 3
        int adultCount = 1;
        while (adultCount < 3) {
            driver.findElement(By.xpath("(//button[@class='QqFHMw +qYPut vSNayu'])[2]")).click();
            adultCount++;
        }

        // Add 1 Child
        driver.findElement(By.xpath("//div[@class='s+8PCn t6x2xH _9zHVSK _5XbPZt eTQwh+']//div[2]//div[2]//div[1]//div[3]//button[1]")).click();
        //Click on Done
        driver.findElement(By.xpath("//button[normalize-space()='Done']")).click();

        // Click Apply
        driver.findElement(By.xpath("//button[@type='button']")).click();
        



    }

}
