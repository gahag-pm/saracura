package br.ufmg.dcc.pm.saracura.util.time;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;


/**
 * Utilities for the LocalDate type.
 * This class is static.
 */
public class LocalTimeUtil {
  private LocalTimeUtil() { }



  public static boolean checkOverflow(LocalTime time, Duration duration) {
    final long minutes = ChronoUnit.MINUTES.between(LocalTime.MIN, time);
    final long maxMinutes = LocalTime.MAX.get(ChronoField.MINUTE_OF_DAY);

    return minutes + duration.toMinutes() > maxMinutes;
  }
}
