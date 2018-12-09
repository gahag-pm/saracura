package br.ufmg.dcc.pm.saracura.ui.views;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;

import br.ufmg.dcc.pm.saracura.ui.controls.NumericFilter;
import br.ufmg.dcc.pm.saracura.ui.controls.money.MoneyTextField;

/**
 * A dialog with the cash payment.
 */
public class PaymentPlanDialog extends JDialog {
  /**
   * Whether the user dismissed the dialog by pressing the window's close button.
   */
  protected boolean dismissed = false;

  protected JTextField nameTextField = new JTextField(14);

  protected JTextField registrationNumberTextField = new JTextField(14) {{
    ((AbstractDocument) getDocument()).setDocumentFilter(new NumericFilter());
  }};

  protected MoneyTextField ATMInput = new MoneyTextField();

  protected JButton confirmButton = new JButton("Ok") {{
    setSize(new Dimension(200, 75));
    setMaximumSize(new Dimension(200, 75));
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected ActionListener confirmButtonAction = e -> this.setVisible(false);


  /**
   * Creates a plan payment dialog.
   * It is unresizable, dispose on close, positioned at screen center.
   * @param parent the parent window of the dialog
   */
  public PaymentPlanDialog(Window parent) {
    super(parent, "Pagamento em cheque", ModalityType.APPLICATION_MODAL);


    this.confirmButton.addActionListener(e -> this.confirmButtonAction.actionPerformed(e));

    var fieldsPanel = new JPanel();
    fieldsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    fieldsPanel.add(new JLabel("Nome:     "));
    fieldsPanel.add(this.nameTextField);
    fieldsPanel.add(Box.createRigidArea(new Dimension(340, 5)));
    fieldsPanel.add(new JLabel("Registro:"));
    fieldsPanel.add(this.registrationNumberTextField);
    fieldsPanel.add(Box.createRigidArea(new Dimension(340, 5)));
    fieldsPanel.add(new JLabel("Valor:      "));
    fieldsPanel.add(ATMInput);
    fieldsPanel.add(Box.createRigidArea(new Dimension(340, 5)));

    var panel = new JPanel(new BorderLayout());
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    panel.add(fieldsPanel);
    panel.add(confirmButton, BorderLayout.SOUTH);
    this.add(panel);


    this.setMinimumSize(new Dimension(300, 200));
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
   * Get the health plan's name.
   */
  public String getSelectedName() {
    return this.nameTextField.getText();
  }

  /**
   * Get the patient's registration number.
   */
  public String getSelectedRegistration() {
    return this.registrationNumberTextField.getText();
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
