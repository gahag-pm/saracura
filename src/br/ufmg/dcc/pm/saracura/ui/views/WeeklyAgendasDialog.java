package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import br.ufmg.dcc.pm.saracura.ui.controls.agenda.AgendaEvent;
import br.ufmg.dcc.pm.saracura.ui.controls.agenda.Schedule;
import br.ufmg.dcc.pm.saracura.ui.controls.agenda.WeekAgenda;


public class WeeklyAgendasDialog<T> extends JDialog {
  protected LocalDateTime selectedDateTime;
  protected AgendaEvent selectedEvent;
  protected T selectedAgenda;

  protected Consumer<AgendaEvent> eventClicked = e -> { };
  protected Consumer<LocalDateTime> slotClicked = dt -> { };



  public WeeklyAgendasDialog(Window parent, Collection<Schedule> schedules) {
    super(parent, "Agendas", ModalityType.APPLICATION_MODAL);


    if (schedules == null)
      throw new IllegalArgumentException("schedules mustn't be null");

    if (schedules.isEmpty())
      throw new IllegalArgumentException("schedules mustn't be empty");


    Collection<WeekAgenda> agendas =
      schedules.stream()
               .map(
                 s -> {
                   var agenda = new WeekAgenda(s);

                   agenda.setSlotClicked(dt -> {
                     this.selectedDateTime = dt;
                     this.slotClicked.accept(dt);
                   });

                   agenda.setEventClicked(e -> {
                     this.selectedEvent = e;
                     this.eventClicked.accept(e);
                   });

                   return agenda;
                 }
               )
               .collect(Collectors.toUnmodifiableList());


    var goToTodayBtn = new JButton("Hoje") {{
      addActionListener(e -> agendas.forEach(WeekAgenda::goToToday));
    }};
    var nextWeekBtn = new JButton(">") {{
      addActionListener(e -> agendas.forEach(WeekAgenda::nextWeek));
    }};
    var prevWeekBtn = new JButton("<") {{
      addActionListener(e -> agendas.forEach(WeekAgenda::prevWeek));
    }};
    var nextMonthBtn = new JButton(">>") {{
      addActionListener(e -> agendas.forEach(WeekAgenda::nextMonth));
    }};
    var prevMonthBtn = new JButton("<<") {{
      addActionListener(e -> agendas.forEach(WeekAgenda::prevMonth));
    }};


    var weekControls = new JPanel(new FlowLayout());
    weekControls.add(prevMonthBtn);
    weekControls.add(prevWeekBtn);
    weekControls.add(goToTodayBtn);
    weekControls.add(nextWeekBtn);
    weekControls.add(nextMonthBtn);
    this.add(weekControls, BorderLayout.NORTH);

    this.add(agendas.iterator().next(), BorderLayout.CENTER); // TODO

    // TODO

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
