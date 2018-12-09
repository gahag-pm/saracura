package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.ufmg.dcc.pm.saracura.ui.controls.money.MoneyTextField;


/**
 * A dialog with the cash payment.
 */
public class PaymentCashDialog extends JDialog {
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



  /**
   * Creates a cash payment dialog.
   * It is unresizable, dispose on close, positioned at screen center.
   * @param parent the parent window of the dialog
   */
  public PaymentCashDialog(Window parent) {
    super(parent, "Pagamento em dinheiro", ModalityType.APPLICATION_MODAL);


    this.confirmButton.addActionListener(e -> this.confirmButtonAction.actionPerformed(e));

    JLabel myLabel = new JLabel("Digite o valor:");
    myLabel.setSize(myLabel.getPreferredSize());

    JPanel labelPanel = new JPanel();
    labelPanel.add(myLabel);
    labelPanel.add(ATMInput);

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    panel.add(labelPanel, BorderLayout.NORTH);
    panel.add(confirmButton, BorderLayout.SOUTH);
    this.add(panel);


    this.pack();
    this.setSize(new Dimension(280, 120));
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
