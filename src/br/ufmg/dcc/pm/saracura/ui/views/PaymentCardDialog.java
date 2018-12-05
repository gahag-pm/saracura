package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;

import br.ufmg.dcc.pm.saracura.ui.controls.money.CreditCardTextField;
import br.ufmg.dcc.pm.saracura.ui.controls.money.MoneyTextField;


public class PaymentCardDialog extends JDialog {
  /**
   * Whether the user dismissed the dialog by pressing the window's close button.
   */
  protected boolean dismissed = false;
  /**
   * Labels and text fields corresponding to name, value and number.
   */
  protected JLabel name;
  protected JLabel value;
  protected JLabel number;
  protected JTextField numberTextField;
  protected JTextField nameTextField;
  protected MoneyTextField ATMInput = new MoneyTextField();

  protected JButton confirmButton = new JButton("Ok") {{
    setSize(new Dimension(200, 75));
    setMaximumSize(new Dimension(200, 75));
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};



  /**
   * Creates a cash payment dialog.
   * It is unresizable, dispose on close, positioned at screen center.
   * @param parent the parent window of the dialog
   */
  public PaymentCardDialog (Window parent) {
    super(parent, "Pagamento em cartão", ModalityType.APPLICATION_MODAL);
    name = new JLabel("Nome:");
    name.setSize(name.getPreferredSize());
    value = new JLabel("Valor:");
    value.setSize(value.getPreferredSize());
    number = new JLabel("Número do Cartão:");
    number.setSize(number.getPreferredSize());

    numberTextField = new JTextField(10);
    ((AbstractDocument) numberTextField.getDocument()).setDocumentFilter(new CreditCardTextField());
    nameTextField = new JTextField(10);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    panel.add(name);
    panel.add(nameTextField);
    panel.add(value);
    panel.add(ATMInput);
    panel.add(number);
    panel.add(numberTextField);
    panel.add(Box.createRigidArea(new Dimension(10, 15)));
    panel.add(confirmButton);
    panel.setPreferredSize(new Dimension(280, 180));

    this.add(panel);
    this.pack();
    this.setSize(new Dimension(280, 200));
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
   * Get the patient's name.
   */
  public String getNameFieldContent() {
    return this.nameTextField.getText();
  }

  /**
   * Get the patient's credit card's number.
   */
  public String getNumberFieldContent() {
    return this.numberTextField.getText();
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
