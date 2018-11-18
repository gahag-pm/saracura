package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;

import br.ufmg.dcc.pm.saracura.ui.views.MainWindow;


public class MainController implements Controller {
  public MainWindow window;



  public MainController(
    MainWindow window,
    Controller appointmentController,
    Controller examController,
    Controller patientRegisterController,
    Controller doctorRegisterController,
    Controller equipmentRegisterController
  ) {
    if (window == null)
      throw new IllegalArgumentException("window mustn't be null");

    if (appointmentController == null)
      throw new IllegalArgumentException("appointmentController mustn't be null");

    if (examController == null)
      throw new IllegalArgumentException("examController mustn't be null");

    if (patientRegisterController == null)
      throw new IllegalArgumentException("patientRegisterController mustn't be null");

    if (doctorRegisterController == null)
      throw new IllegalArgumentException("doctorRegisterController mustn't be null");

    if (equipmentRegisterController == null)
      throw new IllegalArgumentException("equipmentRegisterController mustn't be null");


    this.window = window;

    this.window.addAppointmentAction(
      () -> appointmentController.show(this.window)
    );
    this.window.addExamAction(
      () -> examController.show(this.window)
    );
    this.window.addPatientRegisterAction(
      () -> patientRegisterController.show(this.window)
    );
    this.window.addDoctorRegisterAction(
      () -> doctorRegisterController.show(this.window)
    );
    this.window.addEquipmentRegisterAction(
      () -> equipmentRegisterController.show(this.window)
    );
  }



  public void show(Window parent) {
    this.window.setVisible(true);
  }
}
