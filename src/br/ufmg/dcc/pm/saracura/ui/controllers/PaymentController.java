package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import javax.swing.JOptionPane;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;
import br.ufmg.dcc.pm.saracura.clinic.Patient;
import br.ufmg.dcc.pm.saracura.clinic.payment.Cash;
import br.ufmg.dcc.pm.saracura.clinic.payment.Check;
import br.ufmg.dcc.pm.saracura.clinic.payment.Credit;
import br.ufmg.dcc.pm.saracura.clinic.payment.Debit;
import br.ufmg.dcc.pm.saracura.clinic.payment.HealthPlan;
import br.ufmg.dcc.pm.saracura.clinic.payment.Invoice;
import br.ufmg.dcc.pm.saracura.clinic.payment.Payment;
import br.ufmg.dcc.pm.saracura.clinic.payment.PaymentMethod;
import br.ufmg.dcc.pm.saracura.ui.views.PaymentCardDialog;
import br.ufmg.dcc.pm.saracura.ui.views.PaymentCashDialog;
import br.ufmg.dcc.pm.saracura.ui.views.PaymentCheckDialog;
import br.ufmg.dcc.pm.saracura.ui.views.PaymentPickDialog;
import br.ufmg.dcc.pm.saracura.ui.views.PaymentPlanDialog;


public class PaymentController implements Controller<Invoice> {
  protected Clinic clinic;
  protected Patient patient;



  public PaymentController(Clinic clinic, Patient patient) {
    if (clinic == null)
      throw new IllegalArgumentException("clinic mustn't be null");

    if (patient == null)
      throw new IllegalArgumentException("patient mustn't be null");

    this.clinic = clinic;
    this.patient = patient;
  }



  public Invoice execute(Window parent) {
    Supplier<Invoice> cancel = () -> {
      JOptionPane.showMessageDialog(
        parent,
        "Pagamento cancelado!",
        "Pagamento",
        JOptionPane.ERROR_MESSAGE
      );

      return null;
    };

    var paymentPickDialog = new PaymentPickDialog(parent);
    paymentPickDialog.setVisible(true);

    if (paymentPickDialog.getDismissed())
      return cancel.get();

    switch (paymentPickDialog.getSelected()) {
    case CHECK:
      var checkDialog = new PaymentCheckDialog(paymentPickDialog);
      checkDialog.setVisible(true);

      if (!checkDialog.getDismissed())
        return cancel.get();

      return new Check(this.patient.name).pay(
        this.clinic,
        checkDialog.getSelectedValue()
      );


    case HEALTH_PLAN:
      var planDialog = new PaymentPlanDialog(paymentPickDialog);
      planDialog.setVisible(true);

      if (planDialog.getDismissed())
        return cancel.get();

      return new HealthPlan(
        planDialog.getSelectedName(),
        planDialog.getSelectedRegistration()
      ).pay(
        this.clinic,
        planDialog.getSelectedValue()
      );


    case CASH:
      var cashDialog = new PaymentCashDialog(paymentPickDialog);
      cashDialog.setVisible(true);

      if (cashDialog.getDismissed())
        return cancel.get();

      return new Cash(this.patient.name).pay(this.clinic, cashDialog.getSelectedValue());


    case CREDIT:
    case DEBIT:
      var cardDialog = new PaymentCardDialog(paymentPickDialog);
      cardDialog.setVisible(true);

      if (cardDialog.getDismissed())
        return cancel.get();

      BiFunction<String, String, Payment> card =
        paymentPickDialog.getSelected() == PaymentMethod.CREDIT ? Credit::new
                                                                : Debit::new;

      return card.apply(
        cardDialog.getSelectedName(),
        cardDialog.getSelectedNumber()
      ).pay(
        this.clinic, cardDialog.getSelectedValue()
      );


    default:
      throw new RuntimeException(); // This should never happen.
    }
  }
}
