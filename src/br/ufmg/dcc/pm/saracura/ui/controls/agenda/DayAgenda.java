package br.ufmg.dcc.pm.saracura.ui.controls.agenda;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;


public class DayAgenda extends Agenda {
  private LocalDate date;



  public DayAgenda(List<AgendaEvent> events) {
    super(List.of(LocalDate.now().getDayOfWeek()), events);
    this.date = LocalDate.now();
  }

  @Override
  protected boolean dateInRange(LocalDate date) {
    return this.date.equals(date);
  }

  @Override
  protected LocalDate getDateFromDay(DayOfWeek day) {
    return this.date;
  }

  @Override
  protected int numDaysToShow() {
    return 1;
  }

  @Override
  protected void setRangeToToday() {
    this.date = LocalDate.now();
  }

  @Override
  protected double dayToPixel(DayOfWeek dayOfWeek) {
    return TIME_COL_WIDTH;
  }


  public void nextDay() {
    this.date = this.date.plusDays(1);
    repaint();
  }

  public void prevDay() {
    this.date = this.date.minusDays(1);
    repaint();
  }

  @Override
  protected DayOfWeek getStartDay() {
    return this.date.getDayOfWeek();
  }

  @Override
  protected DayOfWeek getEndDay() {
    return this.date.getDayOfWeek();
  }
}
