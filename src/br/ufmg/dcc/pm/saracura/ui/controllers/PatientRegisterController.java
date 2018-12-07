package br.ufmg.dcc.pm.saracura.ui.controllers;

import br.ufmg.dcc.pm.saracura.clinic.Clinic;
import br.ufmg.dcc.pm.saracura.clinic.Patient;
import br.ufmg.dcc.pm.saracura.ui.views.PatientRegisterDialog;

import javax.swing.*;
import java.awt.*;


public class PatientRegisterController implements Controller<Void> {

  protected Clinic clinic;

  public PatientRegisterController(Clinic clinic) {
    if (clinic == null) {
      throw new IllegalArgumentException("clinic mustn't be null");
    }
    this.clinic = clinic;
  }

  public Void execute(Window parent) {

    String patientName;
    String nin;

    var patientRegisterDialog = new PatientRegisterDialog(parent);
    patientRegisterDialog.setVisible(true);

    if(!patientRegisterDialog.isDismissed()){
      if(patientRegisterDialog.getNameFieldContent().equals("")){
        JOptionPane.showMessageDialog(parent,
                "Insira um nome válido!",
                "NOME INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        patientName = patientRegisterDialog.getNameFieldContent();
      }

      if(patientRegisterDialog.getNinFieldContent().equals("")){
        JOptionPane.showMessageDialog(parent,
                "Insira um CPF válido!",
                "CPF INVÁLIDO",
                JOptionPane.WARNING_MESSAGE);
        return null;
      } else {
        nin = patientRegisterDialog.getNinFieldContent();
      }

      clinic.addPatient(new Patient(nin, patientName));
    }
    patientRegisterDialog.dispose();

    return null;
  }
}
