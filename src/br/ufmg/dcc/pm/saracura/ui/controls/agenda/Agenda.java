package br.ufmg.dcc.pm.saracura.ui.controls.agenda;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.NavigableMap;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JComponent;

import br.ufmg.dcc.pm.saracura.util.time.LocalTimeUtil;


public abstract class Agenda extends JComponent {
  // An estimate of the width of a single character 
  protected static final int FONT_LETTER_PIXEL_WIDTH = 7;

  protected static final LocalTime START_TIME = LocalTime.of(7, 0);
  protected static final LocalTime END_TIME = LocalTime.of(20, 0);

  protected static final int MIN_WIDTH = 600;
  protected static final int MIN_HEIGHT = MIN_WIDTH;

  protected static final int HEADER_HEIGHT = 30;
  protected static final int TIME_COL_WIDTH = 100;


  protected final List<DayOfWeek> week;
  protected final NavigableMap<LocalDateTime, AgendaEvent> events;
  protected final Set<DayOfWeek> workDays;
  protected final LocalTime startTime;
  protected final Duration dayDuration;
  protected final Duration appointmentDuration;

  protected double timeScale;
  protected double dayWidth;
  protected Graphics2D g2;

  protected abstract LocalDate startDate();
  protected abstract LocalDate endDate();
  protected abstract LocalDate getDateFromDay(DayOfWeek day);
  protected abstract double dayToPixel(DayOfWeek dayOfWeek);
  protected abstract void setRangeToToday();

  protected Consumer<AgendaEvent> eventClicked = e -> { };
  protected Consumer<LocalDateTime> slotClicked = dt -> { };



