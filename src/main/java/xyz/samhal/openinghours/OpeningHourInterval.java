package xyz.samhal.openinghours;

import java.time.LocalTime;

public class OpeningHourInterval {

  private LocalTime openingTime;
  private LocalTime closingTime;

  public OpeningHourInterval(LocalTime from, LocalTime to) {
    this.openingTime = from;
    this.closingTime = to;
  }

  public OpeningHourInterval() {

  }

  public LocalTime getOpeningTime() {
    return this.openingTime;
  }

  public OpeningHourInterval setOpeningTime(LocalTime openingTime) {
    this.openingTime = openingTime;
    return this;
  }

  public LocalTime getClosingTime() {
    return this.closingTime;
  }

  public OpeningHourInterval setClosingTime(LocalTime closingTime) {
    this.closingTime = closingTime;
    return this;
  }

  public boolean isWithin(LocalTime time) {
    return time.isAfter(this.openingTime) && time.isBefore(this.closingTime);
  }

  public boolean isClosed() {
    return this.openingTime.equals(LocalTime.MAX) && this.closingTime.equals(LocalTime.MIN);
  }

  public boolean isOpen() {
    return !isClosed();
  }

  public OpeningHourInterval alwaysOpen() {
    this.openingTime = LocalTime.MIN;
    this.closingTime = LocalTime.MAX;
    return this;
  }

  public OpeningHourInterval alwaysClosed() {
    this.openingTime = LocalTime.MAX;
    this.closingTime = LocalTime.MIN;
    return this;
  }
}
