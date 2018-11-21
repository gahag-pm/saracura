package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;
import br.ufmg.dcc.pm.saracura.clinic.Doctor;
import br.ufmg.dcc.pm.saracura.clinic.Patient;
import br.ufmg.dcc.pm.saracura.clinic.Specialty;
import br.ufmg.dcc.pm.saracura.ui.views.ListPickDialog;


public class AppointmentController implements Controller<Void> {
  protected Clinic clinic;

  protected Controller<LocalDateTime> agendaController;



  public AppointmentController(Clinic clinic, Controller<LocalDateTime> agendaController) {
    if (clinic == null)
      throw new IllegalArgumentException("clinic mustn't be null");

    if (agendaController == null)
      throw new IllegalArgumentException("agendaController mustn't be null");


    this.clinic = clinic;
    this.agendaController = agendaController;
  }



  public Void execute(Window parent) {
    // TODO: check empty patients/doctors
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


    var specialtyDialog = new ListPickDialog<Specialty>(
      parent,
      "Selecione a especialidade",
      Specialty.textMap
    );
    specialtyDialog.setVisible(true);

    if (specialtyDialog.getDismissed()) // User canceled.
      return null;

    var specialty = specialtyDialog.getSelected();


    var doctorDialog = new ListPickDialog<Doctor>(
      parent,
      "Selecione o médico",
      this.clinic.getDoctors().stream()
                              .filter(
                                d -> d.specialties.contains(specialty)
                              )
                              .collect(
                                Collectors.toUnmodifiableMap(
                                  Function.identity(),
                                  d -> d.name + " - " + d.crm
                                )
                              )
    );
    doctorDialog.setVisible(true);

    if (doctorDialog.getDismissed()) // User canceled.
      return null;

    var doctor = doctorDialog.getSelected();


    // TODO.

    return null;
  }
}
