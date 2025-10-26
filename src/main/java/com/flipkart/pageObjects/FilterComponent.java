package com.flipkart.pageObjects;

import com.flipkart.abstractComponenets.abstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class FilterComponent extends abstractComponent {
    WebDriver driver;

    public FilterComponent(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ===== Locators =====
    @FindBy(xpath = "//input[@placeholder='Search Processor']")
    WebElement processorSearchBox;

    @FindBy(xpath = "(//div[@class='XqNaEv'])[1]")
    WebElement firstSearchedProcessor;

    @FindBy(xpath = "//select[contains(.,'Min')]")
    WebElement minDropdown;

    @FindBy(xpath = "//div[@class='tKgS7w']//select[@class='Gn+jFg']")
    WebElement maxDropdown;

    @FindBy(css = "div[class*='zg-M3Z']:nth-child(4)")
    WebElement lowToHighButton;

    @FindBy(css = "div[class*='zg-M3Z']:nth-child(5)")
    WebElement highToLowButton;

    @FindBy(css = "div[class^='Nx9bqj _4b5DiR']")
    List<WebElement> priceElements;

    // ===== Actions =====
    public void searchProcessor(String processor) {
        processorSearchBox.sendKeys(processor);
        waitForElementToAppear(firstSearchedProcessor);
        safeClick(firstSearchedProcessor);
    }

    public int setMinPrice(String minPrice) {
        Select minSelect = new Select(minDropdown);
        minSelect.selectByVisibleText(minPrice);
        return Integer.parseInt(minPrice.replace("₹","").replace(",","").trim());
    }

    public int setMaxPrice(String maxPrice) {
        Select maxSelect = new Select(maxDropdown);
        maxSelect.selectByVisibleText(maxPrice);
        return Integer.parseInt(maxPrice.replace("₹","").replace(",","").trim());
    }

    public void sortLowToHigh() {
        safeClick(lowToHighButton);
    }

    public void sortHighToLow() {
        safeClick(highToLowButton);
    }

    public List<Integer> getAllPrices() {
        List<Integer> prices = new ArrayList<>();
        for (WebElement el : priceElements) {
            try {
                String txt = el.getText().replace("₹", "").replace(",", "").trim();
                if (!txt.isEmpty()) {
                    prices.add(Integer.parseInt(txt));
                }
            } catch (Exception ignored) {}
        }
        return prices;
    }


}
