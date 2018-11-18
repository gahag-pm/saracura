package br.ufmg.dcc.pm.saracura.clinic.payment;

import java.math.BigDecimal;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;


public class Cash implements Payment {
  /**
   * The patient's name.
   */
  public final String name;



  /**
   * Creates a Cash Payment object.
   * @param name the patient's name, mustn't be null
   */
  public Cash(String name){
    if (name == null)
      throw new IllegalArgumentException("name mustn't be null");


    this.name = name;
  }



  public Invoice pay(Clinic clinic, BigDecimal value) {
    return new Invoice(this.name, clinic.getName(), clinic.getNLRE(), value);
  }
}
