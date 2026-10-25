package com.company;

import java.math.BigDecimal;

public interface ParkingPricingStrategy {
    BigDecimal calculateFee(ParkingSession session);
}
