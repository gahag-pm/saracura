package br.ufmg.dcc.pm.saracura.clinic.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;


public class Check implements Payment {
  /**
   * The payer's name.
   */
  public final String payer;

  /**
   * The payement's due date.
   */
  public final LocalDate date;



  /**
   * Create a check payment.
   * @param payer the payer's name, mustn't be null
   * @param date the payment's due date, mustn't be null
   */
  public Check(String payer, LocalDate date) {
    if (payer == null)
      throw new IllegalArgumentException("payer mustn't be null");
    if (date == null)
      throw new IllegalArgumentException("date mustn't be null");


    this.payer = payer;
    this.date = date;
  }



  public Invoice pay(Clinic clinic, BigDecimal value) {
    final int chance = (int)(Math.random() * 10);

    if (chance == 0)
      return null;

    return new Invoice(
      this.payer,
      clinic.getName(),
      clinic.getNRLE(),
      this.date,
      value
    );
  }
}
