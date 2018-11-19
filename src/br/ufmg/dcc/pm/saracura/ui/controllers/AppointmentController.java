package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.time.LocalDateTime;

import br.ufmg.dcc.pm.saracura.clinic.Specialty;
import br.ufmg.dcc.pm.saracura.ui.views.ListPickDialog;


public class AppointmentController implements Controller<Void> {
  protected Controller<LocalDateTime> agendaController;



  public AppointmentController(Controller<LocalDateTime> agendaController) {
    if (agendaController == null)
      throw new IllegalArgumentException("agendaController mustn't be null");


    this.agendaController = agendaController;
  }



  public Void execute(Window parent) {
    // TODO.
    var listPickDialog = new ListPickDialog(parent);
    listPickDialog.setItems(Specialty.textMap.values());
    listPickDialog.setVisible(true);

    return null;
  }
}
