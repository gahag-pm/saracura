package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.util.Collection;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;


/**
 * A dialog with a list of items and a select button.
 */
public class ListPickDialog extends JDialog {
  protected final Dimension dButton = new Dimension(250, 75);

  protected final JButton selectButton = new JButton("Selecionar") {{
    setSize(dButton);
    setMaximumSize(dButton);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected final JList<String> list = new JList<String>() {{
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }};



  /**
   * Create a list pick dialog.
   * @param parent the parent window of the dialog
   */
  public ListPickDialog(Window parent) {
    super(parent, ModalityType.APPLICATION_MODAL);


    this.selectButton.addActionListener(e -> this.setVisible(false));

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    panel.add(new JScrollPane(list));
    panel.add(Box.createRigidArea(new Dimension(10, 15)));
    panel.add(selectButton);
    this.add(panel);

    this.pack();
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }



  /**
   * Set the list items.
   * @param items  the items of the list, mustn't be null nor empty.
   */
  public void setItems(Collection<String> items) {
    if (items == null)
      throw new IllegalArgumentException("items mustn't be null");

    if (items.isEmpty())
      throw new IllegalArgumentException("items mustn't be empty");

    this.list.setListData(new Vector<String>(items));
    this.list.setSelectedIndex(0);

    this.pack(); // Resize window to accommodate the items width.
  }


  /**
   * Get the selected item. Returns null if the list has no items.
   */
  public String getSelected() {
    return this.list.getSelectedValue();
  }
}
