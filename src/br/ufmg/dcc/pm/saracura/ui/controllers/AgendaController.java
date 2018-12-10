package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

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
      agenda.stream()
            .map(a -> new AgendaEvent(a.time, "consulta - " + a.patient.name))
            .collect(
              Collectors.toMap(
                e -> e.dateTime,
                Function.identity(),
                (v1,v2) ->{ throw new RuntimeException(); }, // This should never happen.
                TreeMap::new
              )
            ),
      agenda.workDays,
      agenda.startTime,
      agenda.dayDuration,
      agenda.appointmentDuration
    );

    agendaDialog.setSlotClicked(dt -> {
      final var time = dt.toLocalTime();

      if ( // Check if dateTime is within the working hours:
        ! agenda.workDays.contains(dt.getDayOfWeek())
        || agenda.startTime.isAfter(time)
        || agenda.startTime.plus(agenda.dayDuration).isBefore(time)
      ) {
        JOptionPane.showMessageDialog(
          agendaDialog,
          "Escolha um horário dentro do expediente!",
          "Atenção",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      agendaDialog.setVisible(false);
    });

    agendaDialog.setEventClicked(e -> {
      JOptionPane.showMessageDialog(
        agendaDialog,
        "Escolha um horário vago!",
        "Atenção",
        JOptionPane.WARNING_MESSAGE
      );
    });

    agendaDialog.setVisible(true);


    return agendaDialog.getSelectedDateTime();
  }
}
