package br.ufmg.dcc.pm.saracura.clinic.payment;

import java.math.BigDecimal;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;


/**
 * The interface for a payment method that generates a receipt with the clinic's
 * information.
 */
public interface Payment {
  /**
   * Attempts to execute payment. Returns null on failure.
   * @param clinic payment beneficiary
   * @param value  amount to be paid
   * @return Invoice if success; null otherwise
   */
  public Invoice pay(Clinic clinic, BigDecimal value);
}

