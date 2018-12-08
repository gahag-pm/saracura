package br.ufmg.dcc.pm.saracura.clinic;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;


/**
 * The agenda for an operator and a cooperator of the given types.
 * The operator is the owner of the agenda.
 * The cooperator is possibly associated in the appointments, and has its own agenda.
 */
public class Agenda<
  Operator extends Schedulable<Operator, Cooperator>,
  Cooperator extends Schedulable<Cooperator, Operator>
> {
  /**
   * The set of scheduled appointments.
   */
  protected final NavigableSet<Appointment<Operator, Cooperator>> agenda;

  /**
   * The associated operator.
   */
  public final Operator operator;
  /**
   * The duration of each appointment in the agenda.
   */
  public final Duration appointmentDuration;
  /**
   * The beginning time of the work day.
   */
  public final LocalTime startTime;
  /**
   * The duration of the work day.
   */
  public final Duration dayDuration;
  /**
   * The days of work in the week.
   */
  public final Set<DayOfWeek> workDays;



  /**
   * Create an agenda object for the given operator.
   * @param operator            the associated operator, mustn't be null
   * @param appointmentDuration the duration of each appointment in the agenda,
   *                            mustn't be null
   * @param startTime           the beginning of the work day, mustn't be null
   * @param dayDuration         the duration of the work day, mustn't be null
   * @param workDays            the days of work in the week, mustn't be null
   */
  public Agenda(
    Operator operator,
    Duration appointmentDuration,
    LocalTime startTime,
    Duration dayDuration,
    Set<DayOfWeek> workDays
  ) {
    if (operator == null)
      throw new IllegalArgumentException("operator mustn't be null");

    if (appointmentDuration == null)
      throw new IllegalArgumentException("appointmentDuration mustn't be null");

    if (startTime == null)
      throw new IllegalArgumentException("startTime mustn't be null");

    if (dayDuration == null)
      throw new IllegalArgumentException("dayDuration mustn't be null");

    if (workDays == null)
      throw new IllegalArgumentException("workDays mustn't be null");

    if (appointmentDuration.compareTo(dayDuration) > 0)
      throw new IllegalArgumentException("appointmentDuration bigger than dayDuration");

    final long startMinutes = ChronoUnit.MINUTES.between(LocalTime.MIN, startTime);
    final long maxMinutes = LocalTime.MAX.get(ChronoField.MINUTE_OF_DAY);
    if (startMinutes + dayDuration.toSeconds() < maxMinutes)
      throw new IllegalArgumentException("TIME DURATION EXCEPTION");


    this.operator = operator;
    this.appointmentDuration = appointmentDuration;
    this.startTime = startTime;
    this.dayDuration = dayDuration;
    this.workDays = Collections.unmodifiableSet(new HashSet<DayOfWeek>(workDays));
    this.agenda = new TreeSet<Appointment<Operator, Cooperator>>(
      Comparator.comparing(a -> a.time)
    );
  }



  /**
   * Schedule an appointment.
   * @param cooperator    the related cooperator, possibly null
   * @param crossSchedule wether to schedule the cooperator's agenda
   * @param dateTime      the LocalDateTime of the appointment, mustn't be null
   * @param patient       the associated patient, mustn't be null
   * @param description   the description of the appointment, may be empty, mustn't be null
   */
  protected void schedule(
    Cooperator cooperator,
    boolean crossSchedule,
    LocalDateTime dateTime,
    Patient patient,
    String description
  ) {
    if (dateTime == null)
      throw new IllegalArgumentException("dateTime mustn't be null");

    if (patient == null)
      throw new IllegalArgumentException("patient mustn't be null");

    if (description == null)
      throw new IllegalArgumentException("description mustn't be null");


    // Check if dateTime is within the work days:
    if (!this.workDays.contains(dateTime.getDayOfWeek()))
      throw new IllegalArgumentException("dateTime out of agenda");

    final LocalTime time = dateTime.toLocalTime();

    // Check if time is within the work hours:
    if (this.startTime.isAfter(time) || this.startTime.plus(this.dayDuration).isBefore(time))
      throw new IllegalArgumentException("dateTime out of agenda");

    final var appointmentMinutes = this.appointmentDuration.toMinutes();

    // Check if time is a multiple of the appointment duration:
    if (ChronoUnit.MINUTES.between(this.startTime, time) % appointmentMinutes != 0)
      throw new IllegalArgumentException("dateTime out of agenda");



    final var appointment = new Appointment<Operator, Cooperator>(
      operator,
      cooperator,
      patient,
      dateTime,
      description
    );

    if (!this.agenda.add(appointment))
      throw new IllegalArgumentException("dateTime already scheduled");

    if (cooperator != null && crossSchedule)
      try {
        cooperator.getAgenda().schedule(operator, false, dateTime, patient, description);
      }
      catch (IllegalArgumentException e) {
        // Cooperator schedule unnavailable, rollback:
        this.agenda.remove(appointment);
        throw e;
      }
  }


  /**
   * Schedule an appointment.
   * @param dateTime    the LocalDateTime of the appointment, mustn't be null
   * @param patient     the associated patient, mustn't be null
   * @param description the description of the appointment, may be empty, mustn't be null
   */
  public void scheduleAppointment(
    LocalDateTime dateTime,
    Patient patient,
    String description
  ) {
    this.schedule(null, true, dateTime, patient, description);
  }

  /**
   * Schedule an appointment.
   * @param dateTime    the LocalDateTime of the appointment, mustn't be null
   * @param patient     the associated patient, mustn't be null
   * @param cooperator  the related cooperator, mustn't be null
   * @param description the description of the appointment, may be empty, mustn't be null
   */
  public void scheduleAppointment(
    LocalDateTime dateTime,
    Patient patient,
    Cooperator cooperator,
    String description
  ) {
    if (cooperator == null)
      throw new IllegalArgumentException("cooperator mustn't be null");

    this.schedule(cooperator, true, dateTime, patient, description);
  }


  /**
   * Get a read-only view of the appointments in the given time interval.
   * @param begin the beginning date of the interval
   * @param end the end date of the interval
   */
  public NavigableSet<Appointment<Operator, Cooperator>> frameView(
    LocalDate begin,
    LocalDate end
  ) {
    var _begin = Appointment.<Operator, Cooperator>mock(begin.atStartOfDay());
    var _end = Appointment.<Operator, Cooperator>mock(end.plusDays(1).atStartOfDay());

    return Collections.unmodifiableNavigableSet(
      this.agenda.subSet(_begin, true, _end, false)
    );
  }
}
