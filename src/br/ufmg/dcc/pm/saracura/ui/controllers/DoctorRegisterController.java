package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;

import javax.swing.JOptionPane;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;
import br.ufmg.dcc.pm.saracura.clinic.Doctor;
import br.ufmg.dcc.pm.saracura.ui.views.DoctorRegisterDialog;


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
      if (dialog.getSelectedCrm().isEmpty()) {
        JOptionPane.showMessageDialog(
          parent,
          "Insira um CRM válido!",
          "CRM INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (dialog.getSelectedName().isEmpty()) {
        JOptionPane.showMessageDialog(
          parent,
          "Insira um nome válido!",
          "NOME INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (dialog.getSelectedSpecialties().isEmpty()) {
        JOptionPane.showMessageDialog(
          parent,
          "Selecione ao menos uma especialidade!",
          "ESPECIALIDADE INVÁLIDA",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (dialog.getSelectedWorkdays().isEmpty()) {
        JOptionPane.showMessageDialog(
          parent,
          "Selecione ao menos um dia de trabalho!",
          "JORNADA DE TRABALHO INVÁLIDA",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (dialog.getSelectedStartTime() == null) {
        JOptionPane.showMessageDialog(
          parent,
          "Horário de início de expediente inválido!",
          "INÍCIO EXPEDIENTE INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (dialog.getSelectedDayDuration() == null) {
        JOptionPane.showMessageDialog(
          parent,
          "Duração de expediente inválida!",
          "DURAÇÃO EXPEDIENTE INVÁLIDO",
          JOptionPane.WARNING_MESSAGE
        );
        return;
      }

      if (dialog.getSelectedAppointmentDuration() == null) {
        JOptionPane.showMessageDialog(
          parent,
          "Duração de consulta inválida!",
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
          dialog.getSelectedWorkdays()
        )
      );


    return null;
  }
}
