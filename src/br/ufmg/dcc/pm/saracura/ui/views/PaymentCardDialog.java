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

import br.ufmg.dcc.pm.saracura.ui.controls.NumericFilter;
import br.ufmg.dcc.pm.saracura.ui.controls.money.MoneyTextField;


public class PaymentCardDialog extends JDialog {
  /**
   * Whether the user dismissed the dialog by pressing the window's close button.
   */
  protected boolean dismissed = false;

  protected JTextField cardNumberTextField = new JTextField(10) {{
    ((AbstractDocument) getDocument()).setDocumentFilter(new NumericFilter());
  }};

  protected JTextField nameTextField = new JTextField(10);

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


    this.confirmButton.addActionListener(e -> this.setVisible(false));

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    panel.add(new JLabel("Nome:"));
    panel.add(nameTextField);
    panel.add(new JLabel("Valor:"));
    panel.add(ATMInput);
    panel.add(new JLabel("Número do Cartão:"));
    panel.add(cardNumberTextField);
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
   * Get the card owner's name.
   */
  public String getSelectedName() {
    return this.nameTextField.getText();
  }

  /**
   * Get the credit card's number.
   */
  public String getSelectedNumber() {
    return this.cardNumberTextField.getText();
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
