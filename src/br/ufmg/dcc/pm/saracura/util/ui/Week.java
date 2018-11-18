package br.ufmg.dcc.pm.saracura.util.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;


public class Week {
    private ArrayList<LocalDate> days;

    // Gets week variables from any date (can be within week)
    public Week(LocalDate date) {
        days = new ArrayList<>();
        LocalDate monday = getStartOfWeek(date);
        days.add(monday);
        for (int i = 1; i < 7; i++) {
            days.add(monday.plusDays(i));
        }
    }

    public static LocalDate getStartOfWeek(LocalDate date) {
        LocalDate day = date;
        while (day.getDayOfWeek() != DayOfWeek.MONDAY) {
            day = day.minusDays(1);
        }
        return day;
    }

    public LocalDate getDay(DayOfWeek dayOfWeek) {
        // DayOfWeek enum starts with monday == 1
        return days.get(dayOfWeek.getValue() - 1);
    }

    public Week nextWeek() {
        final LocalDate sunday = getDay(DayOfWeek.SUNDAY);
        return new Week(sunday.plusDays(1));
    }

    public Week prevWeek() {
        final LocalDate monday = getDay(DayOfWeek.MONDAY);
        return new Week(monday.minusDays(1));
    }

}
