package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;

import javax.swing.JOptionPane;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;
import br.ufmg.dcc.pm.saracura.clinic.Patient;
import br.ufmg.dcc.pm.saracura.ui.views.PatientRegisterDialog;


public class PatientRegisterController implements Controller<Void> {
  protected Clinic clinic;



  public PatientRegisterController(Clinic clinic) {
    if (clinic == null) {
      throw new IllegalArgumentException("clinic mustn't be null");
    }
    this.clinic = clinic;
  }



  public Void execute(Window parent) {
    var dialog = new PatientRegisterDialog(parent);
    dialog.setConfirmAction(e -> {
      if(dialog.getSelectedName().isEmpty()){
        JOptionPane.showMessageDialog(
          dialog,
          "Insira um nome válido!",
          "NOME INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if(dialog.getSelectedNin().isEmpty()){
        JOptionPane.showMessageDialog(
          dialog,
          "Insira um CPF válido!",
          "CPF INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }
      
      if(dialog.getSelectedPhoneNumber().isEmpty()){
          JOptionPane.showMessageDialog(
            dialog,
            "Insira um número de telefone para contato.",
            "TELEFONE INVÁLIDO",
            JOptionPane.WARNING_MESSAGE
          );
          return;
        }

      dialog.setVisible(false);
    });
    dialog.setVisible(true);


    if (dialog.getDismissed())
      return null;


    clinic.addPatient(
      new Patient(
        dialog.getSelectedNin(),
        dialog.getSelectedName(),
        dialog.getSelectedPhoneNumber()
      )
    );


    return null;
  }
}
