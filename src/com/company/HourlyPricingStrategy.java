package com.company;

import java.math.BigDecimal;
import java.util.Objects;

public class HourlyPricingStrategy implements ParkingPricingStrategy {
    private final BigDecimal hourlyRate;

    public HourlyPricingStrategy(BigDecimal hourlyRate) {
        this.hourlyRate = PricingSupport.requirePositiveAmount(hourlyRate, "Hourly rate");
    }

    @Override
    public BigDecimal calculateFee(ParkingSession session) {
        Objects.requireNonNull(session, "Parking session is required.");
        long hours = PricingSupport.startedHours(session.getBillableMinutes());
        return hourlyRate.multiply(BigDecimal.valueOf(hours));
    }
}
