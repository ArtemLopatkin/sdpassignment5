package com.company;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSession {
    private final LocalDateTime entryTime;
    private final LocalDateTime exitTime;

    public ParkingSession(LocalDateTime entryTime, LocalDateTime exitTime) {
        this.entryTime = Objects.requireNonNull(entryTime, "Entry time is required.");
        this.exitTime = Objects.requireNonNull(exitTime, "Exit time is required.");
        if (exitTime.isBefore(entryTime)) {
            throw new IllegalArgumentException("Exit time cannot be before entry time.");
        }
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public long getBillableMinutes() {
        Duration duration = Duration.between(entryTime, exitTime);
        long minutes = duration.toMinutes();
        return duration.minusMinutes(minutes).isZero() ? minutes : minutes + 1;
    }
}
