package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;

import br.ufmg.dcc.pm.saracura.ui.views.DoctorRegisterWindow;
import br.ufmg.dcc.pm.saracura.util.ui.WindowUtil;


public class DoctorRegisterController implements Controller {
  public DoctorRegisterWindow doctorRegisterWindow;



  public DoctorRegisterController(DoctorRegisterWindow doctorRegisterWindow) {
    if (doctorRegisterWindow == null)
      throw new IllegalArgumentException("doctorRegisterWindow mustn't be null");


    this.doctorRegisterWindow = doctorRegisterWindow;
  }



  public void show(Window parent) {
    parent.setVisible(false);

    this.doctorRegisterWindow.addWindowListener(
      WindowUtil.closeListener(
        (_listener) -> {
          parent.setVisible(true);
          this.doctorRegisterWindow.removeWindowListener(_listener);
        }
      )
    );
    this.doctorRegisterWindow.setVisible(true);
  }
}
