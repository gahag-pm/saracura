package br.ufmg.dcc.pm.saracura.ui.controls.agenda;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Set;

import br.ufmg.dcc.pm.saracura.util.time.LocalTimeUtil;


/**
 * The class to represent a schedule for the agenda control.
 * This class is immutable.
 */
public class Schedule {
  public final NavigableMap<LocalDateTime, AgendaEvent> events;
  public final Set<DayOfWeek> workDays;
  public final LocalTime startTime;
  public final Duration dayDuration;
  public final Duration appointmentDuration;



  /**
   * Create a schedule.
   */
  public Schedule(
    NavigableMap<LocalDateTime, AgendaEvent> events,
    Set<DayOfWeek> workDays,
    LocalTime startTime,
    Duration dayDuration,
    Duration appointmentDuration
  ) {
    if (events == null)
      throw new IllegalArgumentException("events mustn't be null");

    if (workDays == null)
      throw new IllegalArgumentException("workDays mustn't be null");

    if (workDays.isEmpty())
      throw new IllegalArgumentException("workDays mustn't be empty");

    if (startTime == null)
      throw new IllegalArgumentException("startTime mustn't be null");

    if (dayDuration == null)
      throw new IllegalArgumentException("dayDuration mustn't be null");

    if (dayDuration == Duration.ZERO)
      throw new IllegalArgumentException("dayDuration must'nt be zero");

    if (LocalTimeUtil.checkOverflow(startTime, dayDuration))
      throw new IllegalArgumentException(
        "the workday period (startTime + dayDuration) exceeds the day"
      );

    if (appointmentDuration == null)
      throw new IllegalArgumentException("appointmentDuration mustn't be null");

    if (appointmentDuration.compareTo(dayDuration) > 0)
      throw new IllegalArgumentException("appointmentDuration bigger than dayDuration");

    if (appointmentDuration == Duration.ZERO)
      throw new IllegalArgumentException("appointmentDuration must'nt be zero");


    this.events = events;
    this.workDays = workDays;
    this.startTime = startTime;
    this.dayDuration = dayDuration;
    this.appointmentDuration = appointmentDuration;
  }



  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof Schedule))
      return false;

    final var obj = (Schedule) o;

    return this.events.equals(obj.events)
        && this.workDays.equals(obj.workDays)
        && this.startTime.equals(obj.startTime)
        && this.dayDuration.equals(obj.dayDuration)
        && this.appointmentDuration.equals(obj.appointmentDuration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      this.events,
      this.workDays,
      this.startTime,
      this.dayDuration,
      this.appointmentDuration
    );
  }
}
