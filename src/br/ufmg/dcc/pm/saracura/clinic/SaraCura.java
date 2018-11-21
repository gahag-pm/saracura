package br.ufmg.dcc.pm.saracura.clinic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * The SaraCura clinic.
 */
public class SaraCura implements Clinic {
  public List<Doctor>    doctors    = new ArrayList<Doctor>();
  public List<Equipment> equipments = new ArrayList<Equipment>();
  public List<Patient>   patients   = new ArrayList<Patient>();



  public String getName() {
    return "SaraCura";
  }

  public String getNRLE() {
    return "00.000.000/0001-00";
  }


  public Collection<Doctor> getDoctors() {
    return Collections.unmodifiableCollection(this.doctors);
  }

  public Collection<Equipment> getEquipments() {
    return java.util.Collections.unmodifiableCollection(this.equipments);
  }

  public Collection<Patient> getPatients() {
    return java.util.Collections.unmodifiableCollection(this.patients);
  }


  public void addDoctor(Doctor doctor) {
    if (doctor == null)
      throw new IllegalArgumentException("doctor mustn't be null");

    this.doctors.add(doctor);
  }

  public void addEquipment(Equipment equipment) {
    if (equipment == null)
      throw new IllegalArgumentException("quipment mustn't be null");

    this.equipments.add(equipment);
  }

  public void addPatient(Patient patient) {
    if (patient == null)
      throw new IllegalArgumentException("patient mustn't be null");

    this.patients.add(patient);
  }
}
