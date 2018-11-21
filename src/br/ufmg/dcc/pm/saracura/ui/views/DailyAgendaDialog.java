package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import br.ufmg.dcc.pm.saracura.ui.controls.agenda.AgendaEvent;
import br.ufmg.dcc.pm.saracura.ui.controls.agenda.DayAgenda;


public class DailyAgendaDialog extends JDialog {
  protected List<AgendaEvent> events;

  protected DayAgenda dayAgenda;
  protected JButton goToTodayBtn;
  protected JButton nextDayBtn;
  protected JButton prevDayBtn;
  protected JPanel weekControls;



  public DailyAgendaDialog(Window parent, String owner, List<AgendaEvent> events) {
    super(parent, "Agenda diária de " + owner, ModalityType.APPLICATION_MODAL);

    this.events = events;

    this.dayAgenda = new DayAgenda(this.events);
    this.weekControls = new JPanel(new FlowLayout());

    this.goToTodayBtn = new JButton("Hoje") {{
      addActionListener(e -> dayAgenda.goToToday());
    }};
    this.nextDayBtn = new JButton(">") {{
      addActionListener(e -> dayAgenda.nextDay());
    }};
    this.prevDayBtn = new JButton("<") {{
      addActionListener(e -> dayAgenda.prevDay());
    }};

    this.weekControls.add(prevDayBtn);
    this.weekControls.add(goToTodayBtn);
    this.weekControls.add(nextDayBtn);

    this.add(weekControls, BorderLayout.NORTH);
    this.add(dayAgenda, BorderLayout.CENTER);


    this.setSize(1000, 900);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }



  public void addEvent(LocalDate date, LocalTime startTime, LocalTime endTime, String client) {
    this.events.add(new AgendaEvent(date, startTime, endTime, client));
  }
}
