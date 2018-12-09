package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;
import java.time.Duration;

import javax.swing.JOptionPane;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;
import br.ufmg.dcc.pm.saracura.clinic.Equipment;
import br.ufmg.dcc.pm.saracura.ui.views.EquipmentRegisterDialog;
import br.ufmg.dcc.pm.saracura.util.time.LocalTimeUtil;


public class EquipmentRegisterController implements Controller<Void> {
  protected Clinic clinic;



  public EquipmentRegisterController(Clinic clinic) {
    if (clinic == null) {
      throw new IllegalArgumentException("clinic mustn't be null");
    }
    this.clinic = clinic;
  }



  public Void execute(Window parent) {
    var dialog = new EquipmentRegisterDialog(parent);
    dialog.setConfirmAction(e -> {
      var startTime = dialog.getSelectedStartTime();
      var dayDuration = dialog.getSelectedDayDuration();
      var appointmentDuration = dialog.getSelectedAppointmentDuration();

      if (dialog.getSelectedExam() == null) {
        JOptionPane.showMessageDialog(
          dialog,
          "Selecione o tipo de exame!",
          "EXAME INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (dialog.getSelectedWorkDays().isEmpty()) {
        JOptionPane.showMessageDialog(
          dialog,
          "Selecione ao menos um dia de funcionamento!",
          "DIA DE FUNCIONAMENTO INVÁLIDO",
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
          "Duração de exame inválida!",
          "DURAÇÃO EXAME INVÁLIDA",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (LocalTimeUtil.checkOverflow(startTime, dayDuration)) {
        JOptionPane.showMessageDialog(
          dialog,
          "O horário de funcionamento não pode extrapolar 23:59!",
          "EXPEDIENTE INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (appointmentDuration.compareTo(dayDuration) > 0) {
        JOptionPane.showMessageDialog(
          dialog,
          "Duração de exame não pode extrapolar o expediente!",
          "DURAÇÃO CONSULTA INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }


      dialog.setVisible(false);
    });
    dialog.setVisible(true);


    if (!dialog.getDismissed())
      clinic.addEquipment(
        new Equipment(
          dialog.getSelectedExam(),
          dialog.getSelectedAppointmentDuration(),
          dialog.getSelectedStartTime(),
          dialog.getSelectedDayDuration(),
          dialog.getSelectedWorkDays()
        )
      );


    return null;
  }
}
