package com.flipkart.pageObjects;

import com.flipkart.abstractComponenets.abstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductPage extends abstractComponent {
    WebDriver driver;

    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ====== Locators ======
    @FindBy(xpath = "//button[@class='QqFHMw vslbG+ In9uk2']")
    WebElement addToCartBtn;

    @FindBy(css = "a[class*='T2CNXf QqLTQ-']")
    WebElement placedOrderItem;

    @FindBy(css = "button[class$='QqFHMw zA2EfJ _7Pd1Fp']")
    WebElement placeOrderBtn;

    @FindBy(css = "span[class*='PXFoIh']")
    List<WebElement> deliveryPageItems;

    By popupToast = By.xpath("//div[@class='eIDgeN']"); // popup message (unavailable product)

    // ====== Actions ======

    public void switchToNewTab() {
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    }

    public void addToCart() {
        scrollBy(1500); // scroll like in standalone test
        addToCartBtn.click();
    }

    public boolean isProductAvailable() {
        try {
            Thread.sleep(2000); // small wait for popup
            List<WebElement> popups = driver.findElements(popupToast);
            if (!popups.isEmpty()) {
                System.out.println("Popup Message: " + popups.get(0).getText());
                return false; // product not available
            }
        } catch (Exception e) {
            System.out.println("No popup found.");
        }
        return true; // product available
    }

    public void placeOrder() throws InterruptedException {
        System.out.println("Item in cart: " + placedOrderItem.getText());
        placeOrderBtn.click();
        Thread.sleep(5000);

        for (WebElement element : deliveryPageItems) {
            System.out.println("After clicking Place Order: " + element.getText());
        }
    }

    public void closeAndReturnToParent(String parentWindow) {
        if (driver.getWindowHandles().size() > 1) {
            driver.close(); // close current tab
            driver.switchTo().window(parentWindow); // return to parent tab
        }
    }
}
