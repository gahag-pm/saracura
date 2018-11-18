package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;

import br.ufmg.dcc.pm.saracura.ui.views.DatePickWindow;
import br.ufmg.dcc.pm.saracura.ui.views.DoctorPickWindow;
import br.ufmg.dcc.pm.saracura.ui.views.PatientPickWindow;
import br.ufmg.dcc.pm.saracura.ui.views.SpecialtyPickWindow;
import br.ufmg.dcc.pm.saracura.util.ui.WindowUtil;


public class AppointmentController implements Controller {
  public PatientPickWindow patientPickWindow;
  public SpecialtyPickWindow specialtyPickWindow;
  public DoctorPickWindow doctorPickWindow;
  public DatePickWindow datePickWindow;



  public AppointmentController(
    PatientPickWindow patientPickWindow,
    SpecialtyPickWindow specialtyPickWindow,
    DoctorPickWindow doctorPickWindow,
    DatePickWindow datePickWindow,
    Controller agendaController
  ) {
    if (patientPickWindow == null)
      throw new IllegalArgumentException("patientPickWindow mustn't be null");

    if (specialtyPickWindow == null)
      throw new IllegalArgumentException("specialtyPickWindow mustn't be null");

    if (doctorPickWindow == null)
      throw new IllegalArgumentException("doctorPickWindow mustn't be null");

    if (datePickWindow == null)
      throw new IllegalArgumentException("datePickWindow mustn't be null");

    if (agendaController == null)
      throw new IllegalArgumentException("agendaController mustn't be null");


    this.patientPickWindow = patientPickWindow;
    this.specialtyPickWindow = specialtyPickWindow;
    this.doctorPickWindow = doctorPickWindow;
    this.datePickWindow = datePickWindow;

    // TODO: agenda controller
  }



  public void show(Window parent) {
    parent.setVisible(false);

    this.patientPickWindow.addWindowListener(
      WindowUtil.closeListener(
        (_listener) -> {
          parent.setVisible(true);
          this.patientPickWindow.removeWindowListener(_listener);
        }
      )
    );
    this.patientPickWindow.setVisible(true);
  }
}
