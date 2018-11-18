package br.ufmg.dcc.pm.saracura;

import br.ufmg.dcc.pm.saracura.ui.controllers.AgendaController;
import br.ufmg.dcc.pm.saracura.ui.controllers.AppointmentController;
import br.ufmg.dcc.pm.saracura.ui.controllers.DoctorRegisterController;
import br.ufmg.dcc.pm.saracura.ui.controllers.EquipmentRegisterController;
import br.ufmg.dcc.pm.saracura.ui.controllers.ExamAgendaController;
import br.ufmg.dcc.pm.saracura.ui.controllers.ExamController;
import br.ufmg.dcc.pm.saracura.ui.controllers.MainController;
import br.ufmg.dcc.pm.saracura.ui.controllers.PatientRegisterController;
import br.ufmg.dcc.pm.saracura.ui.views.DatePickWindow;
import br.ufmg.dcc.pm.saracura.ui.views.DoctorPickWindow;
import br.ufmg.dcc.pm.saracura.ui.views.DoctorRegisterWindow;
import br.ufmg.dcc.pm.saracura.ui.views.EquipmentRegisterWindow;
import br.ufmg.dcc.pm.saracura.ui.views.ExamPickWindow;
import br.ufmg.dcc.pm.saracura.ui.views.MainWindow;
import br.ufmg.dcc.pm.saracura.ui.views.PatientPickWindow;
import br.ufmg.dcc.pm.saracura.ui.views.PatientRegisterWindow;
import br.ufmg.dcc.pm.saracura.ui.views.SpecialtyPickWindow;


public class Main {
  public static void main(String[] args) {
    var mainWindow = new MainWindow();
    var patientRegisterWindow = new PatientRegisterWindow();
    var doctorRegisterWindow = new DoctorRegisterWindow();
    var equipmentRegisterWindow = new EquipmentRegisterWindow();
    var patientPickWindow = new PatientPickWindow();
    var specialtyPickWindow = new SpecialtyPickWindow();
    var examPickWindow = new ExamPickWindow();
    var doctorPickWindow = new DoctorPickWindow();
    var datePickWindow = new DatePickWindow();


    var agendaController = new AgendaController();
    var examAgendaController = new ExamAgendaController();
    var appointmentController = new AppointmentController(
      patientPickWindow,
      specialtyPickWindow,
      doctorPickWindow,
      datePickWindow,
      agendaController
    );
    var examController = new ExamController(
      patientPickWindow,
      examPickWindow,
      datePickWindow,
      examAgendaController
    );
    var patientRegisterController = new PatientRegisterController(patientRegisterWindow);
    var doctorRegisterController = new DoctorRegisterController(doctorRegisterWindow);
    var equipmentRegisterController = new EquipmentRegisterController(equipmentRegisterWindow);
    var mainController = new MainController(
      mainWindow,
      appointmentController,
      examController,
      patientRegisterController,
      doctorRegisterController,
      equipmentRegisterController
    );
    mainController.show(null);
  }
}
