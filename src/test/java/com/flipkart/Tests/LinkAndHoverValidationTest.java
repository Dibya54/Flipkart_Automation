package com.flipkart.Tests;

import com.flipkart.pageObjects.HoverCheck;
import com.flipkart.pageObjects.SearchResultsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LinkAndHoverValidationTest extends baseTest {


    SearchResultsPage results;

    @BeforeMethod
    public void setupSearch() {

        results = land.searchProduct("Laptop");  // common step
    }

    @Test
    public void validateHoverFeature() {
        HoverCheck hover = new HoverCheck(driver);
        hover.hoverAndValidateLinks();
    }


}
