package com.flipkart.pageObjects;

import com.flipkart.abstractComponenets.abstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class HoverCheck extends abstractComponent {

    WebDriver driver;
    private final int TIMEOUT = 10;

    public HoverCheck(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='bpjkJb']/span[contains(text(),'Electronics')]")
    WebElement hoverElectronics;

//    @FindBy(xpath = "//div[@class='_1kidPb']//a")
//    List<WebElement> subMenuLinks;
    public void hoverAndValidateLinks() {
        Actions actions = new Actions(driver);
        actions.moveToElement(hoverElectronics).perform();

        // ✅ Wait until submenu appears dynamically
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        // ✅ Wait for at least one submenu element to appear
       WebElement electronicsMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Electronics']")));
       actions.moveToElement(electronicsMenu).perform();


        // ✅ Now fetch elements dynamically
        //  List<WebElement> subMenuLinks = driver.findElements(By.xpath("//div[@class='wZsanD']//a"));
        // Wait until *all submenu links* under Electronics are present
        List<WebElement> subMenuLinks = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='wZsanD']//a"))
        );
        System.out.println("Found " + subMenuLinks.size() + " submenu links under Electronics.");

        // Loop through all links dynamically
        for (WebElement link : subMenuLinks) {
            try {
                String linkText = link.getText().trim();
                String url = link.getAttribute("href");

                if (url == null || url.isEmpty()) {
                    System.out.println("⚠️ Link without href: " + linkText);
                    continue;
                }

                // ✅ Check visibility
                if (!link.isDisplayed()) {
                    System.out.println("⚠️ Hidden link: " + linkText + " → " + url);
                } else {
                    System.out.println("Visible link: " + linkText + " → " + url);
                }

                // ✅ Check if clickable
                wait.until(ExpectedConditions.elementToBeClickable(link));

                // ✅ Check if link is broken
                validateLink(url);

            } catch (TimeoutException e) {
                System.out.println("❌ Link not clickable: " + link.getText());
            } catch (Exception e) {
                System.out.println("❌ Exception while checking link: " + e.getMessage());
            }
        }
    }

    public void validateLink(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.setConnectTimeout(5000);
            conn.connect();
            int respCode = conn.getResponseCode();

            if (respCode >= 400) {
                System.out.println("❌ Broken link: " + url + " | Response: " + respCode);
            } else {
                System.out.println("✅ Valid link: " + url + " | Response: " + respCode);
            }
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("❌ Could not connect: " + url);
        }
    }
}
