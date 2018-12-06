package br.ufmg.dcc.pm.saracura.ui.views;
import java.awt.BorderLayout;
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
import javax.swing.text.AbstractDocument;

import br.ufmg.dcc.pm.saracura.ui.controls.money.CreditCardTextField;
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
  protected JLabel value;
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
    super(parent, "Pagamento em cheque", ModalityType.APPLICATION_MODAL);    
    
    nameLabel = new JLabel("Nome:      ");
    nameField = new JTextField(14);
    regLabel = new JLabel("Registro: ");
    regField = new JTextField(14);
    ((AbstractDocument) regField.getDocument()).setDocumentFilter(new CreditCardTextField());
    value = new JLabel("Valor:       ");
    
    this.cancelButton.addActionListener(e -> {
        this.dismissed = true;
        this.dispose();
      });

    this.confirmButton.addActionListener(e -> {
        this.dispose();
      });

    var panelButtons = new JPanel();
    var panelFields = new JPanel();
    var panel = new JPanel(new BorderLayout());

    panelFields.setLayout(new FlowLayout(FlowLayout.LEFT));
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    panelFields.add(nameLabel);
    panelFields.add(nameField);
    panelFields.add(Box.createRigidArea(new Dimension(340, 5)));
    panelFields.add(regLabel);
    panelFields.add(regField);
    panelFields.add(Box.createRigidArea(new Dimension(340, 5)));
    panelFields.add(value);
    panelFields.add(ATMInput);
    panelFields.add(Box.createRigidArea(new Dimension(340, 5)));
    panelButtons.add(confirmButton);
    panelButtons.add(cancelButton);

    panelButtons.setSize(new Dimension(300, 75));
    panel.add(panelFields);
    panel.add(panelButtons, BorderLayout.SOUTH);

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
   * Get the patient's name.
   */
  public String getNameFieldContent() {
    return this.nameField.getText();
  }

  /**
   * Get the patient's plan registration number.
   */
  public String getRegFieldContent() {
    return this.regField.getText();
  }

  /**
   * Get the money input.
   */
  public BigDecimal getValue() {
    return this.ATMInput.getValue();
  }

  /**
   * Whether the user closed the dialog without confirming.
   */
  public boolean getDismissed() {
    return this.dismissed;
  }
}
