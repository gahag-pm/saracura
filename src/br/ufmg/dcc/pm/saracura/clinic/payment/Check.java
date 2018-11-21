package br.ufmg.dcc.pm.saracura.clinic.payment;

import java.math.BigDecimal;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;


public class Check implements Payment {
  /**
   * The payee's name.
   */
  public final String payee;



  /**
   * Create a check payment.
   * @param payee the payee's naem, mustn't be null
   */
  public Check(String payee) {
    if (payee == null)
      throw new IllegalArgumentException("payee mustn't be null");


    this.payee = payee;
  }



  public Invoice pay(Clinic clinic, BigDecimal value) {
    final int chance = (int)(Math.random() * 10);

    if (chance == 0)
      return null;

    return new Invoice(this.payee, clinic.getName(), clinic.getNRLE(), value);
  }
}
