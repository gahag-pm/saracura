package br.ufmg.dcc.pm.saracura.clinic;

import java.time.LocalDateTime;


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
}
