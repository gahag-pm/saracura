package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
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

import br.ufmg.dcc.pm.saracura.ui.controls.money.MoneyTextField;

/**
 * A dialog with the cash payment.
 */
public class PaymentPlanDialog extends JDialog {
  /**
   * Whether the user dismissed the dialog by pressing the window's close button.
   */
  protected boolean dismissed = false;
  /**
   * Labels and text fields corresponding to name, register and value.
   */
  protected JLabel nameLabel;
  protected JTextField nameField;
  protected JLabel regLabel;
  protected JTextField regField;
  protected MoneyTextField ATMInput = new MoneyTextField();

  protected Dimension dimension = new Dimension(200, 75);
  protected JButton cancelButton = new JButton("Cancelar") {{
    setSize(dimension);
    setMaximumSize(new Dimension(200, 75));
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};
  protected JButton confirmButton = new JButton("Ok") {{
    setSize(dimension);
    setMaximumSize(new Dimension(200, 75));
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};



  /**
   * Creates a plan payment dialog.
   * It is unresizable, dispose on close, positioned at screen center.
   * @param parent the parent window of the dialog
   */
  public PaymentPlanDialog(Window parent) {
    super(parent, "Pagamento do plano", ModalityType.APPLICATION_MODAL);

    var panel = new JPanel(new FlowLayout());
    nameLabel = new JLabel("Nome: ");
    nameField = new JTextField(20);
    regLabel = new JLabel("Registro do plano: ");
    regField = new JTextField(20);

    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    panel.add(nameLabel);
    panel.add(nameField);
    panel.add(regLabel);
    panel.add(regField);
    panel.add(Box.createRigidArea(new Dimension(340, 5)));
    panel.add(confirmButton);
    panel.add(cancelButton);

    this.add(panel);
    this.setMinimumSize(new Dimension(300,190));
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
   * Get the money input.
   */
  public BigDecimal getSelected() {
    return this.ATMInput.getValue();
  }

  /**
   * Whether the user closed the dialog without confirming.
   */
  public boolean getDismissed() {
    return this.dismissed;
  }
}
