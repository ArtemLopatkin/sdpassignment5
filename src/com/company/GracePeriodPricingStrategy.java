package com.company;

import java.math.BigDecimal;
import java.util.Objects;

public class GracePeriodPricingStrategy implements ParkingPricingStrategy {
    private final int freeMinutes;
    private final BigDecimal hourlyRate;

    public GracePeriodPricingStrategy(int freeMinutes, BigDecimal hourlyRate) {
        if (freeMinutes < 0) {
            throw new IllegalArgumentException("Free minutes cannot be negative.");
        }
        this.freeMinutes = freeMinutes;
        this.hourlyRate = PricingSupport.requirePositiveAmount(hourlyRate, "Hourly rate");
    }

    @Override
    public BigDecimal calculateFee(ParkingSession session) {
        Objects.requireNonNull(session, "Parking session is required.");
        long paidMinutes = Math.max(0, session.getBillableMinutes() - freeMinutes);
        long hours = PricingSupport.startedHours(paidMinutes);
        return hourlyRate.multiply(BigDecimal.valueOf(hours));
    }
}
