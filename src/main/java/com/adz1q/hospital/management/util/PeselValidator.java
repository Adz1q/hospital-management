package com.adz1q.hospital.management.util;

// Usage in Person commented because of UX reasons
public class PeselValidator {
    public static void isValid(String pesel) {
        if (pesel == null) {
            throw new NullPointerException("PESEL cannot be null.");
        }

        if (pesel.isBlank()) {
            throw new IllegalArgumentException("PESEL cannot be blank.");
        }

        if (!pesel.matches("\\d{11}")) {
            throw new IllegalArgumentException("PESEL must contain exactly 11 digits.");
        }

        int[] weights = { 1, 3, 7, 9, 1, 3, 7, 9, 1, 3 };
        int sum = 0;

        for (int i = 0; i < weights.length; i++) {
            sum += Character.getNumericValue(pesel.charAt(i)) * weights[i];
        }

        int checksumDigit = (10 - (sum % 10)) % 10;
        int lastDigit = Character.getNumericValue(pesel.charAt(10));

        if (checksumDigit != lastDigit) {
            throw new IllegalArgumentException("Invalid PESEL number.");
        }
    }
}
