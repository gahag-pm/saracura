package br.ufmg.dcc.pm.saracura.ui.views;

import br.ufmg.dcc.pm.saracura.clinic.Specialty;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;


public class DoctorRegisterDialog extends JDialog {
    protected int panelWidth = 370;
    protected int panelHeight = 640;
    protected boolean dismissed = false;

    protected JTextField nameField;
    protected JTextField crmField;

    protected JIntegerTextField startTimeHours;
    protected JIntegerTextField startTimeMinutes;

    protected JIntegerTextField dayDurationHours;
    protected JIntegerTextField dayDurationMinutes;

    protected JIntegerTextField appointmentDurationHours;
    protected JIntegerTextField appointmentDurationMinutes;

    protected final JList<String> specialties;
    protected final JList<String> workDays;

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

    public DoctorRegisterDialog(Window parent) {
        super(parent, "Novo Médico", ModalityType.APPLICATION_MODAL);

        var panel = new JPanel(new FlowLayout());

        nameField = new JTextField(20);
        crmField = new JTextField(20);

        startTimeHours = new JIntegerTextField(3);
        startTimeMinutes = new JIntegerTextField(3);

        dayDurationHours = new JIntegerTextField(3);
        dayDurationMinutes = new JIntegerTextField(3);

        appointmentDurationHours = new JIntegerTextField(3);
        appointmentDurationMinutes = new JIntegerTextField(3);

        specialties = new JList<String>() {{
            setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }};

        workDays = new JList<String>() {{
            setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }};

        int i = 0;
        String[] items = new String[Specialty.values().length];

        for (Specialty specialty : Specialty.values()) {
            items[i] = Specialty.textMap.get(specialty);
            i++;
        }
        specialties.setListData(items);
        specialties.setSelectedIndex(0);

        String[] days = new String[]{"Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira",
                "Quinta-feira", "Sexta-feira", "Sabado"};
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
        panel.add(new JLabel("Nome: "));
        panel.add(nameField);
        panel.add(new JLabel("   CRM: "));
        panel.add(crmField);
        panel.add(Box.createRigidArea(new Dimension(panelWidth, 5)));
        panel.add(new JLabel("Escolha as especialidades do médico:"));
        panel.add(new JLabel("(Segure a tecla CTRL para selecionar mais de uma)"));
        panel.add(Box.createRigidArea(new Dimension(panelWidth, 5)));
        panel.add(new JScrollPane(specialties));
        panel.add(Box.createRigidArea(new Dimension(panelWidth, 5)));
        panel.add(new JLabel("Escolha os dias de trabalho do médico:"));
        panel.add(new JLabel("(Segure a tecla CTRL para selecionar mais de um)"));
        panel.add(Box.createRigidArea(new Dimension(panelWidth, 5)));
        panel.add(new JScrollPane(workDays));
        panel.add(Box.createRigidArea(new Dimension(panelWidth, 3)));
        panel.add(new JLabel("Início do expediente:"));
        panel.add(startTimeHours);
        panel.add(new JLabel(":"));
        panel.add(startTimeMinutes);
        panel.add(Box.createRigidArea(new Dimension(panelWidth, 3)));
        panel.add(new JLabel("Duração do expediente:"));
        panel.add(dayDurationHours);
        panel.add(new JLabel("h"));
        panel.add(dayDurationMinutes);
        panel.add(new JLabel("m"));
        panel.add(Box.createRigidArea(new Dimension(panelWidth, 3)));
        panel.add(new JLabel("Duração das consultas:"));
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

    public String getNameFieldContent() {
        return nameField.getText();
    }

    public String getCrmFieldContent() {
        return crmField.getText();
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public java.util.List<String> getSelectedSpecialties() {
        return this.specialties.getSelectedValuesList();
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
