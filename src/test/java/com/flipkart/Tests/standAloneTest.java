package com.flipkart.Tests;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.sound.midi.Soundbank;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class standAloneTest {

    public static void main(String[] args) throws InterruptedException {

        WebDriverManager.chromedriver().setup();

        // Create a new ChromeDriver instance
        WebDriver driver = new ChromeDriver();

        String productName = "Laptop";

        driver.manage().window().maximize();

        driver.get("https://www.google.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.navigate().to("https://www.flipkart.com/");

        // Close login popup if present
//        try {
//            WebElement closeBtn = driver.findElement(By.xpath("//button[contains(text(),'✕')]"));
//            closeBtn.click();
//        } catch (Exception e) {
//            System.out.println("Login popup not displayed.");
//        }

        System.out.println("Entered product name"+ productName);
        WebElement searchBox = driver.findElement(By.xpath("//input[@autocomplete='off']"));
        searchBox.sendKeys(productName);

        Actions actions = new Actions(driver);
        actions.moveToElement(searchBox).click().sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();

        List<WebElement> productLinks = driver.findElements(By.xpath("//div[@class='yKfJKb row']"));
        System.out.println(productLinks.size());

        WebDriverWait wait;
        int limit = Math.min(productLinks.size(), 5);

        String parentWindow = driver.getWindowHandle();

        for (int i = 0; i < limit; i++) {

            try {
                productLinks = driver.findElements(By.xpath("//div[@class='yKfJKb row']"));
            // Click the first product dynamically
            if (!productLinks.isEmpty() && i < productLinks.size()) {
                productLinks.get(i).click();
                System.out.println("Clicked on the first product!");

                // Wait for Add to Cart button to appear
                wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                // Get current window handles
                Set<String> handles = driver.getWindowHandles();
                List<String> tabs = new ArrayList<>(handles);

                // Switch to new tab (index 1 is the new tab usually)
                driver.switchTo().window(tabs.get(1));

                wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                // get all matching buttons

                // Scroll down by 1500 pixels
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0,1500);");
                WebElement addToCart = driver.findElement(By.xpath("//button[@class='QqFHMw vslbG+ In9uk2']"));
                addToCart.click();
                // Call function to check availability
                boolean available = isProductAvailable(driver, wait);
                System.out.println(available);
                //WebElement addToCartButton = driver.findElement(By.cssSelector("a[class*='_9Wy27C']"));


                if (available) {
                    System.out.println("✅ Product successfully added to cart!");
                    //addToCart.click();
                    System.out.println("Add to cart button clicked");
                    WebElement placedOrderItem = driver.findElement(By.cssSelector("a[class*='T2CNXf QqLTQ-']"));
                    System.out.println(placedOrderItem.getText());
                    WebElement placedOrderButton = driver.findElement(By.cssSelector("button[class$='QqFHMw zA2EfJ _7Pd1Fp']"));
                    placedOrderButton.click();
                    Thread.sleep(5000);
                    List<WebElement> deliveryPage = driver.findElements(By.cssSelector("span[class*='PXFoIh']"));
                    for (WebElement element : deliveryPage) {
                        String text = element.getText();
                        System.out.println("After clicking on place order" + text);
                    }


                } else {
                    System.out.println("❌ Product not available, skipping...");
                }

            } else {
                System.out.println("No products found.");
            }
        }catch (Exception e){
                System.out.println("⚠️ Error while clicking product " + (i + 1) + ": " + e.getMessage());
            }

            finally {
                Set<String> handles = driver.getWindowHandles();
                if (handles.size() > 1) {
                    driver.close();
                    List<String> tabs = new ArrayList<>(handles);
                    driver.switchTo().window(parentWindow);
                }


            }
            }

    }

    public static boolean isProductAvailable(WebDriver driver, WebDriverWait wait) {
        // Xpath of popup message (toast)
        String popupXpath = "//div[@class='eIDgeN']";

        // Small wait to let popup appear if it’s coming
        try {
            Thread.sleep(2000);
            List<WebElement> popups = driver.findElements(By.xpath(popupXpath));
            if (!popups.isEmpty()) {
                System.out.println("Popup Message: " + popups.get(0).getText());
                return false; // Product not available
            }
        } catch (Exception e) {
            System.out.println("No popup found.");
        }
        return true; // Product added successfully
    }

}