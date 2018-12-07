package br.ufmg.dcc.pm.saracura.ui.views;

import br.ufmg.dcc.pm.saracura.clinic.Exam;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;


public class EquipmentRegisterDialog extends JDialog {

    protected int panelWidth = 380;
    protected int panelHeight = 580;
    protected boolean dismissed = false;

    protected final JList<String> exams;
    protected final JList<String> workDays;

    protected JIntegerTextField startTimeHours;
    protected JIntegerTextField startTimeMinutes;

    protected JIntegerTextField dayDurationHours;
    protected JIntegerTextField dayDurationMinutes;

    protected JIntegerTextField appointmentDurationHours;
    protected JIntegerTextField appointmentDurationMinutes;

    protected final Dimension dButton = new Dimension(250, 75);
    protected final JButton confirmButton = new JButton("Confirmar") {{
        setSize(dButton);
        setMaximumSize(dButton);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }};

    protected final JButton cancelButton = new JButton("Cancelar") {{
        setSize(dButton);
        setMaximumSize(dButton);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }};

    public EquipmentRegisterDialog(Window parent) {
        super(parent, "Novo Equipamento", ModalityType.APPLICATION_MODAL);

        var panel = new JPanel(new FlowLayout());

        startTimeHours = new JIntegerTextField(3);
        startTimeMinutes = new JIntegerTextField(3);

        dayDurationHours = new JIntegerTextField(3);
        dayDurationMinutes = new JIntegerTextField(3);

        appointmentDurationHours = new JIntegerTextField(3);
        appointmentDurationMinutes = new JIntegerTextField(3);

        exams = new JList<String>() {{
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }};

        workDays = new JList<String>() {{
            setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }};

        int i = 0;
        String[] items = new String[Exam.values().length];

        for (Exam exam : Exam.values()) {
            items[i] = Exam.textMap.get(exam);
            i++;
        }
        exams.setListData(items);
        exams.setSelectedIndex(0);

        String[] days = new String[]{"Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira",
                "Quinta-feira", "Sexta-feira", "Sábado"};
        workDays.setListData(days);
        workDays.setSelectedIndex(0);


        this.cancelButton.addActionListener(e -> {
            this.dismissed = true;
            this.dispose();
        });

        this.confirmButton.addActionListener(e -> {
            this.dispose();
        });

        panel.setBorder(new EmptyBorder(10, 20, 15, 20));
        panel.add(new JLabel("Escolha os exames realizados pelo equipamento:"));
        panel.add(Box.createRigidArea(new Dimension(panelWidth, 5)));
        panel.add(new JScrollPane(exams));
        panel.add(Box.createRigidArea(new Dimension(panelWidth, 5)));
        panel.add(new JLabel("Escolha os dias de funcionamento do equipamento:"));
        panel.add(new JLabel("(Segure a tecla CTRL para selecionar mais de um)"));
        panel.add(Box.createRigidArea(new Dimension(panelWidth, 5)));
        panel.add(new JScrollPane(workDays));
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
        panel.add(cancelButton);

        this.add(panel);

        this.setMinimumSize(new Dimension(panelWidth,panelHeight));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public String getSelectedExam() {
        return this.exams.getSelectedValue();
    }

    public java.util.List<String> getSelectedWorkdays() {
        return this.workDays.getSelectedValuesList();
    }

    public int getStartTimeHours() {
        return this.startTimeHours.getValue();
    }

    public int getStartTimeMinutes() {
        return this.startTimeMinutes.getValue();
    }

    public int getDayDurationHours() {
        return this.dayDurationHours.getValue();
    }

    public int getDayDurationMinutes() {
        return this.dayDurationMinutes.getValue();
    }

    public int getAppointmentDurationHours() {
        return this.appointmentDurationHours.getValue();
    }

    public int getAppointmentDurationMinutes() {
        return this.appointmentDurationMinutes.getValue();
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) // WINDOW_CLOSING occurs when the user
            this.dismissed = true;                   // presses the X button, but not when
        // setVisible(false) is called.
        super.processWindowEvent(e);
    }

}
