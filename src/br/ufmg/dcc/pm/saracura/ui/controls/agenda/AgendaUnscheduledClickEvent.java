package br.ufmg.dcc.pm.saracura.ui.controls.agenda;

import java.awt.AWTEvent;
import java.time.LocalDateTime;

public class AgendaUnscheduledClickEvent extends AWTEvent {
    
    private LocalDateTime dateTime;

    public AgendaUnscheduledClickEvent(Object source, LocalDateTime dateTime) {
        super(source, 0);
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
