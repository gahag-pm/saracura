package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;

import br.ufmg.dcc.pm.saracura.clinic.Exam;
import br.ufmg.dcc.pm.saracura.ui.views.ListPickDialog;


public class ExamController implements Controller<Void> {
  protected final Controller<Void> examAgendaController;



  public ExamController(Controller<Void> examAgendaController) {
    if (examAgendaController == null)
      throw new IllegalArgumentException("examAgendaController mustn't be null");


    this.examAgendaController = examAgendaController;
  }



  public Void execute(Window parent) {
    // TODO.
    var listPickDialog = new ListPickDialog(parent);
    listPickDialog.setItems(Exam.textMap.values());
    listPickDialog.setVisible(true);

    return null;
  }
}
