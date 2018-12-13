package br.ufmg.dcc.pm.saracura.ui.controls.agenda;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;


public class DayAgenda extends Agenda {
  protected LocalDate date;



  public DayAgenda(Schedule schedule) {
    super(
      schedule,
      List.of(LocalDate.now().getDayOfWeek())
    );

    this.date = LocalDate.now();
  }



  @Override
  protected LocalDate startDate() {
    return this.date;
  }

  @Override
  protected LocalDate endDate() {
    return this.date;
  }

  @Override
  protected LocalDate getDateFromDay(DayOfWeek day) {
    return this.date;
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
}
