package com.flipkart.Tests;

import com.flipkart.pageObjects.SearchResultsPage;
import com.flipkart.pageObjects.landingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.*;
import java.util.stream.Collectors;

public class filterValidatione2e {

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Landing page
        landingPage landing = new landingPage(driver);
        landing.goTo();

        String productName = "Laptop";
        System.out.println("Searching for product: " + productName);

        // Search results page
        SearchResultsPage results = landing.searchProduct(productName);
        int count = results.getResultsCount();
        System.out.println("Products found: " + count);

        WebElement processorSearchBox = driver.findElement(By.xpath("//input[@placeholder='Search Processor']"));
        processorSearchBox.sendKeys("Core i5");

        Thread.sleep(3000);

        WebElement firstSearchedProcessor = driver.findElement(By.xpath("(//div[@class='XqNaEv'])[1]"));
        firstSearchedProcessor.click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,100)");

        WebElement minDropdown  = driver.findElement(By.xpath("//select[contains(.,'Min')]"));
        WebElement maxDropdown = driver.findElement(By.xpath("//div[@class='tKgS7w']//select[@class='Gn+jFg']"));

        Select minSelect = new Select(minDropdown);
        for (WebElement option : minSelect.getOptions()) {
            System.out.println("Min options: " + option.getText());
        }

        minSelect.selectByVisibleText("₹20000");
        js.executeScript("window.scrollBy(0,200)");

        Select maxSelect = new Select(maxDropdown);
        for (WebElement option : maxSelect.getOptions()) {
            System.out.println("Max options: " + option.getText());
        }

        maxSelect.selectByVisibleText("₹60000");
        js.executeScript("window.scrollBy(0,200)");

        Thread.sleep(3000); // wait for filter refresh

        // ===== Store applied filter dynamically =====
        int minVal = Integer.parseInt(minSelect.getFirstSelectedOption().getText().replace("₹","").replace(",","").trim());
        int maxVal = Integer.parseInt(maxSelect.getFirstSelectedOption().getText().replace("₹","").replace(",","").trim());

        // ===== Price filter =====
        Map<String, Integer> appliedFilter = new HashMap<>();
        appliedFilter.put("MinPrice", minVal);
        appliedFilter.put("MaxPrice", maxVal);

        WebElement lowToHighButton = driver.findElement(By.cssSelector("div[class*='zg-M3Z']:nth-child(4)"));
        lowToHighButton.click();
        Thread.sleep(4000);

        Map<Integer, List<String>> lowHighMap = new LinkedHashMap<>();
        List<Integer> capturedLowHigh = new ArrayList<>();

        List<WebElement> lowHighPricesOfElements = driver.findElements(By.cssSelector("div[class^='Nx9bqj _4b5DiR']"));

        for (WebElement priceElement : lowHighPricesOfElements){
            try {
                String priceText = priceElement.getText().replace("₹", "").replace(",", "").trim();
                if(!priceText.isEmpty()){
                    int price = Integer.parseInt(priceText);
                    List<WebElement> productNames = driver.findElements(By.cssSelector("div[class^='KzDlHZ']"));
                    for (WebElement productTitle : productNames){
                        String titleOfProduct = productTitle.getText();
                        lowHighMap.computeIfAbsent(price, k -> new ArrayList<>()).add(titleOfProduct);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        // Expected sorted (with duplicates preserved)
        List<Integer> expectedLowHigh = new ArrayList<>(capturedLowHigh);
        Collections.sort(expectedLowHigh);

        if (capturedLowHigh.equals(expectedLowHigh)) {
            System.out.println("✅ Low to High sorting is correct.");
        } else {
            System.out.println("❌ Low to High sorting is incorrect!");
            System.out.println("Expected: " + expectedLowHigh);
            System.out.println("Found: " + capturedLowHigh);
        }

        // ===== High to Low =====
        WebElement highToLow = driver.findElement(By.cssSelector("div[class^='zg-M3Z']:nth-child(5)"));
        highToLow.click();
        Thread.sleep(4000);

        Map<Integer, List<String>> highLowMap  = new LinkedHashMap<>();
        List<Integer> capturedHighLow = new ArrayList<>();
        List<WebElement> highLowPricesOfElements = driver.findElements(By.cssSelector("div[class^='Nx9bqj _4b5DiR']"));

        for (WebElement priceElement : highLowPricesOfElements){
            String priceText = priceElement.getText().replace("₹", "").replace(",", "").trim();
            if(!priceText.isEmpty()){
                int price = Integer.parseInt(priceText);
                capturedHighLow.add(price);
                List<WebElement> productNames = driver.findElements(By.cssSelector("div[class^='KzDlHZ']"));
                for (WebElement productTitle : productNames){
                    String titleOfProduct = productTitle.getText();
                    lowHighMap.computeIfAbsent(price, k -> new ArrayList<>()).add(titleOfProduct);
                }
            }
        }

        // Expected sorted descending (with duplicates preserved)
        List<Integer> expectedHighLow = new ArrayList<>(capturedHighLow);
        expectedHighLow.sort(Collections.reverseOrder());

        if (capturedHighLow.equals(expectedHighLow)) {
            System.out.println("✅ High to Low sorting is correct.");
        } else {
            System.out.println("❌ High to Low sorting is incorrect!");
            System.out.println("Expected: " + expectedHighLow);
            System.out.println("Found: " + capturedHighLow);
        }

        System.out.println("\n=== Price Range Validation ===");
        boolean hasBelowMin = checkBelowMin(capturedLowHigh, minVal);
        boolean hasAboveMax = checkAboveMax(capturedLowHigh, maxVal);

        if (!hasBelowMin && !hasAboveMax) {
            System.out.println("✅ All products are within the applied price range.");
        } else {
            System.out.println("❌ Some products are outside the applied price range!");
        }

        driver.close();

    }

    // ===== Function to check below min =====
    public static boolean checkBelowMin(List<Integer> prices, int minVal) {
        boolean found = false;
        for (int price : prices) {
            if (price < minVal) {
                System.out.println("⚠️ Price below min filter: " + price);
                found = true;
            }
        }
        return found;
    }

    // ===== Function to check above max =====
    public static boolean checkAboveMax(List<Integer> prices, int maxVal) {
        boolean found = false;
        for (int price : prices) {
            if (price > maxVal) {
                System.out.println("⚠️ Price above max filter: " + price);
                found = true;
            }
        }
        return found;
    }

}
