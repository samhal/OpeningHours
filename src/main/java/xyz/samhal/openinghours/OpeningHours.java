package xyz.samhal.openinghours;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam on 4/7/17.
 */
public class OpeningHours {
    private Map<DayOfWeek, OpeningHourInterval> openingHours = new HashMap<>();
    private Map<LocalDate, OpeningHourInterval> deviatingOpeningHours = new HashMap<>();
    public boolean isOpen() {
        return isOpen(LocalDateTime.now());
    }

    public boolean isOpen(LocalTime time) {
        return isOpen(LocalDateTime.now().with(time));
    }

    public boolean isOpen(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        boolean open = false;
        if(this.deviatingOpeningHours.containsKey(date)){
            open = this.deviatingOpeningHours.get(date).isWithin(dateTime.toLocalTime());
        } else {
            DayOfWeek day = date.getDayOfWeek();
            if(this.openingHours.containsKey(day)){
                open = this.openingHours.get(day).isWithin(dateTime.toLocalTime());
            }
        }
        return open;
    }

    public boolean isOpen(LocalDate date) {
        boolean open;
        if(this.deviatingOpeningHours.containsKey(date)) {
            open = this.deviatingOpeningHours.get(date).isOpen();
        } else {
            open = isOpen(date.getDayOfWeek());
        }
        return open;
    }

    public boolean isOpen(DayOfWeek day) {
        return this.openingHours.containsKey(day);
    }

    public boolean isOpen(DayOfWeek day, LocalTime time) {
        boolean open = false;
        if(this.openingHours.containsKey(day)){
            open = openingHours.get(day).isWithin(time);
        }
        return open;
    }
    public OpeningHours alwaysOpen(){
        return always(new OpeningHourInterval().alwaysOpen());
    }

    public OpeningHours alwaysOpen(LocalTime from, LocalTime to){
        return always(new OpeningHourInterval(from, to));
    }

    private OpeningHours always(OpeningHourInterval interval){
        for (DayOfWeek day : DayOfWeek.values()) {
            open(day, interval);
        }
        return this;
    }

    public OpeningHours open(DayOfWeek day, LocalTime from, LocalTime to){
        return open(day, new OpeningHourInterval(from, to));
    }

    public OpeningHours open(DayOfWeek day, OpeningHourInterval openingHourInterval){
        this.openingHours.put(day, openingHourInterval);
        return this;
    }

    public OpeningHours open(LocalDate date, LocalTime from, LocalTime to) {
        return open(date, new OpeningHourInterval(from, to));
    }

    public OpeningHours open(LocalDate date, OpeningHourInterval interval) {
        this.deviatingOpeningHours.put(date, interval);
        return this;
    }

    public OpeningHours close(LocalDate date) {
        return open(date, new OpeningHourInterval().alwaysClosed());
    }

    public OpeningHours close(DayOfWeek day) {
        if(this.openingHours.containsKey(day)){
            this.openingHours.remove(day);
        }
        return this;
    }

}
