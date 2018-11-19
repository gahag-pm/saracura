package br.ufmg.dcc.pm.saracura.ui.controls.agenda;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;


public class DayAgenda extends Agenda {
  private LocalDate date;



  public DayAgenda(List<AgendaEvent> events) {
    super(List.of(LocalDate.now().getDayOfWeek()), events);
    date = LocalDate.now();
  }



  @Override
  protected boolean dateInRange(LocalDate date) {
    return date.equals(date);
  }

  @Override
  protected LocalDate getDateFromDay(DayOfWeek day) {
    return date;
  }

  @Override
  protected int numDaysToShow() {
    return 1;
  }

  @Override
  protected void setRangeToToday() {
    date = LocalDate.now();
  }

  @Override
  protected double dayToPixel(DayOfWeek dayOfWeek) {
    return TIME_COL_WIDTH;
  }


  public void nextDay() {
    date = date.plusDays(1);
    repaint();
  }

  public void prevDay() {
    date = date.minusDays(1);
    repaint();
  }
}
