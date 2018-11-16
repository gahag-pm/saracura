package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.ufmg.dcc.pm.saracura.clinic.Exam;


public class ExamPickWindow extends JFrame {
  protected final Dimension dButton = new Dimension(250, 75);

  protected final JButton select = new JButton("Selecionar") {{
    setSize(dButton);
    setMaximumSize(dButton);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected JList<String> list = new JList<String>(
    Exam.textMap.values().toArray(new String[0])
  );



  public ExamPickWindow(){
    super("Exames");

    JPanel pane = new JPanel();
    pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
    pane.setBorder(new EmptyBorder(20, 50, 20, 50));
    pane.add(list);
    pane.add(Box.createRigidArea(new Dimension(10,10)));
    pane.add(select);
    this.add(pane);

    this.pack();
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  }
}
