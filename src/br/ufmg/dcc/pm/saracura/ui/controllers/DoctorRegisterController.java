package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.time.Duration;

import javax.swing.JOptionPane;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;
import br.ufmg.dcc.pm.saracura.clinic.Doctor;
import br.ufmg.dcc.pm.saracura.ui.views.DoctorRegisterDialog;
import br.ufmg.dcc.pm.saracura.util.time.LocalTimeUtil;


public class DoctorRegisterController implements Controller<Void> {
  protected Clinic clinic;



  public DoctorRegisterController(Clinic clinic) {
    if (clinic == null) {
      throw new IllegalArgumentException("clinic mustn't be null");
    }
    this.clinic = clinic;
  }



  public Void execute(Window parent) {
    var dialog = new DoctorRegisterDialog(parent);
    dialog.setConfirmAction(e -> {
      var startTime = dialog.getSelectedStartTime();
      var dayDuration = dialog.getSelectedDayDuration();
      var appointmentDuration = dialog.getSelectedAppointmentDuration();

      if (dialog.getSelectedName().isEmpty()) {
        JOptionPane.showMessageDialog(
          dialog,
          "Insira um nome válido!",
          "NOME INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (dialog.getSelectedCrm().isEmpty()) {
        JOptionPane.showMessageDialog(
          dialog,
          "Insira um CRM válido!",
          "CRM INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (dialog.getSelectedSpecialties().isEmpty()) {
        JOptionPane.showMessageDialog(
          dialog,
          "Selecione ao menos uma especialidade!",
          "ESPECIALIDADE INVÁLIDA",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (dialog.getSelectedWorkDays().isEmpty()) {
        JOptionPane.showMessageDialog(
          dialog,
          "Selecione ao menos um dia de trabalho!",
          "JORNADA DE TRABALHO INVÁLIDA",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (startTime == null) {
        JOptionPane.showMessageDialog(
          dialog,
          "Horário de início de expediente inválido!",
          "INÍCIO EXPEDIENTE INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (dayDuration == null || dayDuration == Duration.ZERO) {
        JOptionPane.showMessageDialog(
          dialog,
          "Duração de expediente inválida!",
          "DURAÇÃO EXPEDIENTE INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (appointmentDuration == null || appointmentDuration == Duration.ZERO) {
        JOptionPane.showMessageDialog(
          dialog,
          "Duração de consulta inválida!",
          "DURAÇÃO CONSULTA INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (LocalTimeUtil.checkOverflow(startTime, dayDuration)) {
        JOptionPane.showMessageDialog(
          dialog,
          "O dia de trabalho não pode extrapolar 23:59!",
          "EXPEDIENTE INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (appointmentDuration.compareTo(dayDuration) > 0) {
        JOptionPane.showMessageDialog(
          dialog,
          "Duração de consulta não pode extrapolar o expediente!",
          "DURAÇÃO CONSULTA INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }


      dialog.setVisible(false);
    });
    dialog.setVisible(true);


    if (!dialog.getDismissed())
      clinic.addDoctor(
        new Doctor(
          dialog.getSelectedCrm(),
          dialog.getSelectedName(),
          dialog.getSelectedSpecialties(),
          dialog.getSelectedAppointmentDuration(),
          dialog.getSelectedStartTime(),
          dialog.getSelectedDayDuration(),
          dialog.getSelectedWorkDays()
        )
      );


    return null;
  }
}
