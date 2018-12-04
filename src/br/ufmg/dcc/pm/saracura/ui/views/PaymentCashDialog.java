package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

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

  protected NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

  protected JButton confirmButton = new JButton("Ok") {{
    setSize(new Dimension(200, 75));
    setMaximumSize(new Dimension(200, 75));
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  MoneyTextField ATMInput = new MoneyTextField();



  /**
   * Creates a cash payment dialog.
   * It is unresizable, dispose on close, positioned at screen center.
   * @param parent the parent window of the dialog
   */
  public PaymentCashDialog(Window parent) {
    super(parent, "Pagamento em dinheiro", ModalityType.APPLICATION_MODAL);

    JPanel panel = new JPanel();
    JPanel labelPanel = new JPanel();
    JLabel myLabel = new JLabel("Digite o valor:");


    panel.setLayout(new BorderLayout());
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    myLabel.setSize(myLabel.getPreferredSize());
    labelPanel.add(myLabel);
    labelPanel.add(ATMInput);

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
   * Get the money input.
   */
  public BigDecimal getSelected() {
    return this.ATMInput.getValue(); // This should never happen.
  }

  /**
   * Whether the user closed the dialog without confirming.
   */
  public boolean getDismissed() {
    return this.dismissed;
  }
}
