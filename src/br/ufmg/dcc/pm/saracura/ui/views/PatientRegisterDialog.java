package br.ufmg.dcc.pm.saracura.ui.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;


public class PatientRegisterDialog extends JDialog {

    protected boolean dismissed = false;

    protected JLabel nameLabel;
    protected JTextField nameField;
    protected JLabel ninLabel;
    protected JTextField ninField;


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



    public PatientRegisterDialog(Window parent) {
        super(parent, "Novo Paciente", ModalityType.APPLICATION_MODAL);

        var panel = new JPanel(new FlowLayout());
        nameLabel = new JLabel("Nome: ");
        nameField = new JTextField(20);
        ninLabel = new JLabel("    CPF: ");
        ninField = new JTextField(20);

        this.cancelButton.addActionListener(e -> {
            this.dismissed = true;
            this.dispose();
        });

        this.confirmButton.addActionListener(e -> {
            this.dispose();
        });

        panel.setBorder(new EmptyBorder(10, 20, 15, 20));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ninLabel);
        panel.add(ninField);
        panel.add(Box.createRigidArea(new Dimension(340, 5)));
        panel.add(confirmButton);
        panel.add(cancelButton);

        this.add(panel);

        this.setMinimumSize(new Dimension(340,140));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public String getNameFieldContent() {
        return nameField.getText();
    }

    public String getNinFieldContent() {
        return ninField.getText();
    }

    public boolean isDismissed() {
        return dismissed;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) // WINDOW_CLOSING occurs when the user
            this.dismissed = true;                   // presses the X button, but not when
                                                     // setVisible(false) is called.
        super.processWindowEvent(e);
    }
}
