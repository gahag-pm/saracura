package br.ufmg.dcc.pm.saracura.ui.controls.agenda;

import java.util.EventListener;

public interface AgendaUnscheduledEventListener extends EventListener {
    void agendaUnscheduledClick(AgendaUnscheduledClickEvent e);
}
