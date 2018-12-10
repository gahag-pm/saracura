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
import br.ufmg.dcc.pm.saracura.ui.views.PaymentPickDialog;


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

    Invoice result = null;

    var paymentPickDialog = new PaymentPickDialog(parent);
    paymentPickDialog.setVisible(true);

    if (paymentPickDialog.getDismissed())
      return cancel.get();


    switch (paymentPickDialog.getSelected()) {
    case CHECK:
      var checkDialog = PaymentDialogFactory.createCheckDialog(paymentPickDialog);
      checkDialog.setVisible(true);

      if (checkDialog.getDismissed())
        return cancel.get();

      result = new Check(this.patient.name, checkDialog.getSelectedDate()).pay(
        this.clinic,
        checkDialog.getSelectedValue()
      );

      break;


    case HEALTH_PLAN:
      var planDialog = PaymentDialogFactory.createPlanDialog(paymentPickDialog);
      planDialog.setVisible(true);

      if (planDialog.getDismissed())
        return cancel.get();

      result = new HealthPlan(
        planDialog.getSelectedName(),
        planDialog.getSelectedRegistration()
      ).pay(
        this.clinic,
        planDialog.getSelectedValue()
      );

      break;


    case CASH:
      var cashDialog = PaymentDialogFactory.createCashDialog(paymentPickDialog);
      cashDialog.setVisible(true);

      if (cashDialog.getDismissed())
        return cancel.get();

      result = new Cash(this.patient.name).pay(
        this.clinic,
        cashDialog.getSelectedValue()
      );

      break;


    case CREDIT:
    case DEBIT:
      var cardDialog = PaymentDialogFactory.createCardDialog(paymentPickDialog);
      cardDialog.setVisible(true);

      if (cardDialog.getDismissed())
        return cancel.get();

      BiFunction<String, String, Payment> card =
        paymentPickDialog.getSelected() == PaymentMethod.CREDIT ? Credit::new
                                                                : Debit::new;

      result = card.apply(
        cardDialog.getSelectedName(),
        cardDialog.getSelectedNumber()
      ).pay(
        this.clinic, cardDialog.getSelectedValue()
      );

      break;


    default:
      throw new RuntimeException(); // This should never happen.
    }


    if (result == null)
      JOptionPane.showMessageDialog(
        parent,
        "Pagamento reprovado!",
        "Pagamento",
        JOptionPane.ERROR_MESSAGE
      );

    return result;
  }
}
