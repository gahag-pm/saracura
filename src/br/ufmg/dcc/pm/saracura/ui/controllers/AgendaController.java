package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import br.ufmg.dcc.pm.saracura.clinic.Doctor;
import br.ufmg.dcc.pm.saracura.ui.controls.agenda.AgendaEvent;
import br.ufmg.dcc.pm.saracura.ui.views.WeeklyAgendaDialog;


public class AgendaController implements Controller<LocalDateTime> {
  protected final Doctor doctor;



  public AgendaController(Doctor doctor) {
    if (doctor == null)
      throw new IllegalArgumentException("doctor mustn't be null");


    this.doctor = doctor;
  }



  public LocalDateTime execute(Window parent) {
    final var agenda = this.doctor.getAgenda();

    final var agendaDialog = new WeeklyAgendaDialog(
      parent,
      this.doctor.name,
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

    LocalDateTime selectedDateTime = agendaDialog.getSelectedDateTime();

    if (selectedDateTime == null) // User canceled.
      return null;

    final var date = selectedDateTime.toLocalDate();
    final var time = selectedDateTime.toLocalTime();
    final var appointmentMinutes = agenda.appointmentDuration.toMinutes();
    final var timeMinutes = ChronoUnit.MINUTES.between(agenda.startTime, time);
    final var normalizedMinutes = (timeMinutes / appointmentMinutes) * appointmentMinutes;

    return LocalDateTime.of(date, agenda.startTime.plusMinutes(normalizedMinutes));
  }
}
