package br.ufmg.dcc.pm.saracura;

import br.ufmg.dcc.pm.saracura.clinic.Doctor;
import br.ufmg.dcc.pm.saracura.clinic.Patient;
import br.ufmg.dcc.pm.saracura.clinic.SaraCura;
import br.ufmg.dcc.pm.saracura.clinic.Specialty;
import br.ufmg.dcc.pm.saracura.ui.controllers.*;
import br.ufmg.dcc.pm.saracura.ui.views.DoctorRegisterDialog;
import br.ufmg.dcc.pm.saracura.ui.views.EquipmentRegisterDialog;
import br.ufmg.dcc.pm.saracura.ui.views.MainWindow;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;


public class Main {
  public static void main(String[] args) {
    var saracura = new SaraCura() {{
      addPatient(
        new Patient("130.371.866.90", "gahag")
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
    var doctorRegisterDialog = new DoctorRegisterDialog();
    var equipmentRegisterDialog = new EquipmentRegisterDialog();

    var agendaController = new AgendaController();
    var examAgendaController = new ExamAgendaController();
    var appointmentController = new AppointmentController(saracura, agendaController);
    var examController = new ExamController(saracura, examAgendaController);
    var patientRegisterController = new PatientRegisterController(saracura);
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
