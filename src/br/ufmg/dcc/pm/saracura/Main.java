package br.ufmg.dcc.pm.saracura;

import br.ufmg.dcc.pm.saracura.ui.controllers.AgendaController;
import br.ufmg.dcc.pm.saracura.ui.controllers.AppointmentController;
import br.ufmg.dcc.pm.saracura.ui.controllers.DoctorRegisterController;
import br.ufmg.dcc.pm.saracura.ui.controllers.EquipmentRegisterController;
import br.ufmg.dcc.pm.saracura.ui.controllers.ExamAgendaController;
import br.ufmg.dcc.pm.saracura.ui.controllers.ExamController;
import br.ufmg.dcc.pm.saracura.ui.controllers.MainController;
import br.ufmg.dcc.pm.saracura.ui.controllers.PatientRegisterController;
import br.ufmg.dcc.pm.saracura.ui.views.DoctorRegisterDialog;
import br.ufmg.dcc.pm.saracura.ui.views.EquipmentRegisterDialog;
import br.ufmg.dcc.pm.saracura.ui.views.MainWindow;
import br.ufmg.dcc.pm.saracura.ui.views.PatientRegisterDialog;


public class Main {
  public static void main(String[] args) {
    var mainWindow = new MainWindow();
    var patientRegisterDialog = new PatientRegisterDialog();
    var doctorRegisterDialog = new DoctorRegisterDialog();
    var equipmentRegisterDialog = new EquipmentRegisterDialog();


    var agendaController = new AgendaController();
    var examAgendaController = new ExamAgendaController();
    var appointmentController = new AppointmentController(agendaController);
    var examController = new ExamController(examAgendaController);
    var patientRegisterController = new PatientRegisterController(patientRegisterDialog);
    var doctorRegisterController = new DoctorRegisterController(doctorRegisterDialog);
    var equipmentRegisterController = new EquipmentRegisterController(equipmentRegisterDialog);
    var mainController = new MainController(
      mainWindow,
      appointmentController,
      examController,
      patientRegisterController,
      doctorRegisterController,
      equipmentRegisterController
    );


    mainController.execute(null);
  }
}
