package com.uos25.uos25.etc.lotto.service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomNumberGenerator {

    public static String generateNumbers() {
        Random random = new Random();
        Set<Integer> uniqueDigits = new HashSet<>();

        while (uniqueDigits.size() < 6) {
            int digit = random.nextInt(10); // Generates a random digit between 0 and 9
            uniqueDigits.add(digit);
        }

        StringBuilder sb = new StringBuilder();
        for (int digit : uniqueDigits) {
            sb.append(digit);
        }

        return sb.toString();

    }

}
