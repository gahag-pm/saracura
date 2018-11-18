package br.ufmg.dcc.pm.saracura.clinic.payment;

import java.math.BigDecimal;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;


/**
 * The health plan payment method.
 * This class is immutable.
 */
public class HealthPlan implements Payment {
  /**
   * The health plan's name.
   */
  public final String name;
  /**
   * The patient's registration.
   */
  public final String registration;



  /**
   * Creates a HealthPlan object.
   * @param name         the health plan's name, mustn't be null
   * @param registration the patient's registration, mustn't be null
   */
  public HealthPlan(String name, String registration) {
    if (name == null)
      throw new IllegalArgumentException("name mustn't be null");

    if (registration == null)
      throw new IllegalArgumentException("registration mustn't be null");


    this.name = name;
    this.registration = registration;
  }



  public Invoice pay(Clinic clinic, BigDecimal value) {
    final int chance = (int)(Math.random() * 12);

    if (chance == 0)
      return null;

    return new Invoice(
      this.name + " " + this.registration,
      clinic.getName(),
      clinic.getNLRE(),
      value
    );
  }
}
