package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;

import br.ufmg.dcc.pm.saracura.ui.views.PatientRegisterWindow;
import br.ufmg.dcc.pm.saracura.util.ui.WindowUtil;


public class PatientRegisterController implements Controller {
  public PatientRegisterWindow patientRegisterWindow;



  public PatientRegisterController(PatientRegisterWindow patientRegisterWindow) {
    if (patientRegisterWindow == null)
      throw new IllegalArgumentException("patientRegisterWindow mustn't be null");


    this.patientRegisterWindow = patientRegisterWindow;
  }



  public void show(Window parent) {
    parent.setVisible(false);

    this.patientRegisterWindow.addWindowListener(
      WindowUtil.closeListener(
        (_listener) -> {
          parent.setVisible(true);
          this.patientRegisterWindow.removeWindowListener(_listener);
        }
      )
    );
    this.patientRegisterWindow.setVisible(true);
  }
}
