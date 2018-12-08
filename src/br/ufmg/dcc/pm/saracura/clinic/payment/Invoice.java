package br.ufmg.dcc.pm.saracura.clinic.payment;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * An invoice.
 * This class is immutable.
 */
public class Invoice {
  /**
   * The payer's name.
   */
  public final String payer;
  /**
   * The beneficiary's name.
   */
  public final String beneficiary;
  /**
   * The beneficiary's NRLE.
   */
  public final String nrle;
  /**
   * The invoice's date.
   */
  public final LocalDate date;
  /**
   * The invoice's value.
   */
  public final BigDecimal value;



  /**
   * Creates a Invoice object.
   * @param payer       the payer's name, mustn't be null
   * @param beneficiary the beneficiary's name, mustn't be null
   * @param nrle        the beneficiary's NRLE, mustn't be null
   * @param date        the invoice's date, mustn't be null
   * @param value       the invoice's value, mustn't be null
   */
  public Invoice(
    String payer,
    String beneficiary,
    String nrle,
    LocalDate date,
    BigDecimal value
  ) {
    if (payer == null)
      throw new IllegalArgumentException("payer mustn't be null");

    if (beneficiary == null)
      throw new IllegalArgumentException("beneficiary mustn't be null");

    if (nrle == null)
      throw new IllegalArgumentException("nrle mustn't be null");

    if (date == null)
      throw new IllegalArgumentException("date mustn't be null");

    if (value == null)
      throw new IllegalArgumentException("value mustn't be null");


    this.payer = payer;
    this.beneficiary = beneficiary;
    this.nrle = nrle;
    this.date = date;
    this.value = value;
  }
}
