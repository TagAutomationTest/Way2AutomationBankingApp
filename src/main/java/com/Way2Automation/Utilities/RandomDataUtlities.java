package com.Way2Automation.Utilities;

import java.util.Random;

public class RandomDataUtlities {

    private RandomDataUtlities() {


    }

    public static String generateRandomPostalCode() {
        Random random = new Random();
        int postalCode = 10000 + random.nextInt(90000); // ensures 5 digits
        return String.valueOf(postalCode);
    }
}
