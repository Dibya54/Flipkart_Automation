package com.flipkart.pageObjects;

import com.flipkart.abstractComponenets.abstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DeliveryPage extends abstractComponent {
    WebDriver driver;

    public DeliveryPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "span[class*='PXFoIh']")
    List<WebElement> deliveryDetails;

    public boolean hasDeliveryDetails() {
        return !deliveryDetails.isEmpty();
    }

    public void printDeliveryDetails() {
        for (WebElement detail : deliveryDetails) {
            System.out.println("Delivery page info: " + detail.getText());
        }
    }
}
