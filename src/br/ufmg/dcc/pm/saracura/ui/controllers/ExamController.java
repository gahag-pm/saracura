package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;

import br.ufmg.dcc.pm.saracura.ui.views.DatePickWindow;
import br.ufmg.dcc.pm.saracura.ui.views.ExamPickWindow;
import br.ufmg.dcc.pm.saracura.ui.views.PatientPickWindow;
import br.ufmg.dcc.pm.saracura.util.ui.WindowUtil;


public class ExamController implements Controller {
  public PatientPickWindow patientPickWindow;
  public ExamPickWindow examPickWindow;
  public DatePickWindow datePickWindow;



  public ExamController(
    PatientPickWindow patientPickWindow,
    ExamPickWindow examPickWindow,
    DatePickWindow datePickWindow,
    Controller examAgendaController
  ) {
    if (patientPickWindow == null)
      throw new IllegalArgumentException("patientPickWindow mustn't be null");

    if (examPickWindow == null)
      throw new IllegalArgumentException("examPickWindow mustn't be null");

    if (datePickWindow == null)
      throw new IllegalArgumentException("datePickWindow mustn't be null");

    if (examAgendaController == null)
      throw new IllegalArgumentException("examAgendaController mustn't be null");


    this.patientPickWindow = patientPickWindow;
    this.examPickWindow = examPickWindow;
    this.datePickWindow = datePickWindow;

    // TODO: examAgenda controller
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
