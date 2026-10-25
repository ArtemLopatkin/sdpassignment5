package com.company;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LocalDateTime entryTime = LocalDateTime.of(2026, 10, 25, 9, 0);
        ParkingSession session = new ParkingSession(entryTime, entryTime.plusMinutes(90));

        ParkingPricingStrategy hourly = new HourlyPricingStrategy(new BigDecimal("400.00"));
        ParkingPricingStrategy gracePeriod = new GracePeriodPricingStrategy(30, new BigDecimal("400.00"));
        ParkingPricingStrategy progressive = new ProgressivePricingStrategy(
                new BigDecimal("500.00"), new BigDecimal("200.00"), new BigDecimal("2000.00"));

        ParkingCalculator calculator = new ParkingCalculator(hourly);

        System.out.println("Parking entry: " + session.getEntryTime());
        System.out.println("Parking exit: " + session.getExitTime());
        System.out.println("Parking duration: " + session.getBillableMinutes() + " minutes");
        System.out.println();

        printFee("Hourly pricing", calculator, session);
        calculator.setPricingStrategy(gracePeriod);
        printFee("Pricing with 30 free minutes", calculator, session);
        calculator.setPricingStrategy(progressive);
        printFee("Progressive pricing with a daily cap", calculator, session);

        System.out.println();
        ParkingSession longStay = new ParkingSession(entryTime, entryTime.plusHours(25));
        printFee("Progressive pricing for 25 hours", calculator, longStay);

        calculator.setPricingStrategy(gracePeriod);
        ParkingSession shortStay = new ParkingSession(entryTime, entryTime.plusMinutes(30));
        printFee("Pricing with a grace period for 30 minutes", calculator, shortStay);
    }

    private static void printFee(String label, ParkingCalculator calculator, ParkingSession session) {
        System.out.println(label + ": " + calculator.calculateFee(session) + " KZT");
    }
}
