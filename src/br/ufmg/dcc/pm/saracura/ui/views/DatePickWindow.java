package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.privatejgoodies.forms.factories.CC;


public class DatePickWindow extends JFrame {
  protected final Dimension buttonDimension = new Dimension(250, 75);

  protected JButton confirm = new JButton("Confirmar") {{
    setSize(buttonDimension);
    setMaximumSize(buttonDimension);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};



  /**
   * Creates a CalendarWindow.
   * It is unresizable, hide on close, positioned at screen center.
   */
  public DatePickWindow() {
    super("Calendário");

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));

    DatePickerSettings set = new DatePickerSettings();
    set.setVisibleClearButton(false);

    CalendarPanel calendarPanel = new CalendarPanel(set);
    calendarPanel.setSelectedDate(LocalDate.now());
    panel.add(calendarPanel, CC.xy(2, 2));
    panel.add(Box.createRigidArea(new Dimension(10,10)));
    panel.add(confirm);
    this.add(panel);

    this.setResizable(false);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
