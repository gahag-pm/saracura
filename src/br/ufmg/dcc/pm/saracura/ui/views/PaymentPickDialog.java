package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import br.ufmg.dcc.pm.saracura.clinic.payment.PaymentMethod;


/**
 * Creates a window to pick the payment.
 */
public class PaymentPickDialog extends JDialog {
  /**
   * Whether the user dismissed the dialog by pressing the window's close button.
   */
  protected boolean dismissed = false;

  protected final Dimension buttonDimension = new Dimension(250, 75);

  protected JRadioButton healthPlanButton = new JRadioButton("Plano de saúde") {{
    setSize(buttonDimension);
    setMaximumSize(buttonDimension);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected JRadioButton cardButton = new JRadioButton("Cartão") {{
    setSize(buttonDimension);
    setMaximumSize(buttonDimension);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected JRadioButton checkButton = new JRadioButton("Cheque") {{
    setSize(buttonDimension);
    setMaximumSize(buttonDimension);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected JRadioButton cashButton = new JRadioButton("Dinheiro") {{
    setSize(buttonDimension);
    setMaximumSize(buttonDimension);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected JButton confirmButton = new JButton("Ok") {{
    setSize(buttonDimension);
    setMaximumSize(buttonDimension);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};



  /**
   * Creates a payment pick dialog.
   * It is unresizable, dispose on close, positioned at screen center.
   * @param parent the parent window of the dialog
   */
  public PaymentPickDialog(Window parent) {
    super(parent, "Forma de pagamento", ModalityType.APPLICATION_MODAL);

    ButtonGroup group = new ButtonGroup();
    group.add(this.cashButton);
    group.add(this.checkButton);
    group.add(this.cardButton);
    group.add(this.healthPlanButton);

    this.healthPlanButton.setSelected(true);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    panel.add(this.healthPlanButton);
    panel.add(Box.createRigidArea(new Dimension(10,10)));
    panel.add(this.cardButton);
    panel.add(Box.createRigidArea(new Dimension(10,10)));
    panel.add(this.checkButton);
    panel.add(Box.createRigidArea(new Dimension(10,10)));
    panel.add(this.cashButton);
    panel.add(Box.createRigidArea(new Dimension(10,10)));
    panel.add(this.confirmButton);
    this.add(panel);

    this.pack();
    this.setSize(new Dimension(280, 212));
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
   * Get the selected method.
   */
  public PaymentMethod getSelected() {
    if (this.cashButton.isSelected())
      return PaymentMethod.CASH;

    if (this.cardButton.isSelected())
      return PaymentMethod.CARD;

    if (this.healthPlanButton.isSelected())
      return PaymentMethod.HEALTH_PLAN;

    if (this.checkButton.isSelected())
      return PaymentMethod.CHECK;

    return null; // This should never happen.
  }

  /**
   * Whether the user closed the dialog without confirming.
   */
  public boolean getDismissed() {
    return this.dismissed;
  }
}
