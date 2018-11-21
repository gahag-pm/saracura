package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.stream.Collectors;

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
public class ListPickDialog<T> extends JDialog {
  /**
   * The items listed - listing string : Listed object
   */
  protected Map<String, T> items;

  /**
   * Whether the user dismissed the dialog by pressing the window's close button.
   */
  protected boolean dismissed = false;

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
   * @param title  the dialog's title
   * @param items  the mapping of items to strings, mustn't be null or empty
   */
  public ListPickDialog(Window parent, String title, Map<T, String> items) {
    super(parent, title, ModalityType.APPLICATION_MODAL);


    if (items == null)
      throw new IllegalArgumentException("items mustn't be null");

    if (items.isEmpty())
      throw new IllegalArgumentException("items mustn't be empty");

    this.items = items.entrySet().stream().collect(
      Collectors.toUnmodifiableMap(Map.Entry::getValue, Map.Entry::getKey)
    );


    this.list.setListData(this.items.keySet().stream().sorted().toArray(String[]::new));
    this.list.setSelectedIndex(0);

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



  @Override
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) // WINDOW_CLOSING occurs when the user
      this.dismissed = true;                     // presses the X button, but not when
                                                 // setVisible(false) is called.
    super.processWindowEvent(e);
  }


  @Override
  public void setVisible(boolean b) {
    this.dismissed = false;

    super.setVisible(b);
  }


  /**
   * Get the selected item. Returns null if the list has no items.
   */
  public T getSelected() {
    return this.items.get(this.list.getSelectedValue());
  }


  /**
   * Whether the user closed the dialog without confirming.
   */
  public boolean getDismissed() {
    return this.dismissed;
  }
}
