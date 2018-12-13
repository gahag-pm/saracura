package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.time.LocalDateTime;
import java.util.Collection;

import br.ufmg.dcc.pm.saracura.clinic.Doctor;
import br.ufmg.dcc.pm.saracura.clinic.Equipment;
import br.ufmg.dcc.pm.saracura.util.Triple;


public class ExamAgendaController implements Controller<
  Triple<Equipment, Doctor, LocalDateTime>
> {
  protected final Collection<Equipment> equipments;
  protected final Collection<Doctor>    radiologists;



  public ExamAgendaController(
    Collection<Equipment> equipments,
    Collection<Doctor> radiologists
  ) {
    if (equipments == null)
      throw new IllegalArgumentException("equipments mustn't be null");

    if (equipments.isEmpty())
      throw new IllegalArgumentException("equipments mustn't be null");

    if (radiologists == null)
      throw new IllegalArgumentException("radiologists mustn't be null");

    if (radiologists.isEmpty())
      throw new IllegalArgumentException("radiologists mustn't be empty");


    this.equipments = equipments;
    this.radiologists = radiologists;
  }



  public Triple<Equipment, Doctor, LocalDateTime> execute(Window parent) {
    // TODO.
    return null;
  }
}
