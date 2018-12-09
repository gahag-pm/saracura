package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import br.ufmg.dcc.pm.saracura.ui.views.PaymentCardDialog;
import br.ufmg.dcc.pm.saracura.ui.views.PaymentCashDialog;
import br.ufmg.dcc.pm.saracura.ui.views.PaymentCheckDialog;
import br.ufmg.dcc.pm.saracura.ui.views.PaymentPlanDialog;


public final class PaymentDialogFactory {
  private PaymentDialogFactory() {
  }


  public static PaymentCheckDialog createCheckDialog(Window parent) {
    var checkDialog = new PaymentCheckDialog(parent);
    checkDialog.setConfirmAction(e -> {
      if (checkDialog.getSelectedValue().compareTo(BigDecimal.ZERO) == 0) {
        JOptionPane.showMessageDialog(
          checkDialog,
          "Digite um valor",
          "VALOR INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (checkDialog.getSelectedDate().isBefore(LocalDate.now())) {
        JOptionPane.showMessageDialog(
          checkDialog,
          "Não é possível voltar no tempo!",
          "DATA INVÁLIDA",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      checkDialog.setVisible(false);
    });


    return checkDialog;
  }


  public static PaymentCashDialog createCashDialog(Window parent) {
    var cashDialog = new PaymentCashDialog(parent);
    cashDialog.setConfirmAction(e -> {
      if (cashDialog.getSelectedValue().compareTo(BigDecimal.ZERO) == 0) {
        JOptionPane.showMessageDialog(
          cashDialog,
          "Digite um valor",
          "VALOR INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      cashDialog.setVisible(false);
    });


    return cashDialog;
  }


  public static PaymentPlanDialog createPlanDialog(Window parent) {
    var planDialog = new PaymentPlanDialog(parent);
    planDialog.setConfirmAction(e -> {
      if (planDialog.getSelectedName().isEmpty()) {
        JOptionPane.showMessageDialog(
          planDialog,
          "Insira um nome",
          "NOME INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (planDialog.getSelectedRegistration().isEmpty()) {
        JOptionPane.showMessageDialog(
          planDialog,
          "Insira um registro",
          "REGISTRO INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (planDialog.getSelectedValue().compareTo(BigDecimal.ZERO) == 0) {
        JOptionPane.showMessageDialog(
          planDialog,
          "Digite um valor",
          "VALOR INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      planDialog.setVisible(false);
    });


    return planDialog;
  }


  public static PaymentCardDialog createCardDialog(Window parent) {
    var cardDialog = new PaymentCardDialog(parent);
    cardDialog.setConfirmAction(e -> {
      var number = cardDialog.getSelectedNumber();

      if (cardDialog.getSelectedValue().compareTo(BigDecimal.ZERO) == 0) {
        JOptionPane.showMessageDialog(
          cardDialog,
          "Insira um valor",
          "VALOR INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
          );
        return;
      }

      if (cardDialog.getSelectedName().isEmpty()) {
        JOptionPane.showMessageDialog(
          cardDialog,
          "Insira o nome como consta no cartão",
          "NOME INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
          );
        return;
      }

      if (cardDialog.getSelectedNumber().isEmpty()) {
        JOptionPane.showMessageDialog(
          cardDialog,
          "Insira o número do cartão",
          "NÚMERO INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
          );
        return;
      }

      if (number.chars().filter(Character::isDigit).count() != 16) {
        JOptionPane.showMessageDialog(
          cardDialog,
          "Número do cartão inválido",
          "NÚMERO INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
          );
        return;
      }

      cardDialog.setVisible(false);
    });


    return cardDialog;
  }
}
