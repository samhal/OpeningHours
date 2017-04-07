package xyz.samhal.openinghours;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by sam on 4/7/17.
 */
public class OpeningHourInterval {
  private LocalTime openingTime;
  private LocalTime closingTime;

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

  public boolean isWithin(LocalTime time){
    return time.isAfter(this.openingTime) && time.isBefore(this.closingTime);
  }

  public boolean isClosed(){
    return this.openingTime.equals(LocalTime.MAX) && this.closingTime.equals(LocalTime.MIN);
  }

  public boolean isOpen(){
    return !isClosed();
  }

  public OpeningHourInterval alwaysOpen() {
    this.openingTime = LocalTime.MIN;
    this.closingTime = LocalTime.MAX;
    return this;
  }
  public OpeningHourInterval alwaysClosed(){
    this.openingTime = LocalTime.MAX;
    this.closingTime = LocalTime.MIN;
    return this;
  }
}
