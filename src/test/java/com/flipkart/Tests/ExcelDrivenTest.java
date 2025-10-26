package com.flipkart.Tests;

import com.flipkart.Utility.ExcelDataProvider;
import com.flipkart.pageObjects.FilterComponent;
import com.flipkart.pageObjects.HoverCheck;
import com.flipkart.pageObjects.SearchResultsPage;
import com.flipkart.pageObjects.landingPage;
import org.testng.annotations.Test;

import java.util.List;

public class ExcelDrivenTest extends baseTest{

    @Test(dataProvider = "testCases", dataProviderClass = ExcelDataProvider.class)
    public void runTestCases(String tcId, String module, String description,
                             String action, String inputData) throws InterruptedException {

        System.out.println("Running Test: " + tcId + " | " + description);

        switch (action) {
            case "searchProduct":
                // direct search
                land.searchProduct(inputData.trim());
                break;

            case "hoverMenu":
                // Hover Electronics / Men / Women etc.
                land.searchProduct(inputData.trim());
                HoverCheck hover = new HoverCheck(driver);
                hover.hoverAndValidateLinks();
                break;

            case "validateLinks":
                land.searchProduct(inputData.trim());
                HoverCheck hoverLinks = new HoverCheck(driver);
                hoverLinks.hoverAndValidateLinks();
                break;

            case "searchAndFilter":
                // InputData: "Laptop → ₹30000–₹60000" OR "Laptop → Core i5"
                String[] filterData = inputData.split("→");
                String searchTerm = filterData[0].trim();
                String filterValue = filterData[1].trim();

                SearchResultsPage results = land.searchProduct(searchTerm);

                if (filterValue.contains("₹")) {
                    // price range
                    String[] prices = filterValue.split("[-–]"); // supports both dash types
                    if (prices.length < 2) {
                        throw new IllegalArgumentException("❌ Invalid price range format. Expected '₹min - ₹max' but got: " + filterValue);
                    }
                    String minPrice = prices[0].trim();
                    String maxPrice = prices[1].trim();

                   // results.setPriceRange(minPrice, maxPrice).sortLowToHigh();

                    String priceRange = minPrice + "–" + maxPrice; // en-dash
                    results.setPriceRange(priceRange).sortLowToHigh();


                    List<Integer> filteredPrices = results.getAllPrices();
                    results.validatePriceRange(filteredPrices,
                            Integer.parseInt(minPrice.replace("₹", "").replace(",", "")),
                            Integer.parseInt(maxPrice.replace("₹", "").replace(",", ""))
                    );
                    results.validateSortingAscending(filteredPrices);

                } else {
                    // processor filter
                    results.filterByProcessor(filterValue);
                    int count = results.getResultsCount();
                    if (count > 0) {
                        System.out.println("✅ Processor filter applied. Products found: " + count);
                    } else {
                        System.out.println("❌ No products found for processor: " + filterValue);
                    }
                }
                break;

            case "searchAndSort":
                // InputData: "Laptop → Price: Low to High" OR "Laptop → Newest First"
                String[] sortData = inputData.split("→");
                String searchKeyword = sortData[0].trim();
                String sortOption = sortData[1].trim();

                SearchResultsPage sortResults = land.searchProduct(searchKeyword);

                if (sortOption.equalsIgnoreCase("Price: Low to High")) {
                    sortResults.sortLowToHigh();
                    List<Integer> lowHighPrices = sortResults.getAllPrices();
                    sortResults.validateSortingAscending(lowHighPrices);

                } else if (sortOption.equalsIgnoreCase("High to Low")) {
                    sortResults.sortHighToLow();
                    List<Integer> highLowPrices = sortResults.getAllPrices();
                    sortResults.validateSortingDescending(highLowPrices);
                } else {
                    throw new IllegalArgumentException("❌ Unknown sort option: " + sortOption);
                }
                break;

            default:
                throw new IllegalArgumentException("❌ Unknown Action: " + action);
        }
    }
}
