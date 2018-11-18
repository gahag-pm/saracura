package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import br.ufmg.dcc.pm.saracura.clinic.DayCalendar;
import br.ufmg.dcc.pm.saracura.util.ui.CalendarEvent;

public class DailyAgendaWindow extends JFrame {
    
    private ArrayList<CalendarEvent> events;
    private DayCalendar dayCalendar;
    private JButton goToTodayBtn;
    private JButton nextDayBtn;
    private JButton prevDayBtn;
    private JButton goBack;
    private JPanel weekControls;
    
    
    public DailyAgendaWindow(String operator, ArrayList<CalendarEvent> events) {
        super("Agenda diária de " + operator);
        this.events = events;
        this.dayCalendar = new DayCalendar(this.events);
        this.weekControls = new JPanel(new FlowLayout());
        createButtons();
        setButtonEvents();
        addButtons();
        addContainers();
        this.setSize(1000, 900);
        this.setLocationRelativeTo(null);
        //this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        
    }
    
    private void createButtons() {
        this.goToTodayBtn = new JButton("Hoje");
        this.nextDayBtn = new JButton(">");
        this.prevDayBtn = new JButton("<");
        this.goBack = new JButton("Voltar");
    }
    
    private void setButtonEvents() {

        this.goToTodayBtn.addActionListener(e -> dayCalendar.goToToday());
        this.nextDayBtn.addActionListener(e -> dayCalendar.nextDay());
        this.prevDayBtn.addActionListener(e -> dayCalendar.prevDay());
        this.goBack.addActionListener(e -> this.setVisible(false));
    }
    
    private void addButtons() {
        this.weekControls.add(prevDayBtn);
        this.weekControls.add(goToTodayBtn);
        this.weekControls.add(nextDayBtn);
        this.weekControls.add(goBack);
    }
    
    private void addContainers() {
        this.add(weekControls, BorderLayout.NORTH);
        this.add(dayCalendar, BorderLayout.CENTER);

    }
    
    public void addEvent(LocalDate date, LocalTime startTime, LocalTime endTime, String client) {
        this.events.add(new CalendarEvent(date, startTime, endTime, client));
    }
}
