package com.flipkart.Tests;


import com.flipkart.pageObjects.SearchResultsPage;
import com.flipkart.pageObjects.landingPage;
import org.testng.annotations.Test;

import java.util.List;

public class filterValidationTest extends baseTest {

    @Test
    public void validatePriceFilterAndSorting() throws InterruptedException{


        SearchResultsPage results = land.searchProduct("Laptop");

        results.filterByProcessor("Core i5")
                .setPriceRange("₹20000", "₹60000")
                .sortLowToHigh();

        results.filterByProcessor("Core i5")
                .setPriceRange("₹20000–₹60000")
                .sortLowToHigh();


        List<Integer> lowHighPrices = results.getAllPrices();
        results.validateSortingAscending(lowHighPrices);

        results.sortHighToLow();
        List<Integer> highLowPrices = results.getAllPrices();
        results.validateSortingDescending(highLowPrices);

        results.validatePriceRange(lowHighPrices, 20000, 60000);

  }

}
