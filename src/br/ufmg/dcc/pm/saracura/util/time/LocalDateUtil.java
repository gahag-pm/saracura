package br.ufmg.dcc.pm.saracura.util.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;


/**
 * Utilities for the LocalDate type.
 * This class is static.
 */
public class LocalDateUtil {
  private LocalDateUtil() { }



  /**
   * Gets the sunday date in the same week.
   */
  public static LocalDate getStartOfWeek(LocalDate date) {
    return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
  }

  /**
   * Gets the saturday date in the same week.
   */
  public static LocalDate getEndOfWeek(LocalDate date) {
    return date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
  }

  /**
   * Gets the correspondent date in the same week.
   */
  public static LocalDate getDayOfWeek(LocalDate date, DayOfWeek dayOfWeek) {
    return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
               .with(TemporalAdjusters.nextOrSame(dayOfWeek));
  }

  /**
   * Gets the sunday of the next week.
   */
  public static LocalDate nextWeek(LocalDate date) {
    return date.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
  }

  /**
   * Gets the sunday of the previous week.
   */
  public static LocalDate prevWeek(LocalDate date) {
    return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
               .with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
  }
}
