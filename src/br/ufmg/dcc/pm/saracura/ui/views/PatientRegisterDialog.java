package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;


public class PatientRegisterDialog extends JDialog {
  /**
   * Whether the user dismissed the dialog by pressing the window's close button.
   */
  protected boolean dismissed = false;

  protected final JTextField nameTextField = new JTextField(20);
  protected final JTextField ninTextField = new JTextField(20);


  protected final Dimension dButton = new Dimension(250, 75);
  protected final JButton confirmButton = new JButton("Confirmar") {{
    setSize(dButton);
    setMaximumSize(dButton);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected ActionListener confirmButtonAction = e -> this.setVisible(false);



  public PatientRegisterDialog(Window parent) {
    super(parent, "Novo Paciente", ModalityType.APPLICATION_MODAL);


    this.confirmButton.addActionListener(e -> this.confirmButtonAction.actionPerformed(e));

    var panel = new JPanel(new FlowLayout());
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    panel.add(new JLabel("Nome: "));
    panel.add(nameTextField);
    panel.add(new JLabel("    CPF: "));
    panel.add(ninTextField);
    panel.add(Box.createRigidArea(new Dimension(340, 5)));
    panel.add(confirmButton);
    this.add(panel);


    this.setMinimumSize(new Dimension(340,140));
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
   * Get the patient's name.
   */
  public String getSelectedName() {
    return nameTextField.getText();
  }

  /**
   * Get the patient's NIN.
   */
  public String getSelectedNin() {
    return ninTextField.getText();
  }

  /**
   * Whether the user closed the dialog without confirming.
   */
  public boolean getDismissed() {
    return dismissed;
  }
}
