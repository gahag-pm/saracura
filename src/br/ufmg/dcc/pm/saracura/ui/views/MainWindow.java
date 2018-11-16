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

  protected JButton examAppointment = new JButton("Agendar Exame de Imagem") {{
    setSize(dButton);
    setMaximumSize(dButton);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected JButton scheduleAppointment = new JButton("Marcar Consulta") {{
    setSize(dButton);
    setMaximumSize(dButton);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected JButton update = new JButton("Atualizar Cadastro") {{
    setSize(dButton);
    setMaximumSize(dButton);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};



  /**
   * Creates main window.
   */
  public MainWindow() {
    super("SaraCura");

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBorder(new EmptyBorder(20, 50, 20, 50));
    panel.add(scheduleAppointment);
    panel.add(Box.createRigidArea(new Dimension(10,10)));
    panel.add(examAppointment);
    panel.add(Box.createRigidArea(new Dimension(10,10)));
    panel.add(update);
    this.add(panel);

    this.getRootPane().setDefaultButton(scheduleAppointment);
    this.setResizable(false);
    this.rootPane.setOpaque(true);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
