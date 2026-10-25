package com.company;

import java.math.BigDecimal;
import java.util.Objects;

public class ProgressivePricingStrategy implements ParkingPricingStrategy {
    private static final int MINUTES_PER_DAY = 24 * 60;

    private final BigDecimal firstHourRate;
    private final BigDecimal additionalHourRate;
    private final BigDecimal dailyCap;

    public ProgressivePricingStrategy(BigDecimal firstHourRate, BigDecimal additionalHourRate, BigDecimal dailyCap) {
        this.firstHourRate = PricingSupport.requirePositiveAmount(firstHourRate, "First hour rate");
        this.additionalHourRate = PricingSupport.requirePositiveAmount(additionalHourRate, "Additional hour rate");
        this.dailyCap = PricingSupport.requirePositiveAmount(dailyCap, "Daily cap");
        if (this.dailyCap.compareTo(this.firstHourRate) < 0) {
            throw new IllegalArgumentException("Daily cap cannot be lower than the first hour rate.");
        }
    }

    @Override
    public BigDecimal calculateFee(ParkingSession session) {
        Objects.requireNonNull(session, "Parking session is required.");
        long minutes = session.getBillableMinutes();
        long fullDays = minutes / MINUTES_PER_DAY;
        long remainingMinutes = minutes % MINUTES_PER_DAY;

        BigDecimal fullDaysFee = calculateDailyFee(MINUTES_PER_DAY).multiply(BigDecimal.valueOf(fullDays));
        return fullDaysFee.add(calculateDailyFee(remainingMinutes));
    }

    private BigDecimal calculateDailyFee(long minutes) {
        if (minutes == 0) {
            return new BigDecimal("0.00");
        }
        long hours = PricingSupport.startedHours(minutes);
        BigDecimal additionalFee = additionalHourRate.multiply(BigDecimal.valueOf(hours - 1));
        return firstHourRate.add(additionalFee).min(dailyCap);
    }
}
