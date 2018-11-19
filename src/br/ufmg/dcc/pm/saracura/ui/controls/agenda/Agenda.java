package br.ufmg.dcc.pm.saracura.ui.controls.agenda;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.Timer;


public abstract class Agenda extends JComponent {
  // An estimate of the width of a single character (not exact but good
  // enough)
  private static final int FONT_LETTER_PIXEL_WIDTH = 7;


  protected static final LocalTime START_TIME = LocalTime.of(7, 0);
  protected static final LocalTime END_TIME = LocalTime.of(20, 0);

  protected static final int MIN_WIDTH = 600;
  protected static final int MIN_HEIGHT = MIN_WIDTH;

  protected static final int HEADER_HEIGHT = 30;
  protected static final int TIME_COL_WIDTH = 100;



  public static LocalTime roundTime(LocalTime time, int minutes) {
    LocalTime t = time;

    if (t.getMinute() % minutes > minutes / 2) {
      t = t.plusMinutes(minutes - (t.getMinute() % minutes));
    } else if (t.getMinute() % minutes < minutes / 2) {
      t = t.minusMinutes(t.getMinute() % minutes);
    }

    return t;
  }



  protected List<DayOfWeek> week;
  protected List<AgendaEvent> events;
  protected double timeScale;
  protected double dayWidth;
  protected Graphics2D g2;



  protected Agenda(List<DayOfWeek> week, List<AgendaEvent> events) {
    if (week == null)
      throw new IllegalArgumentException("week mustn't be null");

    if (events == null)
      throw new IllegalArgumentException("events mustn't be null");

    if (week.isEmpty())
      throw new IllegalArgumentException("week mustn't be empty");

    this.week = week;
    this.events = events;

    // Repaints every minute to update the current time line
    Timer timer = new Timer(1000*60, e -> repaint());
    timer.start();
  }

  public Agenda(List<DayOfWeek> week) {
    this(week, new ArrayList<AgendaEvent>());
  }



  protected abstract boolean dateInRange(LocalDate date);
  protected abstract LocalDate getDateFromDay(DayOfWeek day);
  protected abstract int numDaysToShow();
  protected abstract double dayToPixel(DayOfWeek dayOfWeek);
  protected abstract void setRangeToToday();


  private void calculateScaleVars() {
    int width = getWidth();
    int height = getHeight();

    if (width < MIN_WIDTH) {
      width = MIN_WIDTH;
    }

    if (height < MIN_HEIGHT) {
      height = MIN_HEIGHT;
    }

    // Units are pixels per second
    timeScale = (double) (height - HEADER_HEIGHT) / (END_TIME.toSecondOfDay() - START_TIME.toSecondOfDay());
    dayWidth = (width - TIME_COL_WIDTH) / numDaysToShow();
  }

  private double timeToPixel(LocalTime time) {
    return ((time.toSecondOfDay() - START_TIME.toSecondOfDay()) * timeScale) + HEADER_HEIGHT;
  }

