package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;

import br.ufmg.dcc.pm.saracura.clinic.Exam;


public class EquipmentRegisterDialog extends JDialog {
  /**
   * The exams listed - listing string : Exam
   */
  protected static final Map<String, Exam> exams =
    Exam.textMap.entrySet().stream().collect(
      Collectors.toUnmodifiableMap(Map.Entry::getValue, Map.Entry::getKey)
    );

  /**
   * The work days listed - listing string : DayOfWeek
   */
  protected static final Map<String, DayOfWeek> workDays = new LinkedHashMap<>() {{
    put("Domingo"      , DayOfWeek.SUNDAY);
    put("Segunda-feira", DayOfWeek.MONDAY);
    put("Terça-feira"  , DayOfWeek.TUESDAY);
    put("Quarta-feira" , DayOfWeek.WEDNESDAY);
    put("Quinta-feira" , DayOfWeek.THURSDAY);
    put("Sexta-feira"  , DayOfWeek.FRIDAY);
    put("Sabado"       , DayOfWeek.SATURDAY);
  }};


  protected boolean dismissed = false;

  protected final int panelWidth = 380;
  protected final int panelHeight = 580;

  protected final JIntegerTextField startTimeHours = new JIntegerTextField(3);
  protected final JIntegerTextField startTimeMinutes = new JIntegerTextField(3);

  protected final JIntegerTextField dayDurationHours = new JIntegerTextField(3);
  protected final JIntegerTextField dayDurationMinutes = new JIntegerTextField(3);

  protected final JIntegerTextField appointmentDurationHours = new JIntegerTextField(3);
  protected final JIntegerTextField appointmentDurationMinutes = new JIntegerTextField(3);

  protected final JList<String> examsList = new JList<String>() {{
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }};
  protected final JList<String> workDaysList = new JList<String>() {{
    setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
  }};

  protected final Dimension dButton = new Dimension(250, 75);

  protected final JButton confirmButton = new JButton("Confirmar") {{
    setSize(dButton);
    setMaximumSize(dButton);
    setAlignmentX(Component.CENTER_ALIGNMENT);
  }};

  protected ActionListener confirmButtonAction = e -> this.setVisible(false);



  public EquipmentRegisterDialog(Window parent) {
    super(parent, "Novo Equipamento", ModalityType.APPLICATION_MODAL);


    this.confirmButton.addActionListener(e -> this.confirmButtonAction.actionPerformed(e));

    this.examsList.setListData(exams.keySet().stream().toArray(String[]::new));
    this.examsList.setSelectedIndex(0);

    this.workDaysList.setListData(workDays.keySet().stream().toArray(String[]::new));
    this.workDaysList.setSelectedIndex(0);

    var panel = new JPanel(new FlowLayout());
    panel.setBorder(new EmptyBorder(10, 20, 15, 20));
    panel.add(new JLabel("Escolha os exames realizados pelo equipamento:"));
    panel.add(Box.createRigidArea(new Dimension(panelWidth, 5)));
    panel.add(new JScrollPane(examsList));
    panel.add(Box.createRigidArea(new Dimension(panelWidth, 5)));
    panel.add(new JLabel("Escolha os dias de funcionamento do equipamento:"));
    panel.add(new JLabel("(Segure a tecla CTRL para selecionar mais de um)"));
    panel.add(Box.createRigidArea(new Dimension(panelWidth, 5)));
    panel.add(new JScrollPane(workDaysList));
    panel.add(Box.createRigidArea(new Dimension(panelWidth, 3)));
    panel.add(new JLabel("Início do funcionamento:"));
    panel.add(startTimeHours);
    panel.add(new JLabel(":"));
    panel.add(startTimeMinutes);
    panel.add(Box.createRigidArea(new Dimension(panelWidth, 3)));
    panel.add(new JLabel("Duração do funcionamento:"));
    panel.add(dayDurationHours);
    panel.add(new JLabel("h"));
    panel.add(dayDurationMinutes);
    panel.add(new JLabel("m"));
    panel.add(Box.createRigidArea(new Dimension(panelWidth, 3)));
    panel.add(new JLabel("Duração dos exames:"));
    panel.add(appointmentDurationHours);
    panel.add(new JLabel("h"));
    panel.add(appointmentDurationMinutes);
    panel.add(new JLabel("m"));
    panel.add(Box.createRigidArea(new Dimension(panelWidth, 3)));
    panel.add(confirmButton);
    this.add(panel);


    this.setMinimumSize(new Dimension(panelWidth,panelHeight));
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
   * Get the selected exams.
   * @return the selected exam, or null if not selected
   */
  public Exam getSelectedExam() {
    var selected = this.examsList.getSelectedValue();
    return selected == null ? null
                            : exams.get(selected);
  }

  /**
   * Get the selected work days.
   */
  public Set<DayOfWeek> getSelectedWorkDays() {
    List<String> values = this.workDaysList.getSelectedValuesList();
    return values.stream().map(wd -> workDays.get(wd)).collect(Collectors.toSet());
  }

  /**
   * Get the selected start time
   * @return the selected time, or null if it is invalid
   */
  public LocalTime getSelectedStartTime() {
    try {
      return LocalTime.of(
        this.startTimeHours.getValue(),
        this.startTimeMinutes.getValue()
      );
    }
    catch (DateTimeException e) {
      return null;
    }
  }

  /**
   * Get the selected day duration.
   * @return the selected duration, or null if it is invalid
   */
  public Duration getSelectedDayDuration() {
    int hours = this.dayDurationHours.getValue();
    int minutes = this.dayDurationMinutes.getValue();

    if (hours > 23 || hours < 0 || minutes > 59 || minutes < 0)
      return null;

    return Duration.ofHours(hours).plus(Duration.ofMinutes(minutes));
  }

  /**
   * Get the selected appointment duration.
   * @return the selected duration, or null if it is invalid
   */
  public Duration getSelectedAppointmentDuration() {
    int hours = this.appointmentDurationHours.getValue();
    int minutes = this.appointmentDurationMinutes.getValue();

    if (hours > 23 || hours < 0 || minutes > 59 || minutes < 0)
      return null;

    return Duration.ofHours(hours).plus(Duration.ofMinutes(minutes));
  }

  /**
   * Whether the user closed the dialog without confirming.
   */
  public boolean getDismissed() {
    return dismissed;
  }
}
