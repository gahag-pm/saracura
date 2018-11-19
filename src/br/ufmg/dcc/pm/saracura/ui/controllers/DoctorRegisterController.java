package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;

import br.ufmg.dcc.pm.saracura.ui.views.DoctorRegisterDialog;


public class DoctorRegisterController implements Controller<Void> {
  public DoctorRegisterDialog doctorRegisterDialog;



  public DoctorRegisterController(DoctorRegisterDialog doctorRegisterDialog) {
    if (doctorRegisterDialog == null)
      throw new IllegalArgumentException("doctorRegisterDialog mustn't be null");


    this.doctorRegisterDialog = doctorRegisterDialog;
  }



  public Void execute(Window parent) {
    // TODO.
    this.doctorRegisterDialog.setVisible(true);
    return null;
  }
}
