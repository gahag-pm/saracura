package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;
import br.ufmg.dcc.pm.saracura.clinic.Exam;
import br.ufmg.dcc.pm.saracura.clinic.Patient;
import br.ufmg.dcc.pm.saracura.clinic.Specialty;
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



  /**
   * Request to the user the patient for the exam.
   * @return the selected patient, or null if canceled
   */
  protected Patient selectPatient(Window parent) {
    if (this.clinic.getPatients().isEmpty()) {
      JOptionPane.showMessageDialog(
        parent,
        "Não há pacientes cadastrados!",
        "Erro",
        JOptionPane.ERROR_MESSAGE
      );
      return null;
    }

    final var patientDialog = new ListPickDialog<Patient>(
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
    patientDialog.setConfirmAction(e -> {
      if (patientDialog.getSelected() == null) {
        JOptionPane.showMessageDialog(
          patientDialog,
          "Selecione o paciente!",
          "Atenção",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      patientDialog.setVisible(false);
    });
    patientDialog.setVisible(true);

    if (patientDialog.getDismissed()) // User canceled.
      return null;

    return patientDialog.getSelected();
  }

  /**
   * Request to the user the specialty for the exam.
   * @return the selected specialty, or null if canceled
   */
  protected Exam selectExam(Window parent) {
    Map<Exam, String> availableExams =
      this.clinic.getEquipments().stream()
                                 .map(e -> e.exam)
                                 .distinct()
                                 .collect(
                                   Collectors.toUnmodifiableMap(
                                     Function.identity(),
                                     Exam.textMap::get
                                   )
                                 );

    if (availableExams.isEmpty()) {
      JOptionPane.showMessageDialog(
        parent,
        "Não há equipamentos disponíveis!",
        "Erro",
        JOptionPane.ERROR_MESSAGE
      );
      return null;
    }

    final var examDialog = new ListPickDialog<Exam>(
      parent,
      "Selecione o tipo de exame",
      availableExams
    );
    examDialog.setConfirmAction(e -> {
      if (examDialog.getSelected() == null) {
        JOptionPane.showMessageDialog(
          examDialog,
          "Selecione o tipo de exame!",
          "Atenção",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      examDialog.setVisible(false);
    });
    examDialog.setVisible(true);

    if (examDialog.getDismissed()) // User canceled.
      return null;

    return examDialog.getSelected();
  }

  public Void execute(Window parent) {
    final var radiologists =
      this.clinic.getDoctors().stream()
                              .filter( 
                                d -> d.specialties.contains(Specialty.RADIOLOGY_AND_DIAGNOSTIC_IMAGING)
                              )
                              .collect(Collectors.toUnmodifiableList());

    if (radiologists.isEmpty()) {
      JOptionPane.showMessageDialog(
        parent,
        "Não há radiologistas!",
        "Erro",
        JOptionPane.ERROR_MESSAGE
      );
      return null;
    }

    final var patient = this.selectPatient(parent);

    if (patient == null) // User canceled.
      return null;

    final var exam = this.selectExam(parent);

    if (exam == null) // User canceled.
      return null;

    // TODO.

    return null;
  }
}