  public Agenda(
    List<DayOfWeek> week,
    NavigableMap<LocalDateTime, AgendaEvent> events,
    Set<DayOfWeek> workDays,
    LocalTime startTime,
    Duration dayDuration,
    Duration appointmentDuration
  ) {
    if (week == null)
      throw new IllegalArgumentException("week mustn't be null");

    if (week.isEmpty())
      throw new IllegalArgumentException("week mustn't be empty");

    if (events == null)
      throw new IllegalArgumentException("events mustn't be null");

    if (workDays == null)
      throw new IllegalArgumentException("workDays mustn't be null");

    if (workDays.isEmpty())
      throw new IllegalArgumentException("workDays mustn't be empty");

    if (startTime == null)
      throw new IllegalArgumentException("startTime mustn't be null");

    if (dayDuration == null)
      throw new IllegalArgumentException("dayDuration mustn't be null");

    if (dayDuration == Duration.ZERO)
      throw new IllegalArgumentException("dayDuration must'nt be zero");

    if (LocalTimeUtil.checkOverflow(startTime, dayDuration))
      throw new IllegalArgumentException(
        "the workday period (startTime + dayDuration) exceeds the day"
      );

    if (appointmentDuration == null)
      throw new IllegalArgumentException("appointmentDuration mustn't be null");

    if (appointmentDuration.compareTo(dayDuration) > 0)
      throw new IllegalArgumentException("appointmentDuration bigger than dayDuration");

    if (appointmentDuration == Duration.ZERO)
      throw new IllegalArgumentException("appointmentDuration must'nt be zero");


    this.week = week;
    this.events = events;
    this.workDays = workDays;
    this.startTime = startTime;
    this.dayDuration = dayDuration;
    this.appointmentDuration = appointmentDuration;
    

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        var dateTime = Agenda.this.pointToDateTime(e.getPoint());
        var event = Agenda.this.events.getOrDefault(dateTime, null);

        if (event == null)
          Agenda.this.slotClicked.accept(dateTime);
        else
          Agenda.this.eventClicked.accept(event);
      }
    });
  }



  public void setEventClicked(Consumer<AgendaEvent> eventClicked){
    if (eventClicked == null)
      throw new IllegalArgumentException("eventClicked mustn't be null");

    this.eventClicked = eventClicked;
  }
  public void setSlotClicked(Consumer<LocalDateTime> slotClicked){
    if (slotClicked == null)
      throw new IllegalArgumentException("slotClicked mustn't be null");

    this.slotClicked = slotClicked;
  }


  protected LocalDateTime pointToDateTime(Point p) {
    final LocalDate date = getDateFromDay(pixelToDay(p.getX()));
    final LocalTime time = pixelToTime(p.getY());

    final var appointmentMinutes = this.appointmentDuration.toMinutes();
    final var timeMinutes = ChronoUnit.MINUTES.between(this.startTime, time);
    final var normalizedMinutes = (timeMinutes / appointmentMinutes) * appointmentMinutes;

    return LocalDateTime.of(date, this.startTime.plusMinutes(normalizedMinutes));
  }

  protected void calculateScaleVars() {
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
    dayWidth = (width - TIME_COL_WIDTH) / this.week.size();
  }

  protected double timeToPixel(LocalTime time) {
    return ((time.toSecondOfDay() - START_TIME.toSecondOfDay()) * timeScale) + HEADER_HEIGHT;
  }
  
  protected LocalTime pixelToTime(double y) {
    return LocalTime.ofSecondOfDay((int) ((y - HEADER_HEIGHT) / timeScale) + START_TIME.toSecondOfDay()).truncatedTo(ChronoUnit.MINUTES);
  }
  
  protected DayOfWeek pixelToDay(double x) {
    for (DayOfWeek day : this.week) {
      var pixel = dayToPixel(day);
      if (x >= pixel && x < pixel + dayWidth)
        return day;
    }
    return null;
  }
  
  protected void drawDayHeadings() {
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

  protected void drawGrid() {
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

  protected void drawTodayShade() {
    LocalDate today = LocalDate.now();

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
  
  protected void drawWorkHours() {
    final Color origColor = g2.getColor();
    for(DayOfWeek wDay: this.workDays) {
      final double x = dayToPixel(wDay);
      double y = timeToPixel(this.startTime);
      final double width = dayWidth;
      final double height = timeToPixel(this.startTime.plusHours(this.dayDuration.toHours()))
                          - timeToPixel(this.startTime);
          
      Color alphaGray = new Color(250, 250, 250, 90);
      g2.setColor(alphaGray);
      g2.fill(new Rectangle2D.Double(x, y, width, height));
          
    }
    g2.setColor(origColor);
      
  }

  protected void drawCurrentTimeLine() {
    LocalDate today = LocalDate.now();

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

  protected void drawTimes() {
    int y;
    for (LocalTime time = START_TIME; time.compareTo(END_TIME) <= 0; time = time.plusHours(1)) {
      y = (int) timeToPixel(time) + 15;
      g2.drawString(time.toString(), TIME_COL_WIDTH - (FONT_LETTER_PIXEL_WIDTH * time.toString().length()) - 5, y);
    }
  }

  protected void drawEvents() {
    double x;
    double y0;

    var periodEvents = this.events.subMap(
      this.startDate().atStartOfDay(),
      true,
      this.endDate().plusDays(1).atStartOfDay(),
      false
    );
    
    for (AgendaEvent event : periodEvents.values()) {
      var startTime = event.dateTime.toLocalTime();
      var endTime = startTime.plus(this.appointmentDuration);

      x = dayToPixel(event.dateTime.getDayOfWeek());
      y0 = timeToPixel(startTime);

      Rectangle2D rect = new Rectangle2D.Double(x, y0, dayWidth, (timeToPixel(endTime) - timeToPixel(startTime)));
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

      g2.drawString(startTime + " - " + endTime, (int) x + 5, (int) y0 + 11);

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
    g2.setColor(new Color(200,200,200));
    g2.fillRect(0, 0, getWidth(), getHeight());

    // Set paint colour to black
    g2.setColor(Color.black);

    drawDayHeadings();
    drawGrid();
    drawTimes();
    drawWorkHours();
    drawEvents();

    var today = LocalDate.now();
    if (!(today.isAfter(this.endDate()) || today.isBefore(this.startDate()))) {
      drawTodayShade();
      drawCurrentTimeLine();
    }
  }

  protected double getDayWidth() {
    return dayWidth;
  }

  public void goToToday() {
    setRangeToToday();
    repaint();
  }
}
