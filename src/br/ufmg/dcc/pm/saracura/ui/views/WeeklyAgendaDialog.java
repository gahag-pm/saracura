package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import br.ufmg.dcc.pm.saracura.ui.controls.agenda.AgendaEvent;
import br.ufmg.dcc.pm.saracura.ui.controls.agenda.AgendaUnscheduledClickEvent;
import br.ufmg.dcc.pm.saracura.ui.controls.agenda.WeekAgenda;


public class WeeklyAgendaDialog extends JDialog {
  private WeekAgenda weekAgenda;
  private JButton goToTodayBtn;
  private JButton nextWeekBtn;
  private JButton prevWeekBtn;
  private JButton nextMonthBtn;
  private JButton prevMonthBtn;
  private JPanel weekControls;
  private LocalDateTime selectedDateTime;



  public WeeklyAgendaDialog(
    Window parent,
    String owner,
    Iterable<AgendaEvent> events,
    Set<DayOfWeek> workDays,
    LocalTime startTime,
    int workHours
  ) {
    super(parent, "Agenda semanal de " + owner, ModalityType.APPLICATION_MODAL);


    if (events == null)
      throw new IllegalArgumentException("events mustn't be null");


    this.weekAgenda = new WeekAgenda(events, workDays, startTime, workHours);
    this.weekControls = new JPanel(new FlowLayout());
    
    this.weekAgenda.addAgendaScheduledEventListener(e -> System.out.println(e.getAgendaEvent()));
    this.weekAgenda.addAgendaUnscheduledEventListener(e -> {
        System.out.println(e.getDateTime());
        setSelectedDateTime(e);
    });
    
    this.goToTodayBtn = new JButton("Hoje") {{
      addActionListener(e -> weekAgenda.goToToday());
    }};
    this.nextWeekBtn = new JButton(">") {{
      addActionListener(e -> weekAgenda.nextWeek());
    }};
    this.prevWeekBtn = new JButton("<") {{
      addActionListener(e -> weekAgenda.prevWeek());
    }};
    this.nextMonthBtn = new JButton(">>") {{
      addActionListener(e -> weekAgenda.nextMonth());
    }};
    this.prevMonthBtn = new JButton("<<") {{
      addActionListener(e -> weekAgenda.prevMonth());
    }};


    this.weekControls.add(prevMonthBtn);
    this.weekControls.add(prevWeekBtn);
    this.weekControls.add(goToTodayBtn);
    this.weekControls.add(nextWeekBtn);
    this.weekControls.add(nextMonthBtn);

    this.add(weekControls, BorderLayout.NORTH);
    this.add(weekAgenda, BorderLayout.CENTER);

    this.setSize(1000, 900);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }
  private void setSelectedDateTime(AgendaUnscheduledClickEvent event) {
      this.selectedDateTime = event.getDateTime();
  }
  public LocalDateTime getSelectedDateTime() {
      return this.selectedDateTime;
  }
}
