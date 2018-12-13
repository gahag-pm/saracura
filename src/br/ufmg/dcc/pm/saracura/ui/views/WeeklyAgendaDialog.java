package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NavigableMap;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import br.ufmg.dcc.pm.saracura.ui.controls.agenda.AgendaEvent;
import br.ufmg.dcc.pm.saracura.ui.controls.agenda.Schedule;
import br.ufmg.dcc.pm.saracura.ui.controls.agenda.WeekAgenda;


public class WeeklyAgendaDialog extends JDialog {
  protected LocalDateTime selectedDateTime;
  protected AgendaEvent selectedEvent;

  protected Consumer<AgendaEvent> eventClicked = e -> { };
  protected Consumer<LocalDateTime> slotClicked = dt -> { };



  public WeeklyAgendaDialog(Window parent, String owner, Schedule schedule) {
    super(parent, "Agenda semanal de " + owner, ModalityType.APPLICATION_MODAL);


    var agenda = new WeekAgenda(schedule);
    agenda.setSlotClicked(dt -> {
      this.selectedDateTime = dt;
      this.slotClicked.accept(dt);
    });
    agenda.setEventClicked(e -> {
      this.selectedEvent = e;
      this.eventClicked.accept(e);
    });

    var goToTodayBtn = new JButton("Hoje") {{
      addActionListener(e -> agenda.goToToday());
    }};
    var nextWeekBtn = new JButton(">") {{
      addActionListener(e -> agenda.nextWeek());
    }};
    var prevWeekBtn = new JButton("<") {{
      addActionListener(e -> agenda.prevWeek());
    }};
    var nextMonthBtn = new JButton(">>") {{
      addActionListener(e -> agenda.nextMonth());
    }};
    var prevMonthBtn = new JButton("<<") {{
      addActionListener(e -> agenda.prevMonth());
    }};


    var weekControls = new JPanel(new FlowLayout());
    weekControls.add(prevMonthBtn);
    weekControls.add(prevWeekBtn);
    weekControls.add(goToTodayBtn);
    weekControls.add(nextWeekBtn);
    weekControls.add(nextMonthBtn);
    this.add(weekControls, BorderLayout.NORTH);

    this.add(agenda, BorderLayout.CENTER);


    this.setSize(1000, 900);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }



  public void setEventClicked(Consumer<AgendaEvent> eventClicked){
    if (eventClicked == null)
      throw new IllegalArgumentException("eventClicked mustn't be null");

    this.eventClicked = eventClicked;
  }

  public void setSlotClicked(Consumer<LocalDateTime> slotClicked){
    if (slotClicked == null)
      throw new IllegalArgumentException("slotClicked mustn't be null");

    this.slotClicked = slotClicked;
  }

  public LocalDateTime getSelectedDateTime() {
    return this.selectedDateTime;
  }

  public AgendaEvent getSelectedEvent() {
    return this.selectedEvent;
  }
}
