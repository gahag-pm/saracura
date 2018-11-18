package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import br.ufmg.dcc.pm.saracura.clinic.WeekCalendar;
import br.ufmg.dcc.pm.saracura.util.ui.CalendarEvent;


public class WeeklyAgendaWindow extends JFrame {
    
    private ArrayList<CalendarEvent> events;
    private WeekCalendar weekCalendar;
    private JButton goToTodayBtn;
    private JButton nextWeekBtn;
    private JButton prevWeekBtn;
    private JButton nextMonthBtn;
    private JButton prevMonthBtn;
    private JButton goBack;
    private JPanel weekControls;
    
    
    public WeeklyAgendaWindow(String operator, ArrayList<CalendarEvent> events) {
        super("Agenda semanal de " + operator);
        this.events = events;
        this.weekCalendar = new WeekCalendar(this.events);
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
        this.nextWeekBtn = new JButton(">");
        this.prevWeekBtn = new JButton("<");
        this.nextMonthBtn = new JButton(">>");
        this.prevMonthBtn = new JButton("<<");
        this.goBack = new JButton("Voltar");
    }
    private void setButtonEvents() {

        this.goToTodayBtn.addActionListener(e -> weekCalendar.goToToday());
        this.nextWeekBtn.addActionListener(e -> weekCalendar.nextWeek());
        this.prevWeekBtn.addActionListener(e -> weekCalendar.prevWeek());
        this.nextMonthBtn.addActionListener(e -> weekCalendar.nextMonth());
        this.prevMonthBtn.addActionListener(e -> weekCalendar.prevMonth());
        this.goBack.addActionListener(e -> this.setVisible(false));
    }
    private void addButtons() {
        this.weekControls.add(prevMonthBtn);
        this.weekControls.add(prevWeekBtn);
        this.weekControls.add(goToTodayBtn);
        this.weekControls.add(nextWeekBtn);
        this.weekControls.add(nextMonthBtn);
        this.weekControls.add(goBack);
    }
    
    private void addContainers() {
        this.add(weekControls, BorderLayout.NORTH);
        this.add(weekCalendar, BorderLayout.CENTER);

    }
    
    public void addEvent(LocalDate date, LocalTime startTime, LocalTime endTime, String client) {
        this.events.add(new CalendarEvent(date, startTime, endTime, client));
    }
}
