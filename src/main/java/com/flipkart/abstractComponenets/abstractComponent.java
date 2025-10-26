package com.flipkart.abstractComponenets;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class abstractComponent {
    WebDriver driver;

    public abstractComponent(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private final int TIMEOUT = 15;

    // Wait until element located by By appears
    public void waitForElementToAppear(By findBy) {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    // Wait until WebElement appears
    public void waitForElementToAppear(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.visibilityOf(element));
    }

    // Scroll into view
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollBy(int pixels) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + pixels + ");");
    }


    // Safe click with scroll
    public void safeClick(WebElement element) {
        scrollToElement(element);
        element.click();
    }

    // Switch to newly opened tab
    public void switchToNewTab() {
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    }

    // Switch back to parent tab
    public void switchToParentTab(String parentWindow) {

        driver.switchTo().window(parentWindow);
    }


}
