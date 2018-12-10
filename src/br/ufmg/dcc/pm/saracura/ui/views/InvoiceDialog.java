package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.ufmg.dcc.pm.saracura.clinic.payment.Invoice;


/**
 * Creates a window to show the invoice.
 */
public class InvoiceDialog extends JDialog {
  /**
   * Whether the user dismissed the dialog by pressing the window's close button.
   */
  protected boolean dismissed = false;



  /**
   * Creates an invoice dialog.
   * It is unresizable, dispose on close, positioned at screen center.
   * @param parent the parent window of the dialog
   * @param invoice the invoice.
   */
  public InvoiceDialog(Window parent, Invoice invoice) {
    super(parent, "Recibo", ModalityType.APPLICATION_MODAL);


    JLabel payer = new JLabel("Cliente: " + invoice.payer);
    JLabel separator = new JLabel("------------------------");
    JLabel beneficiary = new JLabel("Beneficiário: " + invoice.beneficiary);
    JLabel nlre = new JLabel("Nlre: " + invoice.nrle);
    JLabel date = new JLabel("Data: " + invoice.date.toString());
    JLabel value = new JLabel("Valor: R$" + invoice.value.toString());

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    panel.add(payer);
    panel.add(beneficiary);
    panel.add(nlre);
    panel.add(date);
    panel.add(separator);
    panel.add(value);
    panel.add(Box.createRigidArea(new Dimension(50, 10)));

    this.add(panel);
    this.pack();
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
   * Whether the user closed the dialog without confirming.
   */
  public boolean getDismissed() {
    return this.dismissed;
  }
}
