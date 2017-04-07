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
    private Map<LocalDate, OpeningHourInterval> devatingOpeningHours = new HashMap<>();
    public boolean isOpen() {
        return isOpen(LocalDateTime.now());
    }

    public boolean isOpen(LocalTime time) {
        return isOpen(LocalDateTime.now().with(time));
    }

    public boolean isOpen(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        boolean open = false;
        if(this.devatingOpeningHours.containsKey(date)){
            open = this.devatingOpeningHours.get(date).isWithin(dateTime.toLocalTime());
        } else {
            DayOfWeek day = date.getDayOfWeek();
            if(this.openingHours.containsKey(day)){
                open = this.openingHours.get(day).isWithin(dateTime.toLocalTime());
            }
        }
        return open;
    }

    public boolean isOpen(LocalDate date) {
        boolean open = false;
        if(this.devatingOpeningHours.containsKey(date)) {
            open = this.devatingOpeningHours.get(date).isOpen();
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
        for (DayOfWeek day : DayOfWeek.values()){
            addOpeningHour(day, new OpeningHourInterval().alwaysOpen());
        }
        return this;
    }

    public OpeningHours addOpeningHour(DayOfWeek day, LocalTime from, LocalTime to){
        addOpeningHour(
            day,
            new OpeningHourInterval()
                .setOpeningTime(from)
                .setClosingTime(to)
        );
        return this;
    }

    public OpeningHours addOpeningHour(DayOfWeek day, OpeningHourInterval openingHourInterval){
        this.openingHours.put(day, openingHourInterval);
        return this;
    }

}