  private void drawDayHeadings() {
    for (DayOfWeek day : this.week) {
      LocalDate date = this.getDateFromDay(day);
      int x;

      String text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()) + " "
                  + date.getDayOfMonth() + "/" + date.getMonthValue();

      x = (int) (
        this.dayToPixel(day) + (this.dayWidth / 2) - (
          FONT_LETTER_PIXEL_WIDTH * text.length() / 2
        )
      );


      g2.drawString(text, x, 20);
    }
  }

  private void drawGrid() {
    // Save the original colour
    final Color ORIG_COLOUR = g2.getColor();

    // Set colour to grey with half alpha (opacity)
    Color alphaGray = new Color(128, 128, 128, 128);
    Color alphaGrayLighter = new Color(200, 200, 200, 128);
    g2.setColor(alphaGray);

    // Draw vertical grid lines
    double x;
    for (DayOfWeek day : this.week) {
      x = dayToPixel(day);
      g2.draw(new Line2D.Double(x, HEADER_HEIGHT, x, timeToPixel(END_TIME)));
    }

    // Draw horizontal grid lines
    double y;
    int x1;
    for (LocalTime time = START_TIME; time.compareTo(END_TIME) <= 0; time = time.plusMinutes(30)) {
      y = timeToPixel(time);
      if (time.getMinute() == 0) {
        g2.setColor(alphaGray);
        x1 = 0;
      } else {
        g2.setColor(alphaGrayLighter);
        x1 = TIME_COL_WIDTH;
      }
      g2.draw(new Line2D.Double(x1, y, dayToPixel(this.week.get(this.week.size() - 1)) + dayWidth, y));
    }

    // Reset the graphics context's colour
    g2.setColor(ORIG_COLOUR);
  }

  private void drawTodayShade() {
    LocalDate today = LocalDate.now();

    // Check that date range being viewed is current date range
    if (!dateInRange(today)) return;

    final double x = dayToPixel(today.getDayOfWeek());
    final double y = timeToPixel(START_TIME);
    final double width = dayWidth;
    final double height = timeToPixel(END_TIME) - timeToPixel(START_TIME);

    final Color origColor = g2.getColor();
    Color alphaGray = new Color(200, 200, 200, 64);
    g2.setColor(alphaGray);
    g2.fill(new Rectangle2D.Double(x, y, width, height));
    g2.setColor(origColor);
  }

  private void drawCurrentTimeLine() {
    LocalDate today = LocalDate.now();

    // Check that date range being viewed is current date range
    if (!dateInRange(today)) return;

    final double x0 = dayToPixel(today.getDayOfWeek());
    final double x1 = dayToPixel(today.getDayOfWeek()) + dayWidth;
    final double y = timeToPixel(LocalTime.now());

    final Color origColor = g2.getColor();
    final Stroke origStroke = g2.getStroke();

    g2.setColor(new Color(255, 127, 110));
    g2.setStroke(new BasicStroke(2));
    g2.draw(new Line2D.Double(x0, y, x1, y));

    g2.setColor(origColor);
    g2.setStroke(origStroke);
  }

  private void drawTimes() {
    int y;
    for (LocalTime time = START_TIME; time.compareTo(END_TIME) <= 0; time = time.plusHours(1)) {
      y = (int) timeToPixel(time) + 15;
      g2.drawString(time.toString(), TIME_COL_WIDTH - (FONT_LETTER_PIXEL_WIDTH * time.toString().length()) - 5, y);
    }
  }


  private void drawEvents() {
    double x;
    double y0;

    for (AgendaEvent event : events) {
      if (!dateInRange(event.date)) continue;

      x = dayToPixel(event.date.getDayOfWeek());
      y0 = timeToPixel(event.start);

      Rectangle2D rect = new Rectangle2D.Double(x, y0, dayWidth, (timeToPixel(event.end) - timeToPixel(event.start)));
      Color origColor = g2.getColor();
      g2.setColor(event.color);
      g2.fill(rect);
      g2.setColor(origColor);

      // Draw time header

      // Store the current font state
      Font origFont = g2.getFont();

      final float fontSize = origFont.getSize() - 1.6F;

      // Create a new font with same properties but bold
      Font newFont = origFont.deriveFont(Font.BOLD, fontSize);
      g2.setFont(newFont);

      g2.drawString(event.start + " - " + event.end, (int) x + 5, (int) y0 + 11);

      // Unbolden
      g2.setFont(origFont.deriveFont(fontSize));

      // Draw the event's text
      g2.drawString(event.text, (int) x + 5, (int) y0 + 23);

      // Reset font
      g2.setFont(origFont);
    }
  }


  @Override
  protected void paintComponent(Graphics g) {
    calculateScaleVars();
    g2 = (Graphics2D) g;

    // Rendering hints try to turn anti-aliasing on which improves quality
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Set background to white
    g2.setColor(Color.white);
    g2.fillRect(0, 0, getWidth(), getHeight());

    // Set paint colour to black
    g2.setColor(Color.black);

    drawDayHeadings();
    drawTodayShade();
    drawGrid();
    drawTimes();
    drawEvents();
    drawCurrentTimeLine();
  }

  protected double getDayWidth() {
    return dayWidth;
  }


  public void goToToday() {
    setRangeToToday();
    repaint();
  }

  public void setEvents(ArrayList<AgendaEvent> events) {
    this.events = events;
    repaint();
  }

  public void addEvent(AgendaEvent event) {
    events.add(event);
    repaint();
  }

  public boolean removeEvent(AgendaEvent event) {
    boolean removed = events.remove(event);
    repaint();
    return removed;
  }
}