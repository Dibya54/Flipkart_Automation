package com.flipkart.pageObjects;

import com.flipkart.abstractComponenets.abstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchResultsPage extends abstractComponent {
    WebDriver driver;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='yKfJKb row']")
    List<WebElement> productLinks;

    By processorSearchBox = By.xpath("//input[@placeholder='Search Processor']");
    By processorFirstOption = By.xpath("(//div[@class='XqNaEv'])[1]");
    By minDropdown = By.xpath("//select[contains(.,'Min')]");
    By maxDropdown = By.xpath("//div[@class='tKgS7w']//select[@class='Gn+jFg']");
    By lowToHighButton = By.cssSelector("div[class*='zg-M3Z']:nth-child(4)");
    By highToLowButton = By.cssSelector("div[class*='zg-M3Z']:nth-child(5)");
    By priceElements = By.cssSelector("div[class^='Nx9bqj _4b5DiR']");

    public SearchResultsPage filterByProcessor(String processor) throws InterruptedException {
        driver.findElement(processorSearchBox).sendKeys(processor);
        Thread.sleep(3000);
        driver.findElement(processorFirstOption).click();
        return this;
    }

//    public SearchResultsPage setPriceRange(String min, String max) throws InterruptedException {
//
//        String minValue = min.replace("₹", "").replace(",", "").trim();
//        String maxValue = max.replace("₹", "").replace(",", "").trim();
//
//        Select minSelect = new Select(driver.findElement(minDropdown));
//        minSelect.selectByVisibleText(minValue);
//        //minSelect.selectByValue(min.replace("₹", "").replace(",", ""));
//
//        Select maxSelect = new Select(driver.findElement(maxDropdown));
//        maxSelect.selectByVisibleText(maxValue);
//        //maxSelect.selectByValue(max.replace("₹", "").replace(",", ""));
//
//        Thread.sleep(3000); // wait for refresh
//        return this;
//    }

    // -------- Existing single-string method (keep as is) --------
    public SearchResultsPage setPriceRange(String priceRange) throws InterruptedException {
        // Split the range using en-dash
        String[] range = priceRange.split("–");
        String minValue = range[0].replace("₹", "").replace(",", "").trim();
        String maxValue = range[1].replace("₹", "").replace(",", "").trim();

        // Select min price from dropdown
        Select minSelect = new Select(driver.findElement(minDropdown));
        minSelect.selectByValue(minValue);

        // Select max price from dropdown
        Select maxSelect = new Select(driver.findElement(maxDropdown));
        maxSelect.selectByValue(maxValue);

        Thread.sleep(3000); // wait for the page to refresh after filter

        return this; // return SearchResultsPage for chaining
    }

    // -------- New overloaded method: separate min and max --------
    public SearchResultsPage setPriceRange(String minPrice, String maxPrice) throws InterruptedException {
        String minValue = minPrice.replace("₹", "").replace(",", "").trim();
        String maxValue = maxPrice.replace("₹", "").replace(",", "").trim();

        Select minSelect = new Select(driver.findElement(minDropdown));
        minSelect.selectByValue(minValue);

        Select maxSelect = new Select(driver.findElement(maxDropdown));
        maxSelect.selectByValue(maxValue);

        Thread.sleep(3000); // wait for the page to refresh
        return this;
    }





    public SearchResultsPage sortLowToHigh() throws InterruptedException {
        driver.findElement(lowToHighButton).click();
        Thread.sleep(3000);
        return this;
    }

    public SearchResultsPage sortHighToLow() throws InterruptedException {
        driver.findElement(highToLowButton).click();
        Thread.sleep(3000);
        return this;
    }

    public List<Integer> getAllPrices() {
        List<WebElement> priceElementsList = driver.findElements(priceElements);
        List<Integer> prices = new ArrayList<>();

        for (WebElement el : priceElementsList) {
            String text = el.getText().replace("₹", "").replace(",", "").trim();
            if (!text.isEmpty()) {
                prices.add(Integer.parseInt(text));
            }
        }
        return prices;
    }

    public void validateSortingAscending(List<Integer> prices) {
        List<Integer> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        if (prices.equals(sorted)) {
            System.out.println("✅ Low to High sorting correct.");
        } else {
            System.out.println("❌ Sorting failed!");
        }
    }

    public void validateSortingDescending(List<Integer> prices) {
        List<Integer> sorted = new ArrayList<>(prices);
        sorted.sort(Collections.reverseOrder());
        if (prices.equals(sorted)) {
            System.out.println("✅ High to Low sorting correct.");
        } else {
            System.out.println("❌ Sorting failed!");
        }
    }

    public void validatePriceRange(List<Integer> prices, int min, int max) {
        for (int price : prices) {
            if (price < min || price > max) {
                System.out.println("❌ Price outside range: " + price);
                return;
            }
        }
        System.out.println("✅ All products within price range.");
    }


    public int getResultsCount() {

        return productLinks.size();
    }

    public ProductPage clickProduct(int index) {
        productLinks.get(index).click();
        return new ProductPage(driver);
    }
}
