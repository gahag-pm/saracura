package br.ufmg.dcc.pm.saracura;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;

import br.ufmg.dcc.pm.saracura.clinic.Doctor;
import br.ufmg.dcc.pm.saracura.clinic.Patient;
import br.ufmg.dcc.pm.saracura.clinic.SaraCura;
import br.ufmg.dcc.pm.saracura.clinic.Specialty;
import br.ufmg.dcc.pm.saracura.ui.controllers.AgendaController;
import br.ufmg.dcc.pm.saracura.ui.controllers.AppointmentController;
import br.ufmg.dcc.pm.saracura.ui.controllers.DoctorRegisterController;
import br.ufmg.dcc.pm.saracura.ui.controllers.EquipmentRegisterController;
import br.ufmg.dcc.pm.saracura.ui.controllers.ExamAgendaController;
import br.ufmg.dcc.pm.saracura.ui.controllers.ExamController;
import br.ufmg.dcc.pm.saracura.ui.controllers.MainController;
import br.ufmg.dcc.pm.saracura.ui.controllers.PatientRegisterController;
import br.ufmg.dcc.pm.saracura.ui.controllers.PaymentController;
import br.ufmg.dcc.pm.saracura.ui.views.MainWindow;


public class Main {
  public static void main(String[] args) {
    var saracura = new SaraCura() {{
      addPatient(
        new Patient("130.371.866.90", "gahag", "(31)6669-6969")
      );
      addDoctor(
        new Doctor(
          "PI 2978",
          "Pinheiro Rodrigues",
          Set.of(Specialty.RHEUMATOLOGY, Specialty.UROLOGY),
          Duration.ofMinutes(30),
          LocalTime.of(13, 0),
          Duration.ofHours(6),
          Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY)
        )
      );
    }};


    var mainWindow = new MainWindow();

    var examAgendaController = new ExamAgendaController();
    var appointmentController = new AppointmentController(
      saracura,
      AgendaController::new,
      PaymentController::new
    );
    var examController = new ExamController(saracura, examAgendaController);
    var patientRegisterController = new PatientRegisterController(saracura);
    var doctorRegisterController = new DoctorRegisterController(saracura);
    var equipmentRegisterController = new EquipmentRegisterController(saracura);
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
