package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.math.BigDecimal;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;
import br.ufmg.dcc.pm.saracura.clinic.payment.PaymentMethod;
import br.ufmg.dcc.pm.saracura.ui.views.PaymentCardDialog;
import br.ufmg.dcc.pm.saracura.ui.views.PaymentCashDialog;
import br.ufmg.dcc.pm.saracura.ui.views.PaymentCheckDialog;
import br.ufmg.dcc.pm.saracura.ui.views.PaymentPickDialog;
import br.ufmg.dcc.pm.saracura.ui.views.PaymentPlanDialog;


public class PaymentController implements Controller<Void> {

  protected Clinic clinic;
  protected String name;
  protected String registration;
  protected String cardNumber;
  protected BigDecimal value;

  public PaymentController(Clinic clinic) {
    if (clinic == null) {
      throw new IllegalArgumentException("clinic mustn't be null");
    }
    this.clinic = clinic;
  }

  public Void execute(Window parent) {
    var PaymentPickDialog = new PaymentPickDialog(parent);
    PaymentPickDialog.setVisible(true);
    if(!PaymentPickDialog.getDismissed()){
      PaymentMethod method = PaymentPickDialog.getSelected();
      switch(method){
      case CHECK:
        var PaymentCheckDialog = new PaymentCheckDialog(PaymentPickDialog);
        PaymentCheckDialog.setVisible(true);
        if(!PaymentCheckDialog.getDismissed()){
          this.name = PaymentCheckDialog.getNameFieldContent();
          this.value = PaymentCheckDialog.getValue();
        }
        break;

      case HEALTH_PLAN:
        var PaymentPlanDialog = new PaymentPlanDialog(PaymentPickDialog);
        PaymentPlanDialog.setVisible(true);
        if(!PaymentPlanDialog.getDismissed()){
          this.name = PaymentPlanDialog.getNameFieldContent();
          this.value = PaymentPlanDialog.getValue();
          this.registration = PaymentPlanDialog.getRegFieldContent();
        }
        break;

      case CASH:
        var PaymentCashDialog = new PaymentCashDialog(PaymentPickDialog);
        PaymentCashDialog.setVisible(true);
        if(!PaymentCashDialog.getDismissed()){
          this.value = PaymentCashDialog.getSelected();
        }
        break;

      case CARD:
        var PaymentCardDialog = new PaymentCardDialog(PaymentPickDialog);
        PaymentCardDialog.setVisible(true);
        if(!PaymentCardDialog.getDismissed()){
          this.name = PaymentCardDialog.getNameFieldContent();
          this.cardNumber = PaymentCardDialog.getNumberFieldContent();
          this.value = PaymentCardDialog.getSelected();
        }
        break;

      default:
        break;
      }
    }
    PaymentPickDialog.dispose();
    return null;
  }
}
