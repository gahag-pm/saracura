package br.ufmg.dcc.pm.saracura.ui.controllers;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;
import br.ufmg.dcc.pm.saracura.clinic.Doctor;
import br.ufmg.dcc.pm.saracura.clinic.Specialty;
import br.ufmg.dcc.pm.saracura.ui.views.DoctorRegisterDialog;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class DoctorRegisterController implements Controller<Void> {
  protected Clinic clinic;

  public DoctorRegisterController(Clinic clinic) {
    if (clinic == null) {
      throw new IllegalArgumentException("clinic mustn't be null");
    }
    this.clinic = clinic;
  }

  public Void execute(Window parent) {

    String crm;
    String doctorName;
    Set<Specialty> specialties;
    Duration appointmentDuration;
    LocalTime startTime;
    Duration dayDuration;
    Set<DayOfWeek> workDays;

    var doctorRegisterDialog = new DoctorRegisterDialog(parent);
    doctorRegisterDialog.setVisible(true);

    if(!doctorRegisterDialog.isDismissed()) {
      /**
       * Checks if doctor name is empty
       */
      if(doctorRegisterDialog.getNameFieldContent().equals("")){
        JOptionPane.showMessageDialog(parent,
                "Insira um nome válido!",
                "NOME INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        doctorName = doctorRegisterDialog.getNameFieldContent();
      }
      /**
       * Checks if doctor crm is empty
       */
      if(doctorRegisterDialog.getCrmFieldContent().equals("")){
        JOptionPane.showMessageDialog(parent,
                "Insira um CRM válido!",
                "CRM INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        crm = doctorRegisterDialog.getCrmFieldContent();
      }
      /**
       * Checks if at least one specialty has been selected
       */
      if(doctorRegisterDialog.getSelectedSpecialties().isEmpty()){
        JOptionPane.showMessageDialog(parent,
                "Selecione ao menos uma especialidade!",
                "ESPECIALIDADE INVÁLIDA",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        specialties = new TreeSet<>();
        Iterator<String> specs = doctorRegisterDialog.getSelectedSpecialties().iterator();
        while(specs.hasNext()) {
          String temp = specs.next();
          for (Specialty specialty : Specialty.values()){
            if(temp.equals(Specialty.textMap.get(specialty))) {
              specialties.add(specialty);
              break;
            }
          }
        }
      }
      /**
       * Checks if at least one work day has been selected
       */
      if(doctorRegisterDialog.getSelectedWorkdays().isEmpty()){
        JOptionPane.showMessageDialog(parent,
                "Selecione ao menos um dia de trabalho!",
                "JORNADA DE TRABALHO INVÁLIDA",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        workDays = new TreeSet<>();
        Iterator<String> days = doctorRegisterDialog.getSelectedWorkdays().iterator();
        while(days.hasNext()) {
          String temp = days.next();
          switch(temp) {
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
            case "Sabado":
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
      if(doctorRegisterDialog.getStartTimeHours() > 23 || doctorRegisterDialog.getStartTimeHours() < 0){
        JOptionPane.showMessageDialog(parent,
                "Hora de início de expediente inválida!",
                "INÍCIO EXPEDIENTE INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else if(doctorRegisterDialog.getStartTimeMinutes() > 59 || doctorRegisterDialog.getStartTimeMinutes() < 0){
        JOptionPane.showMessageDialog(parent,
                "Minutos de início de expediente inválido!",
                "INÍCIO EXPEDIENTE INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        startTime = LocalTime.of(doctorRegisterDialog.getStartTimeHours(), doctorRegisterDialog.getStartTimeMinutes());
      }
      /**
       * Checks if doctor day duration hours and minutes is within normal day and hour range
       * Hour cannot be higher than 23 and cannot be negative
       * Minutes cannot be higher than 59 and cannot be negative
       */
      if(doctorRegisterDialog.getDayDurationHours() > 23 || doctorRegisterDialog.getDayDurationHours() < 0){
        JOptionPane.showMessageDialog(parent,
                "Hora de duração de expediente inválida!",
                "DURAÇÃO EXPEDIENTE INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else if(doctorRegisterDialog.getDayDurationMinutes() > 59 || doctorRegisterDialog.getDayDurationMinutes() < 0){
        JOptionPane.showMessageDialog(parent,
                "Minutos de duração de expediente inválido!",
                "DURAÇÃO EXPEDIENTE INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        dayDuration = Duration.ofHours(doctorRegisterDialog.getDayDurationHours()).plus(Duration.ofMinutes(doctorRegisterDialog.getDayDurationMinutes()));
      }
      /**
       * Checks if doctor appointment duration hours and minutes is within normal day and hour range
       * Hour cannot be higher than 23 and cannot be negative
       * Minutes cannot be higher than 59 and cannot be negative
       */
      if(doctorRegisterDialog.getAppointmentDurationHours() > 23 || doctorRegisterDialog.getAppointmentDurationHours() < 0){
        JOptionPane.showMessageDialog(parent,
                "Hora de duração de consulta inválida!",
                "DURAÇÃO CONSULTA INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else if(doctorRegisterDialog.getAppointmentDurationMinutes() > 59 || doctorRegisterDialog.getAppointmentDurationMinutes() < 0){
        JOptionPane.showMessageDialog(parent,
                "Minutos de duração de consulta inválido!",
                "DURAÇÃO CONSULTA INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        appointmentDuration = Duration.ofHours(doctorRegisterDialog.getAppointmentDurationHours()).plus(Duration.ofMinutes(doctorRegisterDialog.getAppointmentDurationMinutes()));
      }

      clinic.addDoctor(new Doctor(
              crm,
              doctorName,
              specialties,
              appointmentDuration,
              startTime,
              dayDuration,
              workDays
      ));
    }

    doctorRegisterDialog.dispose();

    return null;
  }
}
