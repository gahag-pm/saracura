package br.ufmg.dcc.pm.saracura.ui.controls.agenda;

import java.util.EventListener;

public interface AgendaScheduledEventListener extends EventListener {
    void agendaScheduledClick(AgendaScheduledClickEvent e);
}
