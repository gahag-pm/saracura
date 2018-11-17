package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import br.ufmg.dcc.pm.saracura.clinic.Specialty;


public class SpecialtyPickWindow extends JFrame {
  protected final Dimension dButton = new Dimension(250, 75);

  protected final JButton select = new JButton("Selecionar") {{
    setSize(dButton);
    setMaximumSize(dButton);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected JList <String> list = new JList<String>() {{
    setListData(Specialty.textMap.values().stream().sorted().toArray(String[]::new));
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    setSelectedIndex(0);
  }};

  protected JScrollPane listScroller = new JScrollPane(this.list);



  public SpecialtyPickWindow(){
    super("Especialidades médicas");

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    panel.add(listScroller);
    panel.add(Box.createRigidArea(new Dimension(10, 15)));
    panel.add(select);
    this.add(panel);

    this.pack();
    this.setSize(new Dimension(400, 500));
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  }

}
