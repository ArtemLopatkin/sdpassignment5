package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;

final class PricingSupport {
    private PricingSupport() {
    }

    static BigDecimal requirePositiveAmount(BigDecimal amount, String name) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException(name + " must be positive.");
        }
        try {
            return amount.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException(name + " must have at most two decimal places.", exception);
        }
    }

    static long startedHours(long minutes) {
        return minutes / 60 + (minutes % 60 == 0 ? 0 : 1);
    }
}
