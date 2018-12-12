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
  protected final Dimension buttonDimension = new Dimension(250, 75);

  protected JButton scheduleAppointmentButton = new JButton("Marcar Consulta") {{
    setSize(buttonDimension);
    setMaximumSize(buttonDimension);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected JButton scheduleExamButton = new JButton("Agendar Exame de Imagem") {{
    setSize(buttonDimension);
    setMaximumSize(buttonDimension);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected JButton patientRegisterButton = new JButton("Adicionar Paciente") {{
    setSize(buttonDimension);
    setMaximumSize(buttonDimension);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected JButton doctorRegisterButton = new JButton("Adicionar Médico") {{
    setSize(buttonDimension);
    setMaximumSize(buttonDimension);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected JButton equipmentRegisterButton = new JButton("Adicionar equipamento") {{
    setSize(buttonDimension);
    setMaximumSize(buttonDimension);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected JButton exitButton = new JButton("Sair") {{
    setSize(buttonDimension);
    setMaximumSize(buttonDimension);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};



  /**
   * Creates a main window.
   * It is unresizable, exit on close, positioned at screen center.
   */
  public MainWindow() {
    super("SaraCura");

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBorder(new EmptyBorder(20, 50, 20, 50));
    panel.add(this.scheduleAppointmentButton);
    panel.add(Box.createRigidArea(new Dimension(10,10)));
    panel.add(this.scheduleExamButton);
    panel.add(Box.createRigidArea(new Dimension(10,10)));
    panel.add(this.patientRegisterButton);
    panel.add(Box.createRigidArea(new Dimension(10,10)));
    panel.add(this.doctorRegisterButton);
    panel.add(Box.createRigidArea(new Dimension(10,10)));
    panel.add(this.equipmentRegisterButton);
    panel.add(Box.createRigidArea(new Dimension(10,10)));
    panel.add(this.exitButton);
    this.add(panel);

    this.getRootPane().setDefaultButton(this.scheduleAppointmentButton);
    this.setResizable(false);
    this.rootPane.setOpaque(true);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }



  /**
   * Add an action to the schedule appointment button.
   */
  public void addAppointmentAction(Runnable action) {
    if (action == null)
      throw new IllegalArgumentException("action mustn't be null");

    this.scheduleAppointmentButton.addActionListener(e -> action.run());
  }

  /**
   * Add an action to the schedule exam button.
   */
  public void addExamAction(Runnable action) {
    if (action == null)
      throw new IllegalArgumentException("action mustn't be null");

    this.scheduleExamButton.addActionListener(e -> action.run());
  }

  /**
   * Add an action to the register patient button.
   */
  public void addPatientRegisterAction(Runnable action) {
    if (action == null)
      throw new IllegalArgumentException("action mustn't be null");

    this.patientRegisterButton.addActionListener(e -> action.run());
  }

  /**
   * Add an action to the register doctor button.
   */
  public void addDoctorRegisterAction(Runnable action) {
    if (action == null)
      throw new IllegalArgumentException("action mustn't be null");

    this.doctorRegisterButton.addActionListener(e -> action.run());
  }

  /**
   * Add an action to the register equipment button.
   */
  public void addEquipmentRegisterAction(Runnable action) {
    if (action == null)
      throw new IllegalArgumentException("action mustn't be null");

    this.equipmentRegisterButton.addActionListener(e -> action.run());
  }
  
  /**
   * Add an action to the exit button.
   */
  public void addExitAction(Runnable action) {
    if (action == null)
      throw new IllegalArgumentException("action mustn't be null");

    this.exitButton.addActionListener(e -> action.run());
  }
}
