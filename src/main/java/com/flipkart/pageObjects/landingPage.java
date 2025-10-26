package com.flipkart.pageObjects;

import com.flipkart.abstractComponenets.abstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class landingPage extends abstractComponent {
    WebDriver driver;
    Actions actions;
    public  landingPage(WebDriver driver){
        //Intitalization
        super(driver);
        this.driver = driver;
        this.actions = new Actions(driver); // ✅ Initialize actions
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@autocomplete='off']")
    WebElement searchBox;

    public void goTo() {
        driver.manage().window().maximize();

        driver.get("https://www.google.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.navigate().to("https://www.flipkart.com/");

    }

    public SearchResultsPage searchProduct(String productName) {
        searchBox.sendKeys(productName);
        actions.moveToElement(searchBox).click().sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
        return new SearchResultsPage(driver);
    }
}
