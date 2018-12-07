package br.ufmg.dcc.pm.saracura.ui.controllers;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;
import br.ufmg.dcc.pm.saracura.clinic.Equipment;
import br.ufmg.dcc.pm.saracura.clinic.Exam;
import br.ufmg.dcc.pm.saracura.ui.views.EquipmentRegisterDialog;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class EquipmentRegisterController implements Controller<Void> {
  protected Clinic clinic;

  public EquipmentRegisterController(Clinic clinic) {
    if (clinic == null) {
      throw new IllegalArgumentException("clinic mustn't be null");
    }
    this.clinic = clinic;
  }

  public Void execute(Window parent) {

    Exam exam;
    Duration appointmentDuration;
    LocalTime startTime;
    Duration dayDuration;
    Set<DayOfWeek> workDays;

    var equipmentRegisterDialog = new EquipmentRegisterDialog(parent);

    equipmentRegisterDialog.setVisible(true);

    if (!equipmentRegisterDialog.isDismissed()) {
      /**
       * Checks if at least one exam has been selected
       */
      if (equipmentRegisterDialog.getSelectedExam() == null) {
        JOptionPane.showMessageDialog(parent,
                "Selecione um tipo de exame!",
                "EXAME INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        exam = null;
        String temp = equipmentRegisterDialog.getSelectedExam();
        for (Exam tempExam : Exam.values()) {
          if (temp.equals(Exam.textMap.get(tempExam))) {
            exam = tempExam;
            break;
          }
        }
      }

      /**
       * Checks if at least one work day has been selected
       */
      if (equipmentRegisterDialog.getSelectedWorkdays().isEmpty()) {
        JOptionPane.showMessageDialog(parent,
                "Selecione ao menos um dia de funcionamento!",
                "JORNADA DE TRABALHO INVÁLIDA",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        workDays = new TreeSet<>();
        Iterator<String> days = equipmentRegisterDialog.getSelectedWorkdays().iterator();
        while (days.hasNext()) {
          String temp = days.next();
          switch (temp) {
            case "Domingo":
              workDays.add(DayOfWeek.SUNDAY);
              break;
            case "Segunda-feira":
              workDays.add(DayOfWeek.MONDAY);
              break;
            case "Terça-feira":
              workDays.add(DayOfWeek.TUESDAY);
              break;
            case "Quarta-feira":
              workDays.add(DayOfWeek.WEDNESDAY);
              break;
            case "Quinta-feira":
              workDays.add(DayOfWeek.THURSDAY);
              break;
            case "Sexta-feira":
              workDays.add(DayOfWeek.FRIDAY);
              break;
            case "Sábado":
              workDays.add(DayOfWeek.SATURDAY);
              break;
          }
        }
      }
      /**
       * Checks if start time hours and minutes is within normal day and hour range
       * Hour cannot be higher than 23 and cannot be negative
       * Minutes cannot be higher than 59 and cannot be negative
       */
      if (equipmentRegisterDialog.getStartTimeHours() > 23 || equipmentRegisterDialog.getStartTimeHours() < 0) {
        JOptionPane.showMessageDialog(parent,
                "Hora de início de funcionamento inválida!",
                "INÍCIO FUNCIONAMENTO INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else if (equipmentRegisterDialog.getStartTimeMinutes() > 59 || equipmentRegisterDialog.getStartTimeMinutes() < 0) {
        JOptionPane.showMessageDialog(parent,
                "Minutos de início de funcionamento inválido!",
                "INÍCIO FUNCIONAMENTO INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        startTime = LocalTime.of(equipmentRegisterDialog.getStartTimeHours(), equipmentRegisterDialog.getStartTimeMinutes());
      }
      /**
       * Checks if equipment day duration hours and minutes is within normal day and hour range
       * Hour cannot be higher than 23 and cannot be negative
       * Minutes cannot be higher than 59 and cannot be negative
       */
      if (equipmentRegisterDialog.getDayDurationHours() > 23 || equipmentRegisterDialog.getDayDurationHours() < 0) {
        JOptionPane.showMessageDialog(parent,
                "Hora de duração de funcionamentp inválida!",
                "DURAÇÃO FUNCIONAMENTO INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else if (equipmentRegisterDialog.getDayDurationMinutes() > 59 || equipmentRegisterDialog.getDayDurationMinutes() < 0) {
        JOptionPane.showMessageDialog(parent,
                "Minutos de duração de funcionamento inválido!",
                "DURAÇÃO FUNCIONAMENTO INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        dayDuration = Duration.ofHours(equipmentRegisterDialog.getDayDurationHours()).plus(Duration.ofMinutes(equipmentRegisterDialog.getDayDurationMinutes()));
      }
      /**
       * Checks if equipment appointment duration hours and minutes is within normal day and hour range
       * Hour cannot be higher than 23 and cannot be negative
       * Minutes cannot be higher than 59 and cannot be negative
       */
      if (equipmentRegisterDialog.getAppointmentDurationHours() > 23 || equipmentRegisterDialog.getAppointmentDurationHours() < 0) {
        JOptionPane.showMessageDialog(parent,
                "Hora de duração de exame inválida!",
                "DURAÇÃO EXAME INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else if (equipmentRegisterDialog.getAppointmentDurationMinutes() > 59 || equipmentRegisterDialog.getAppointmentDurationMinutes() < 0) {
        JOptionPane.showMessageDialog(parent,
                "Minutos de duração de exame inválido!",
                "DURAÇÃO EXAME INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        appointmentDuration = Duration.ofHours(equipmentRegisterDialog.getAppointmentDurationHours()).plus(Duration.ofMinutes(equipmentRegisterDialog.getAppointmentDurationMinutes()));
      }

      clinic.addEquipment(new Equipment(
                      exam,
                      appointmentDuration,
                      startTime,
                      dayDuration,
                      workDays
      ));
    }

    equipmentRegisterDialog.dispose();

    return null;
  }
}
