package br.ufmg.dcc.pm.saracura.ui.controllers;

import br.ufmg.dcc.pm.saracura.ui.views.MainWindow;

import java.awt.*;


public class MainController implements Controller<Void> {
  public MainWindow window;



  /**
   * Create a main controller.
   * @param window                      the main window, mustn't be null
   * @param appointmentController       the appointment controller, mustn't be null
   * @param examController              the exam controller, mustn't be null
   * @param patientRegisterController   the patient register controller, mustn't be null
   * @param doctorRegisterController    the doctor register controller, mustn't be null
   * @param equipmentRegisterController the equipment register controller, mustn't be null
   */
  public MainController(
    MainWindow window,
    Controller<Void> appointmentController,
    Controller<Void> examController,
    Controller<Void> patientRegisterController,
    Controller<Void> doctorRegisterController,
    Controller<Void> equipmentRegisterController
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
      () -> appointmentController.execute(this.window)
    );
    this.window.addExamAction(
      () -> examController.execute(this.window)
    );
    this.window.addPatientRegisterAction(
      () -> patientRegisterController.execute(this.window)
    );
    this.window.addDoctorRegisterAction(
      () -> doctorRegisterController.execute(this.window)
    );
    this.window.addEquipmentRegisterAction(
      () -> equipmentRegisterController.execute(this.window)
    );
  }



  public Void execute(Window parent) {
    this.window.setVisible(true);
    return null;
  }
}
