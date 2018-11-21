package br.ufmg.dcc.pm.saracura.clinic;


public class Patient {
  /**
   * The patient's NIN.
   */
  public final String nin;
  /**
   * The patient's name.
   */
  public final String name;



  /**
   * Create a patient.
   * @param nin  the patient's NIN, mustn't be null
   * @param name the patient's name, mustn't be null
   */
  public Patient(String nin, String name) {
    if (nin == null)
      throw new IllegalArgumentException("nin mustn't be null");

    if (name == null)
      throw new IllegalArgumentException("name mustn't be null");


    this.nin = nin;
    this.name = name;
  }
}
