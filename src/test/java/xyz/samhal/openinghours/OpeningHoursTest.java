package xyz.samhal.openinghours;

import org.junit.Test;

import java.time.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by sam on 4/7/17.
 */
public class OpeningHoursTest {
    @Test
    public testIsOpen(){
        OpeningHours oh = new OpeningHours();
        assertTrue(oh.isOpen());
        assertTrue(oh.isOpen(LocalTime.of(22,45)));
        assertTrue(oh.isOpen(LocalDateTime.of(2017, Month.APRIL, 7, 11, 00)));
        assertTrue(oh.isOpen(LocalDate.of(2017,Month.APRIL, 7)));
        assertTrue(oh.isOpen(DayOfWeek.FRIDAY));
        assertTrue(oh.isOpen(DayOfWeek.FRIDAY, LocalTime.of(11,22)));
    }
}
