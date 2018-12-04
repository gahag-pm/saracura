package br.ufmg.dcc.pm.saracura.ui.controllers;

import br.ufmg.dcc.pm.saracura.ui.views.PatientRegisterDialog;

import java.awt.*;


public class PatientRegisterController implements Controller<Void> {

  public Void execute(Window parent) {
    // TODO.

    var patientRegisterDialog = new PatientRegisterDialog(parent);
    patientRegisterDialog.setVisible(true);

    if(!patientRegisterDialog.isDismissed()){
      if(patientRegisterDialog.getNinFieldContent().equals("")){
        System.out.println("NOME INVALIDO");
      } else {
        System.out.println(patientRegisterDialog.getNameFieldContent());
      }
      if(patientRegisterDialog.getNinFieldContent().equals("")){
        System.out.println("CPF INVALIDO");
      } else {
        System.out.println(patientRegisterDialog.getNinFieldContent());
      }
    }
    patientRegisterDialog.dispose();

    return null;
  }
}
