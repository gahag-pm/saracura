package br.ufmg.dcc.pm.saracura.ui.controls.agenda;

import java.awt.Color;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * The class to represent events in the agenda control.
 * This class is immutable.
 */
public class AgendaEvent {
  public final LocalDateTime dateTime;
  public final String text;
  public final Color color;



  /**
   * Create a agenda event.
   * @param dateTime the date and time of the event
   * @param duration the duration of the event
   * @param text     the description of the event
   * @param color    the color of the event
   */
  public AgendaEvent(LocalDateTime dateTime, String text, Color color) {
    if (dateTime == null)
      throw new IllegalArgumentException("dateTime mustn't be null");

    if (text == null)
      throw new IllegalArgumentException("text mustn't be null");

    if (color == null)
      throw new IllegalArgumentException("color mustn't be null");


    this.dateTime = dateTime;
    this.text = text;
    this.color = color;
  }

  /**
   * Create a agenda event.
   * @param dateTime the date and time of the event
   * @param text     the description of the event
   */
  public AgendaEvent(LocalDateTime dateTime, String text) {
    this(dateTime, text, Color.PINK);
  }



  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof AgendaEvent))
      return false;

    final AgendaEvent obj = (AgendaEvent) o;

    return this.dateTime.equals(obj.dateTime)
        && this.text.equals(obj.text)
        && this.color.equals(obj.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.dateTime, this.text, this.color);
  }

  @Override
  public String toString() {
    return this.dateTime + ": " + this.text;
  }
}
