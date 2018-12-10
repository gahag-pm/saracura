package br.ufmg.dcc.pm.saracura.clinic;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

import br.ufmg.dcc.pm.saracura.util.time.LocalTimeUtil;


/**
 * The agenda for an operator and a cooperator of the given types.
 * The operator is the owner of the agenda.
 * The cooperator is possibly associated in the appointments, and has its own agenda.
 */
public class Agenda<
  Operator extends Schedulable<Operator, Cooperator>,
  Cooperator extends Schedulable<Cooperator, Operator>
> implements NavigableSet<Appointment<Operator, Cooperator>> {
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
   *                            mustn't be null nor zero
   * @param startTime           the beginning of the work day, mustn't be null
   * @param dayDuration         the duration of the work day, mustn't be null nor zero
   * @param workDays            the days of work in the week, mustn't be null nor empty
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

    if (workDays.isEmpty())
      throw new IllegalArgumentException("workDays mustn't be empty");

    if (appointmentDuration.compareTo(dayDuration) > 0)
      throw new IllegalArgumentException("appointmentDuration bigger than dayDuration");

    if (LocalTimeUtil.checkOverflow(startTime, dayDuration))
      throw new IllegalArgumentException(
        "the workday period (startTime + dayDuration) exceeds the day"
      );

    if (dayDuration == Duration.ZERO)
      throw new IllegalArgumentException("dayDuration must'nt be zero");

    if (appointmentDuration == Duration.ZERO)
      throw new IllegalArgumentException("appointmentDuration must'nt be zero");


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



  // Interfaces --------------------------------------------------------------------------


  // NavigableSet:
  public Appointment<Operator, Cooperator> ceiling(Appointment<Operator, Cooperator> e) {
    return this.agenda.ceiling(e);
  }

  public Appointment<Operator, Cooperator> floor(Appointment<Operator, Cooperator> e) {
    return this.agenda.floor(e);
  }

  public Appointment<Operator, Cooperator> higher(Appointment<Operator, Cooperator> e) {
    return this.agenda.higher(e);
  }

  public Appointment<Operator, Cooperator> lower(Appointment<Operator, Cooperator> e) {
    return this.agenda.lower(e);
  }

  public Appointment<Operator, Cooperator> pollFirst() {
    return this.agenda.pollFirst();
  }

  public Appointment<Operator, Cooperator> pollLast() {
    return this.agenda.pollLast();
  }

  public Iterator<Appointment<Operator, Cooperator>> descendingIterator() {
    return Collections.unmodifiableNavigableSet(this.agenda).descendingIterator();
  }

  public NavigableSet<Appointment<Operator, Cooperator>> subSet(
    Appointment<Operator, Cooperator> fromElement,
    boolean fromInclusive,
    Appointment<Operator, Cooperator> toElement,
    boolean toInclusive
  ) {
    return Collections.unmodifiableNavigableSet(this.agenda).subSet(
      fromElement,
      fromInclusive,
      toElement,
      toInclusive
    );
  }

  public NavigableSet<Appointment<Operator, Cooperator>> descendingSet() {
    return Collections.unmodifiableNavigableSet(this.agenda).descendingSet();
  }

  public NavigableSet<Appointment<Operator, Cooperator>> headSet(
    Appointment<Operator, Cooperator> toElement,
    boolean inclusive
  ) {
    return Collections.unmodifiableNavigableSet(this.agenda).headSet(toElement, inclusive);
  }

  public NavigableSet<Appointment<Operator, Cooperator>> tailSet(
    Appointment<Operator, Cooperator> fromElement,
    boolean inclusive
  ) {
    return Collections.unmodifiableNavigableSet(this.agenda).tailSet(
      fromElement,
      inclusive
    );
  }


  // SortedSet:
  public Comparator<? super Appointment<Operator, Cooperator>> comparator() {
    return this.agenda.comparator();
  }

  public Appointment<Operator, Cooperator> first() {
    return this.agenda.first();
  }

  public Appointment<Operator, Cooperator> last() {
    return this.agenda.last();
  }

  public SortedSet<Appointment<Operator, Cooperator>> subSet(
    Appointment<Operator, Cooperator> fromElement,
    Appointment<Operator, Cooperator> toElement
  ) {
    return Collections.unmodifiableSortedSet(this.agenda).subSet(fromElement, toElement);
  }

  public SortedSet<Appointment<Operator, Cooperator>> headSet(
    Appointment<Operator, Cooperator> toElement
  ) {
    return Collections.unmodifiableSortedSet(this.agenda).headSet(toElement);
  }

  public SortedSet<Appointment<Operator, Cooperator>> tailSet(
    Appointment<Operator, Cooperator> fromElement
  ) {
    return Collections.unmodifiableSortedSet(this.agenda).tailSet(fromElement);
  }


  // Collection:
  public boolean add(Appointment<Operator, Cooperator> e) {
    throw new UnsupportedOperationException();
  }

  public boolean addAll(Collection<? extends Appointment<Operator, Cooperator>> c) {
    throw new UnsupportedOperationException();
  }

  public void clear() {
    throw new UnsupportedOperationException();
  }

  public boolean remove(Object o) {
    throw new UnsupportedOperationException();
  }

  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  public boolean removeIf(
    Predicate<? super Appointment<Operator, Cooperator>> filter
  ) {
    throw new UnsupportedOperationException();
  }

  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  public boolean contains(Object o) {
    return this.agenda.contains(o);
  }

  public boolean containsAll(Collection<?> c) {
    return this.agenda.containsAll(c);
  }

  public boolean equals(Object o) {
    if (o == null || !(o instanceof Agenda))
      return false;

    final var obj = (Agenda<?, ?>) o;

    return this.agenda.equals(obj.agenda)
        && this.operator.equals(obj.operator)
        && this.appointmentDuration.equals(obj.appointmentDuration)
        && this.startTime.equals(obj.startTime)
        && this.dayDuration.equals(obj.dayDuration)
        && this.workDays.equals(obj.workDays);
  }

  public int hashCode() {
    return Objects.hash(
      this.agenda,
      this.operator,
      this.appointmentDuration,
      this.startTime,
      this.dayDuration,
      this.workDays
    );
  }

  public boolean isEmpty() {
    return this.agenda.isEmpty();
  }

  public int size() {
    return this.agenda.size();
  }

  public Stream<Appointment<Operator, Cooperator>> stream() {
    return Collections.unmodifiableCollection(this.agenda).stream();
  }

  public Stream<Appointment<Operator, Cooperator>> parallelStream() {
    return Collections.unmodifiableCollection(this.agenda).parallelStream();
  }

  public Object[] toArray() {
    return this.agenda.toArray();
  }

  public <T> T[] toArray(T[] a) {
    return this.agenda.toArray(a);
  }


  // Iterable:
  public Iterator<Appointment<Operator, Cooperator>> iterator() {
    return Collections.unmodifiableCollection(this.agenda).iterator();
  }

  public Spliterator<Appointment<Operator, Cooperator>> spliterator() {
    return Collections.unmodifiableCollection(this.agenda).spliterator();
  }
}
