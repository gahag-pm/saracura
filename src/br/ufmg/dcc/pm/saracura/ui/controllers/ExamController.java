package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;
import br.ufmg.dcc.pm.saracura.clinic.Exam;
import br.ufmg.dcc.pm.saracura.clinic.Patient;
import br.ufmg.dcc.pm.saracura.ui.views.ListPickDialog;


public class ExamController implements Controller<Void> {
  protected Clinic clinic;

  protected final Controller<Void> examAgendaController;



  public ExamController(Clinic clinic, Controller<Void> examAgendaController) {
    if (clinic == null)
      throw new IllegalArgumentException("clinic mustn't be null");


    if (examAgendaController == null)
      throw new IllegalArgumentException("examAgendaController mustn't be null");


    this.clinic = clinic;
    this.examAgendaController = examAgendaController;
  }



  public Void execute(Window parent) {
    // TODO: check empty patients/radiology doctors/equipments
    var patientDialog = new ListPickDialog<Patient>(
      parent,
      "Selecione o paciente",
      this.clinic.getPatients().stream()
                               .collect(
                                 Collectors.toUnmodifiableMap(
                                   Function.identity(),
                                   p -> p.name + " - " + p.nin
                                 )
                               )
    );
    patientDialog.setVisible(true);

    if (patientDialog.getDismissed()) // User canceled.
      return null;

    var patient = patientDialog.getSelected();


    var examDialog = new ListPickDialog<Exam>(parent, "Selecione o exame", Exam.textMap);
    examDialog.setVisible(true);

    if (examDialog.getDismissed()) // User canceled.
      return null;

    var exam = examDialog.getSelected();


    // TODO.

    return null;
  }
}
