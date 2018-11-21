package br.ufmg.dcc.pm.saracura.clinic.payment;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * An invoice.
 * This class is immutable.
 */
public class Invoice {
  /**
   * The payee's name.
   */
  public final String payee;
  /**
   * The beneficiary's name.
   */
  public final String beneficiary;
  /**
   * The beneficiary's NRLE.
   */
  public final String nrle;
  /**
   * The invoice's value.
   */
  public final BigDecimal value;
  /**
   * The invoice's date.
   */
  public final LocalDate date;



  /**
   * Creates a Invoice object.
   * @param payee       the payee's naem, mustn't be null
   * @param beneficiary the beneficiary's name, mustn't be null
   * @param nrle        the beneficiary's NRLE, mustn't be null
   * @param value       the invoice's value, mustn't be null
   * @param date        the invoice's date, mustn't be null
   */
  public Invoice(String payee, String beneficiary, String nrle, BigDecimal value) {
    if (payee == null)
      throw new IllegalArgumentException("payee mustn't be null");

    if (beneficiary == null)
      throw new IllegalArgumentException("beneficiary mustn't be null");

    if (nrle == null)
      throw new IllegalArgumentException("nrle mustn't be null");

    if (value == null)
      throw new IllegalArgumentException("value mustn't be null");


    this.payee = payee;
    this.beneficiary = beneficiary;
    this.nrle = nrle;
    this.value = value;
    this.date = LocalDate.now();
  }
}
