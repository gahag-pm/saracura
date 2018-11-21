package br.ufmg.dcc.pm.saracura.clinic.payment;

import java.math.BigDecimal;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;


public class Credit implements Payment {
  /**
   * The card owner's name.
   */
  public final String name;
  /**
   * The card number.
   */
  public final String number;



  /**
   * Creates a Credit object.
   * @param name   the card owner's name, mustn't be null
   * @param number the credit card's number, mustn't be null
   */
  public Credit(String name, String number) {
    if (name == null)
      throw new IllegalArgumentException("name mustn't be null");

    if (number == null)
      throw new IllegalArgumentException("number mustn't be null");


    this.name = name;
    this.number = number;
  }



  public Invoice pay(Clinic clinic, BigDecimal value) {
    final int chance = (int)(Math.random() * 10);

    if (chance == 0)
      return null;

    return new Invoice(this.name, clinic.getName(), clinic.getNRLE(), value);
  }
}
