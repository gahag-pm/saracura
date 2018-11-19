package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;

import br.ufmg.dcc.pm.saracura.ui.views.EquipmentRegisterDialog;


public class EquipmentRegisterController implements Controller<Void> {
  public EquipmentRegisterDialog equipmentRegisterDialog;



  public EquipmentRegisterController(EquipmentRegisterDialog equipmentRegisterDialog) {
    if (equipmentRegisterDialog == null)
      throw new IllegalArgumentException("equipmentRegisterDialog mustn't be null");


    this.equipmentRegisterDialog = equipmentRegisterDialog;
  }



  public Void execute(Window parent) {
    // TODO.
    this.equipmentRegisterDialog.setVisible(true);
    return null;
  }
}
