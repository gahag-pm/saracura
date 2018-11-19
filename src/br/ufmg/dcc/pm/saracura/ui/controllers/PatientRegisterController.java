package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;

import br.ufmg.dcc.pm.saracura.ui.views.PatientRegisterDialog;


public class PatientRegisterController implements Controller<Void> {
  public PatientRegisterDialog patientRegisterDialog;



  public PatientRegisterController(PatientRegisterDialog patientRegisterDialog) {
    if (patientRegisterDialog == null)
      throw new IllegalArgumentException("patientRegisterDialog mustn't be null");


    this.patientRegisterDialog = patientRegisterDialog;
  }



  public Void execute(Window parent) {
    // TODO.
    this.patientRegisterDialog.setVisible(true);
    return null;
  }
}
