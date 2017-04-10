package xyz.samhal.openinghours;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * A java-based representation of opening hours using
 * the Time API introduced in Java 8.
 * @author samhal
 */
public class OpeningHours {

  private Map<DayOfWeek, OpeningHourInterval> openingHours = new HashMap<>();
  private Map<LocalDate, OpeningHourInterval> deviatingOpeningHours = new HashMap<>();

  /**
   * Checks whether the OpeningHours are open during the current time and date.
   * @return
   */
  public boolean isOpen() {
    return isOpen(LocalDateTime.now());
  }

  /**
   * Checks whether the OpeningHours are open during the current date and the specified time.
   * @param time the time for which it should be checked whether the OpeningHours are set to open
   * @return
   */
  public boolean isOpen(LocalTime time) {
    return isOpen(LocalDateTime.now().with(time));
  }

  /**
   * Checks whether the OpeningHours are open during the specified time and date.
   * @param dateTime
   * @return
   */
  public boolean isOpen(LocalDateTime dateTime) {
    LocalDate date = dateTime.toLocalDate();
    boolean open = false;
    if (this.deviatingOpeningHours.containsKey(date)) {
      open = this.deviatingOpeningHours.get(date).isWithin(dateTime.toLocalTime());
    } else {
      DayOfWeek day = date.getDayOfWeek();
      if (this.openingHours.containsKey(day)) {
        open = this.openingHours.get(day).isWithin(dateTime.toLocalTime());
      }
    }
    return open;
  }

  /**
   * Checks whether the OpeningHours are open during the given date.
   * @param date
   * @return
   */
  public boolean isOpen(LocalDate date) {
    boolean open;
    if (this.deviatingOpeningHours.containsKey(date)) {
      open = this.deviatingOpeningHours.get(date).isOpen();
    } else {
      open = isOpen(date.getDayOfWeek());
    }
    return open;
  }

  /**
   * Checks whether the OpeningHours are open during the specified day of the week.
   * @param day
   * @return
   */
  public boolean isOpen(DayOfWeek day) {
    return this.openingHours.containsKey(day);
  }

  /**
   * Checks whether the OpeningHours are open during the specified day of the week and time.
   * @param day
   * @param time
   * @return
   */
  public boolean isOpen(DayOfWeek day, LocalTime time) {
    boolean open = false;
    if (this.openingHours.containsKey(day)) {
      open = openingHours.get(day).isWithin(time);
    }
    return open;
  }

  /**
   * Sets the OpeningHours to be open during all times if no deviating dates are set before.
   * @return
   */
  public OpeningHours alwaysOpen() {
    return always(new OpeningHourInterval().alwaysOpen());
  }

  /**
   * Sets the OpeningHours to be open on all days during the specified times.
   * @param from
   * @param to
   * @return
   */
  public OpeningHours alwaysOpen(LocalTime from, LocalTime to) {
    return always(new OpeningHourInterval(from, to));
  }

  private OpeningHours always(OpeningHourInterval interval) {
    for (DayOfWeek day : DayOfWeek.values()) {
      open(day, interval);
    }
    return this;
  }

  /**
   * Sets a day of the week for which the OpeningHours are open within a given time-interval.
   * @param day a day of the week for which the OpeningHours should be open
   * @param from the time from which it opens
   * @param to the time which it closes
   * @return the modified OpeningHours
   */
  public OpeningHours open(DayOfWeek day, LocalTime from, LocalTime to) {
    return open(day, new OpeningHourInterval(from, to));
  }

  private OpeningHours open(DayOfWeek day, OpeningHourInterval openingHourInterval) {
    this.openingHours.put(day, openingHourInterval);
    return this;
  }

  /**
   * Sets a specific date for which the OpeningHours
   * are open within a given time-interval.
   * @param date a date for which the OpeningHours should be open
   * @param from the time from which it opens
   * @param to the time which it closes
   * @return the modified OpeningHours
   */
  public OpeningHours open(LocalDate date, LocalTime from, LocalTime to) {
    return open(date, new OpeningHourInterval(from, to));
  }

  private OpeningHours open(LocalDate date, OpeningHourInterval interval) {
    this.deviatingOpeningHours.put(date, interval);
    return this;
  }

  /**
   * Sets a specific date for which the OpeningHours are not open.
   * @param date a date where the OpeningHours should be closed
   * @return the modified OpeningHours
   */
  public OpeningHours close(LocalDate date) {
    return open(date, new OpeningHourInterval().alwaysClosed());
  }

  /**
   * Sets a day of the week for which the OpeningHours are not open
   * @param day a day of the week where the OpeningHours should be closed
   * @return the modified OpeningHours
   */
  public OpeningHours close(DayOfWeek day) {
    if (this.openingHours.containsKey(day)) {
      this.openingHours.remove(day);
    }
    return this;
  }

}
