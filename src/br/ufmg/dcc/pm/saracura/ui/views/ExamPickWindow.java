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
  JPanel pane = new JPanel();
  protected final Dimension dButton = new Dimension(250, 75);
  protected JButton select = new JButton("Selecionar"){
        {
          setSize(dButton);
          setMaximumSize(dButton);
        }
    };
  
  protected JList list;

  protected String[] exams = (Exam.getMap()).values().toArray(new String[0]);

  public ExamPickWindow(){
    
    super("Exames");
    list = new JList(exams);
    pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
    pane.setBorder(new EmptyBorder(20, 50, 20, 50));
    select.setAlignmentX(Component.CENTER_ALIGNMENT);
    pane.add(list);
    pane.add(Box.createRigidArea(new Dimension(10,10)));
    pane.add(select);

    this.add(pane);
    this.pack();
    this.rootPane.setOpaque(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
}
