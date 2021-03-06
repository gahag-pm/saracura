package br.ufmg.dcc.pm.saracura.clinic;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 * An scheduled appointment.
 * This class is immutable.
 */
public class Appointment<Operator, Cooperator> {
  /**
   * The associated operator.
   */
  public final Operator operator;
  /**
   * The associated cooperator, possibly null.
   */
  public final Cooperator cooperator;
  /**
   * The associated patient.
   */
  public final Patient patient;
  /**
   * The time of the appointment.
   */
  public final LocalDateTime time;
  /**
   * The description.
   */
  public final String description;



  /**
   * Create an mocked appointment object.
   * @param time the time of the appointment, mustn't be null
   */
  protected Appointment(LocalDateTime time) {
    if (time == null)
      throw new IllegalArgumentException("time mustn't be null");

    this.operator = null;
    this.cooperator = null;
    this.patient = null;
    this.time = time;
    this.description = null;
  }


  /**
   * Create an appointment object.
   * @param operator    the related operator, mustn't be null
   * @param cooperator  the related cooperator, possibly null
   * @param patient     the related patient, mustn't be null
   * @param time        the time of the appointment, mustn't be null
   * @param description the description of the appointment, mustn't be null
   */
  public Appointment(
    Operator operator,
    Cooperator cooperator,
    Patient patient,
    LocalDateTime time,
    String description
  ) {
    if (operator == null)
      throw new IllegalArgumentException("operator mustn't be null");

    if (patient == null)
      throw new IllegalArgumentException("patient mustn't be null");

    if (time == null)
      throw new IllegalArgumentException("time mustn't be null");

    if (description == null)
      throw new IllegalArgumentException("description mustn't be null");


    this.operator = operator;
    this.cooperator = cooperator;
    this.patient = patient;
    this.time = time;
    this.description = description;
  }



  /**
   * Create an mocked appointment object.
   * @param time the time of the appointment, mustn't be null
   */
  public static <Operator, Cooperator> Appointment<Operator, Cooperator> mock(
    LocalDateTime time
  ) {
    return new Appointment<Operator, Cooperator>(time);
  }



  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof Agenda))
      return false;

    final var obj = (Appointment<?, ?>) o;

    return this.operator.equals(obj.operator)
        && this.cooperator.equals(obj.cooperator)
        && this.patient.equals(obj.patient)
        && this.time.equals(obj.time)
        && this.description.equals(obj.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      this.operator,
      this.cooperator,
      this.patient,
      this.time,
      this.description
    );
  }
}
