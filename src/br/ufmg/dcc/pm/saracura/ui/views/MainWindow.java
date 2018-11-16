package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame {
  protected final Dimension dButton = new Dimension(250, 75);
  
  protected JButton examAppointment = new JButton("Agendar Exame de Imagem"){
        {
          setSize(dButton);
          setMaximumSize(dButton);
        }
    };

  protected JButton scheduleAppointment = new JButton("Marcar Consulta")
    {
        {
          setSize(dButton);
          setMaximumSize(dButton);
        }
    };
  
  protected JButton update = new JButton("Atualizar Cadastro"){
        {
          setSize(dButton);
          setMaximumSize(dButton);
        }
    };

  

  /**
   * Creates main window.
   */
  public MainWindow() {
    super("SaraCura");
    
    JPanel Tpanel = new JPanel();    
    Tpanel.setLayout(new BoxLayout(Tpanel, BoxLayout.PAGE_AXIS));
    Tpanel.setBorder(new EmptyBorder(20, 50, 20, 50));
    scheduleAppointment.setAlignmentX(Component.CENTER_ALIGNMENT);    
    examAppointment.setAlignmentX(Component.CENTER_ALIGNMENT);    
    update.setAlignmentX(Component.CENTER_ALIGNMENT);    

    Tpanel.add(scheduleAppointment);
    Tpanel.add(Box.createRigidArea(new Dimension(10,10)));
    Tpanel.add(examAppointment);
    Tpanel.add(Box.createRigidArea(new Dimension(10,10)));
    Tpanel.add(update);
    this.add(Tpanel);
    
    getRootPane().setDefaultButton(scheduleAppointment);
    this.setResizable(false);
    this.rootPane.setOpaque(true);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
