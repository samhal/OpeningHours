package xyz.samhal.openinghours;

import org.junit.Test;

import java.time.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by sam on 4/7/17.
 */
public class OpeningHoursTest {
    @Test
    public void testIsOpen() {
        OpeningHours oh = new OpeningHours().alwaysOpen();
        assertTrue(oh.isOpen());
        assertTrue(oh.isOpen(LocalTime.of(22, 45)));
        assertTrue(oh.isOpen(LocalDateTime.of(2017, Month.APRIL, 7, 11, 00)));
        assertTrue(oh.isOpen(LocalDate.of(2017, Month.APRIL, 7)));
        assertTrue(oh.isOpen(DayOfWeek.FRIDAY));
        assertTrue(oh.isOpen(DayOfWeek.FRIDAY, LocalTime.of(11, 22)));
    }
    @Test
    public void testOpen(){
        OpeningHours oh = new OpeningHours();
        assertFalse(oh.isOpen(DayOfWeek.FRIDAY, LocalTime.of(11,15)));
        oh.open(DayOfWeek.FRIDAY, LocalTime.of(11,00), LocalTime.of(17,00));
        assertTrue(oh.isOpen(DayOfWeek.FRIDAY, LocalTime.of(11,15)));
    }
    @Test
    public void testDeviatingClose(){
        OpeningHours oh = new OpeningHours().alwaysOpen();
        oh.close(LocalDate.of(2017, Month.APRIL, 17));
        assertTrue(oh.isOpen(LocalDate.of(2017, Month.APRIL, 10)));
        assertFalse(oh.isOpen(LocalDate.of(2017, Month.APRIL, 17)));
    }
    @Test
    public void testDeviatingOpen() {
        OpeningHours oh = new OpeningHours()
            .alwaysOpen(
                LocalTime.of(8,00),
                LocalTime.of(17,00)
            )
            .open(
                LocalDate.of(2017, Month.APRIL, 17),
                LocalTime.of(8, 00),
                LocalTime.of(15,30)
                );
        assertTrue(oh.isOpen(LocalDateTime.of(
            LocalDate.of(2017, Month.APRIL, 10),
            LocalTime.of(16, 00)
        )));
        assertFalse(oh.isOpen(LocalDateTime.of(
            LocalDate.of(2017, Month.APRIL, 17),
            LocalTime.of(16, 00)
        )));
    }
}
