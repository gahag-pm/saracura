package br.ufmg.dcc.pm.saracura.clinic;

import java.util.Collection;


/**
 * The basic clinic interface.
 */
public interface Clinic {
  /**
   * Get the name of the clinic.
   */
  public String getName();
  /**
   * Get the National Registry of Legal Entities.
   */
  public String getNRLE();

  /**
   * Get an *unmodifiable* view of the doctors.
   */
  public Collection<Doctor> getDoctors();
  /**
   * Get an *unmodifiable* view of the equipments.
   */
  public Collection<Equipment> getEquipments();
  /**
   * Get an *unmodifiable* view of the patients.
   */
  public Collection<Patient> getPatients();

  /**
   * Add a doctor to the clinic.
   */
  public void addDoctor(Doctor doctor);
  /**
   * Add an equipment to the clinic.
   */
  public void addEquipment(Equipment equipment);
  /**
   * Add a patient to the clinic.
   */
  public void addPatient(Patient patient);
}
