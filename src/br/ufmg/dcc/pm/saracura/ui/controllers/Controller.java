package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;


/**
 * A controller for a data model T.
 */
public interface Controller<T> {
  /**
   * Execute the controller, producing the data model.
   * @param parent the window of the parent control
   */
  public T execute(Window parent);
}
