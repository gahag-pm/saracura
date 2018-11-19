package br.ufmg.dcc.pm.saracura.ui.controls.agenda;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;


/**
 * The class to represent events in the agenda control.
 * This class is immutable.
 */
public class AgendaEvent {
  public final LocalDate date;
  public final LocalTime start;
  public final LocalTime end;
  public final String text;
  public final Color color;



  /**
   * Create a agenda event.
   * @param date  the date of the event
   * @param start the start time of the event
   * @param end   the end time of the event
   * @param text  the description of the event
   */
  public AgendaEvent(
    LocalDate date,
    LocalTime start,
    LocalTime end,
    String text,
    Color color
  ) {
    if (date == null)
      throw new IllegalArgumentException("date mustn't be null");

    if (start == null)
      throw new IllegalArgumentException("start mustn't be null");

    if (end == null)
      throw new IllegalArgumentException("end mustn't be null");

    if (text == null)
      throw new IllegalArgumentException("text mustn't be null");

    if (color == null)
      throw new IllegalArgumentException("color mustn't be null");

    if (!start.isBefore(end))
      throw new IllegalArgumentException("start must precede end");


    this.date = date;
    this.start = start;
    this.end = end;
    this.text = text;
    this.color = color;
  }

  /**
   * Create a agenda event with the default color (pink).
   * @param date  the date of the event
   * @param start the start time of the event
   * @param end   the end time of the event
   * @param text  the description of the event
   */
  public AgendaEvent(LocalDate date, LocalTime start, LocalTime end, String text) {
    this(date, start, end, text, Color.PINK);
  }



  @Override
  public String toString() {
    return this.date + " " + this.start + "-" + this.end + ". " + this.text;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof AgendaEvent))
      return false;

    final AgendaEvent obj = (AgendaEvent) o;

    return this.date.equals(obj.date)
        && this.start.equals(obj.start)
        && this.end.equals(obj.end);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.date, this.start, this.end);
  }
}
