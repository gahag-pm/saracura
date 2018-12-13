package br.ufmg.dcc.pm.saracura.ui.controls.agenda;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import br.ufmg.dcc.pm.saracura.util.time.LocalDateUtil;



public class WeekAgenda extends Agenda {
  protected LocalDate date;



  public WeekAgenda(Schedule schedule) {
    super(
      schedule,
      List.of(
        DayOfWeek.SUNDAY,
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY
      )
    );

    this.date = LocalDate.now();
  }



  @Override
  protected LocalDate startDate() {
    return LocalDateUtil.getStartOfWeek(this.date);
  }

  @Override
  protected LocalDate endDate() {
    return LocalDateUtil.getEndOfWeek(this.date);
  }

  @Override
  protected LocalDate getDateFromDay(DayOfWeek day) {
    return LocalDateUtil.getDayOfWeek(this.date, day);
  }

  @Override
  protected void setRangeToToday() {
    this.date = LocalDate.now();
  }

  @Override
  protected double dayToPixel(DayOfWeek dayOfWeek) {
    return TIME_COL_WIDTH + getDayWidth() * (dayOfWeek.getValue() % 7);
  }


  public void nextWeek() {
    this.date = LocalDateUtil.nextWeek(this.date);
    repaint();
  }

  public void prevWeek() {
    this.date = LocalDateUtil.prevWeek(this.date);
    repaint();
  }

  public void nextMonth() {
    this.date = LocalDateUtil.getStartOfWeek(this.date).plusWeeks(4);
    repaint();
  }

  public void prevMonth() {
    this.date = LocalDateUtil.getStartOfWeek(this.date).minusWeeks(4);
    repaint();
  }
}
