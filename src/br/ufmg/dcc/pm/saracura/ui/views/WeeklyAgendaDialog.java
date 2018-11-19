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
import br.ufmg.dcc.pm.saracura.ui.controls.agenda.WeekAgenda;


public class WeeklyAgendaDialog extends JDialog {
  private List<AgendaEvent> events;

  private WeekAgenda weekAgenda;
  private JButton goToTodayBtn;
  private JButton nextWeekBtn;
  private JButton prevWeekBtn;
  private JButton nextMonthBtn;
  private JButton prevMonthBtn;
  private JPanel weekControls;



  public WeeklyAgendaDialog(Window parent, String owner, List<AgendaEvent> events) {
    super(parent, "Agenda semanal de " + owner);


    if (events == null)
      throw new IllegalArgumentException("events mustn't be null");


    this.events = events;
    this.weekAgenda = new WeekAgenda(this.events);
    this.weekControls = new JPanel(new FlowLayout());

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



  public void addEvent(LocalDate date, LocalTime startTime, LocalTime endTime, String client) {
    this.events.add(new AgendaEvent(date, startTime, endTime, client));
  }
}