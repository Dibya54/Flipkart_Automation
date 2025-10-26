package com.flipkart.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterValidator {

    public static boolean isSortedLowToHigh(List<Integer> prices) {
        List<Integer> expected = new ArrayList<>(prices);
        Collections.sort(expected);
        return prices.equals(expected);
    }

    public static boolean isSortedHighToLow(List<Integer> prices) {
        List<Integer> expected = new ArrayList<>(prices);
        expected.sort(Collections.reverseOrder());
        return prices.equals(expected);
    }

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
