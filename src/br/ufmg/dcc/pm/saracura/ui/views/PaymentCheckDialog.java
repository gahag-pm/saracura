package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.privatejgoodies.forms.factories.CC;

import br.ufmg.dcc.pm.saracura.ui.controls.money.MoneyTextField;

/**
 * A dialog with the cash payment.
 */
public class PaymentCheckDialog extends JDialog {
  /**
   * Whether the user dismissed the dialog by pressing the window's close button.
   */
  protected boolean dismissed = false;

  protected MoneyTextField ATMInput = new MoneyTextField();

  protected JButton confirmButton = new JButton("Ok") {{
    setSize(new Dimension(200, 75));
    setMaximumSize(new Dimension(200, 75));
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected ActionListener confirmButtonAction = e -> this.setVisible(false);

  protected DatePickerSettings set;
  protected DatePicker picker;
  protected CalendarPanel calendarPanel;



  /**
   * Creates a check payment dialog.
   * It is unresizable, dispose on close, positioned at screen center.
   * @param parent the parent window of the dialog
   */
  public PaymentCheckDialog(Window parent) {
    super(parent, "Pagamento em cheque", ModalityType.APPLICATION_MODAL);


    JPanel cPanel = new JPanel();
    cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.PAGE_AXIS));
    cPanel.setBorder(new EmptyBorder(10, 20, 15, 20));

    set = new DatePickerSettings();
    set.setVisibleClearButton(false);
    picker = new DatePicker(set);
    calendarPanel = new CalendarPanel(picker);

    calendarPanel.setSelectedDate(LocalDate.now());
    picker.setDateToToday();
    cPanel.add(calendarPanel, CC.xy(2, 2));

    this.confirmButton.addActionListener(e -> this.confirmButtonAction.actionPerformed(e));

    var atmPanel = new JPanel();
    atmPanel.setLayout(new BoxLayout(atmPanel, BoxLayout.PAGE_AXIS));
    atmPanel.add(ATMInput);
    atmPanel.setMaximumSize(new Dimension(100, 100));

    var fieldPanel = new JPanel();
    fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.PAGE_AXIS));
    fieldPanel.add(new JLabel("Valor: "));

    var fieldsPanel = new JPanel();
    fieldsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    fieldsPanel.add(fieldPanel);
    fieldsPanel.add(atmPanel);

    var panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    panel.add(cPanel);
    panel.add(fieldsPanel);
    panel.add(Box.createRigidArea(new Dimension(340, 5)));
    panel.add(confirmButton);
    this.add(panel);

    this.setSize(new Dimension(350, 350));
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }



  @Override
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) // WINDOW_CLOSING occurs when the user
      this.dismissed = true;                     // presses the X button, but not when
                                                 // setVisible(false) is called.
    super.processWindowEvent(e);
  }


  @Override
  public void setVisible(boolean b) {
    if (b)
      this.dismissed = false;

    super.setVisible(b);
  }

  /**
   * Set the confirm button's action.
   * @param action the action, mustn't be null
   */
  public void setConfirmAction(ActionListener action) {
    if (action == null)
      throw new IllegalArgumentException("action mustn't be null");

    this.confirmButtonAction = action;
  }

  /**
   * Get the selected date.
   */
  public LocalDate getSelectedDate() {
    return this.picker.getDate();
  }

  /**
   * Get the monetary value.
   */
  public BigDecimal getSelectedValue() {
    return this.ATMInput.getValue();
  }

  /**
   * Whether the user closed the dialog without confirming.
   */
  public boolean getDismissed() {
    return this.dismissed;
  }
}
