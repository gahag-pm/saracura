package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.time.LocalDateTime;

import br.ufmg.dcc.pm.saracura.clinic.Equipment;
import br.ufmg.dcc.pm.saracura.util.Tuple;


public class ExamAgendaController implements Controller<Tuple<Equipment, LocalDateTime>> {
  protected final Equipment equipment;



  public ExamAgendaController(Equipment equipment) {
    if (equipment == null)
      throw new IllegalArgumentException("equipment mustn't be null");


    this.equipment = equipment;
  }



  public Tuple<Equipment, LocalDateTime> execute(Window parent) {
    // TODO.
    return null;
  }
}
