package br.ufmg.dcc.pm.saracura.ui.controls.agenda;

import java.awt.AWTEvent;

public class AgendaScheduledClickEvent extends AWTEvent {

    private AgendaEvent agendaEvent;

    public AgendaScheduledClickEvent(Object source, AgendaEvent agendaEvent) {
        super(source, 0);
        this.agendaEvent = agendaEvent;
    }

    public AgendaEvent getAgendaEvent() {
        return agendaEvent;
    }
}
