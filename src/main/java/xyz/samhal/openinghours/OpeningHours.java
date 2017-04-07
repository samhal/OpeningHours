package xyz.samhal.openinghours;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by sam on 4/7/17.
 */
public class OpeningHours {
    public boolean isOpen() {
        return isOpen(LocalDateTime.now());
    }

    public boolean isOpen(LocalTime of) {
    }

    public boolean isOpen(LocalDateTime of) {
    }

    public boolean isOpen(LocalDate of) {
    }

    public boolean isOpen(DayOfWeek friday) {
    }

    public boolean isOpen(DayOfWeek friday, LocalTime of) {
    }
}
