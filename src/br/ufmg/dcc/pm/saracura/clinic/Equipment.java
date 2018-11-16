package br.ufmg.dcc.pm.saracura.clinic;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;


/**
 * An equipment, containing the exam type and agenda.
 */
public class Equipment implements Schedulable<Equipment, Doctor> {
  protected final Agenda<Equipment, Doctor> agenda;

  public final Exam exam;



  /**
   * Create a equipment.
   * @param exam                the equipment's exam type, mustn't be null
   * @param appointmentDuration the duration of each appointment in the agenda,
   *                            mustn't be null
   * @param starTime            the beggining of the work day, mustn't be null
   * @param dayDuration         the duration of the work day, mustn't be null
   * @param workDays            the days of work in the week, mustn't be null
   */
  public Equipment(
    Exam exam,
    Duration appointmentDuration,
    LocalTime startTime,
    Duration dayDuration,
    Set<DayOfWeek> workDays) {
    this.exam = exam;
    this.agenda = new Agenda<Equipment, Doctor>(
      this,
      appointmentDuration,
      startTime,
      dayDuration,
      workDays
    );
  }



  public Agenda<Equipment, Doctor> getAgenda() {
    return this.agenda;
  }
}
