package com.company;

import java.math.BigDecimal;
import java.util.Objects;

public final class ParkingCalculator {
    private ParkingPricingStrategy pricingStrategy;

    public ParkingCalculator(ParkingPricingStrategy pricingStrategy) {
        setPricingStrategy(pricingStrategy);
    }

    public void setPricingStrategy(ParkingPricingStrategy pricingStrategy) {
        this.pricingStrategy = Objects.requireNonNull(pricingStrategy, "Pricing strategy is required.");
    }

    public BigDecimal calculateFee(ParkingSession session) {
        Objects.requireNonNull(session, "Parking session is required.");
        return pricingStrategy.calculateFee(session);
    }
}
