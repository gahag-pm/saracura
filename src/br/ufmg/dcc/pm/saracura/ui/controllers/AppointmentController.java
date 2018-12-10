package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;
import br.ufmg.dcc.pm.saracura.clinic.Doctor;
import br.ufmg.dcc.pm.saracura.clinic.Patient;
import br.ufmg.dcc.pm.saracura.clinic.Specialty;
import br.ufmg.dcc.pm.saracura.ui.controls.agenda.AgendaEvent;
import br.ufmg.dcc.pm.saracura.ui.views.ListPickDialog;
import br.ufmg.dcc.pm.saracura.ui.views.WeeklyAgendaDialog;


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



  /**
   * Request to the user the patient for the appointment.
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
   * Request to the user the specialty for the appointment.
   * This lists only the specialties for which there are doctors.
   * @return the selected specialty, or null if canceled
   */
  protected Specialty selectSpecialty(Window parent) {
    Map<Specialty, String> availableSpecialties =
      this.clinic.getDoctors().stream()
                              .map(d -> d.specialties)
                              .flatMap(ss -> ss.stream())
                              .distinct()
                              .collect(
                                Collectors.toUnmodifiableMap(
                                  Function.identity(),
                                  Specialty.textMap::get
                                )
                              );

    if (availableSpecialties.isEmpty()) {
      JOptionPane.showMessageDialog(
        parent,
        "Não há especialidades disponíveis!",
        "Erro",
        JOptionPane.ERROR_MESSAGE
      );
      return null;
    }

    final var specialtyDialog = new ListPickDialog<Specialty>(
      parent,
      "Selecione a especialidade",
      availableSpecialties
    );
    specialtyDialog.setConfirmAction(e -> {
      if (specialtyDialog.getSelected() == null) {
        JOptionPane.showMessageDialog(
          specialtyDialog,
          "Selecione a especialidade!",
          "Atenção",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      specialtyDialog.setVisible(false);
    });
    specialtyDialog.setVisible(true);

    if (specialtyDialog.getDismissed()) // User canceled.
      return null;

    return specialtyDialog.getSelected();
  }

  /**
   * Request to the user the doctor for the appointment.
   * @return the selected doctor, or null if canceled
   */
  public Doctor selectDoctor(Window parent, Specialty specialty) {
    final var doctorDialog = new ListPickDialog<Doctor>(
      parent,
      "Selecione o médico",
      this.clinic.getDoctors().stream() // No need to check if there are no doctors with
                              .filter(  // the specialty, as selectSpecialty does that.
                                d -> d.specialties.contains(specialty)
                              )
                              .collect(
                                Collectors.toUnmodifiableMap(
                                  Function.identity(),
                                  d -> d.name + " - " + d.crm
                                )
                              )
    );
    doctorDialog.setConfirmAction(e -> {
      if (doctorDialog.getSelected() == null) {
        JOptionPane.showMessageDialog(
          doctorDialog,
          "Selecione o médico!",
          "Atenção",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      doctorDialog.setVisible(false);
    });
    doctorDialog.setVisible(true);

    if (doctorDialog.getDismissed()) // User canceled.
      return null;

    return doctorDialog.getSelected();
  }

  public LocalDateTime selectDateTime(Window parent, Doctor doctor) {
    final var agenda = doctor.getAgenda();

    final var agendaDialog = new WeeklyAgendaDialog(
      parent,
      doctor.name,
      agenda.view().stream().map(
        a -> new AgendaEvent(
          a.time.toLocalDate(),
          a.time.toLocalTime(),
          a.time.toLocalTime().plus(agenda.appointmentDuration),
          "consulta"
        )
      ).collect(Collectors.toUnmodifiableList()),
      agenda.workDays,
      agenda.startTime,
      agenda.dayDuration.toHoursPart()
    );
    agendaDialog.setVisible(true);

    LocalDateTime selectedDateTime = null; // TODO: get from agendaDialog.

    if (selectedDateTime == null) // User canceled.
      return null;

    final var date = selectedDateTime.toLocalDate();
    final var time = selectedDateTime.toLocalTime();
    final var appointmentMinutes = agenda.appointmentDuration.toMinutes();
    final var timeMinutes = ChronoUnit.MINUTES.between(agenda.startTime, time);
    final var normalizedMinutes = (timeMinutes / appointmentMinutes) * appointmentMinutes;

    return LocalDateTime.of(date, agenda.startTime.plusMinutes(normalizedMinutes));
  }

  public Void execute(Window parent) {
    final var patient = this.selectPatient(parent);

    if (patient == null) // User canceled.
      return null;


    final var specialty = this.selectSpecialty(parent);

    if (specialty == null) // User canceled.
      return null;


    final var doctor = this.selectDoctor(parent, specialty);

    if (doctor == null) // User canceled.
      return null;


    final var dateTime = this.selectDateTime(parent, doctor);

    if (dateTime == null) // User canceled.
      return null;


    // TODO: payment.


    doctor.getAgenda().scheduleAppointment(dateTime, patient, "consulta");

    return null;
  }
}
