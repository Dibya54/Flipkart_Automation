package com.flipkart.Tests;


import com.flipkart.pageObjects.DeliveryPage;
import com.flipkart.pageObjects.ProductPage;
import com.flipkart.pageObjects.SearchResultsPage;
import com.flipkart.pageObjects.landingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



    public class orderFlowFlipkart extends baseTest {

        @Test
        public void validateOrderFlow() throws InterruptedException {
            SearchResultsPage results = land.searchProduct("Laptop");
            int count = results.getResultsCount();
            System.out.println("Products found: " + count);

            int limit = Math.min(count, 5);
            String parentWindow = driver.getWindowHandle();

            for (int i = 0; i < limit; i++) {
                ProductPage productPage = null;
                try {
                    productPage = results.clickProduct(i);
                    productPage.switchToNewTab();

                    productPage.addToCart();

                    if (productPage.isProductAvailable()) {
                        System.out.println("✅ Product added successfully!");
                        productPage.placeOrder();
                    } else {
                        System.out.println("❌ Product not available");
                    }

                } catch (Exception e) {
                    System.out.println("⚠️ Error while handling product " + (i + 1) + ": " + e.getMessage());
                } finally {
                    if (productPage != null) {
                        productPage.closeAndReturnToParent(parentWindow);
                    }
                }
            }
        }
    }

