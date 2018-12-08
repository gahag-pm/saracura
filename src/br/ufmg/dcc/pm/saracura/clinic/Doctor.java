package br.ufmg.dcc.pm.saracura.clinic;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Set;


/**
 * A doctor, containing identification and agenda.
 */
public class Doctor implements Schedulable<Doctor, Equipment> {
  /**
   * The doctor's agenda.
   */
  protected final Agenda<Doctor, Equipment> agenda;


  /**
   * The doctor's CRM.
   */
  public final String crm;
  /**
   * The doctor's name.
   */
  public final String name;
  /**
   * The doctor's specialties.
   */
  public final Set<Specialty> specialties;



  /**
   * Create a doctor.
   * @param crm                 the doctor's CRM, mustn't be null
   * @param name                the doctor's name, mustn't be null
   * @param specialties         the doctor's specialties, mustn't be null or empty
   * @param appointmentDuration the duration of each appointment in the agenda,
   *                            mustn't be null
   * @param startTime           the beginning of the work day, mustn't be null
   * @param dayDuration         the duration of the work day, mustn't be null
   * @param workDays            the days of work in the week, mustn't be null
   */
  public Doctor(
    String crm,
    String name,
    Set<Specialty> specialties,
    Duration appointmentDuration,
    LocalTime startTime,
    Duration dayDuration,
    Set<DayOfWeek> workDays
  ) {
    if (crm == null)
      throw new IllegalArgumentException("crm mustn't be null");

    if (name == null)
      throw new IllegalArgumentException("name mustn't be null");

    if (specialties == null)
      throw new IllegalArgumentException("specialties mustn't be null");

    if (specialties.isEmpty())
      throw new IllegalArgumentException("specialties mustn't be null");


    this.crm = crm;
    this.name = name;
    this.specialties = Collections.unmodifiableSet(specialties);
    this.agenda = new Agenda<Doctor, Equipment>(
      this,
      appointmentDuration,
      startTime,
      dayDuration,
      workDays
    );
  }



  public Agenda<Doctor, Equipment> getAgenda() {
    return this.agenda;
  }
}
