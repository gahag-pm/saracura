package br.ufmg.dcc.pm.saracura.ui.controllers;

import java.awt.Window;

import br.ufmg.dcc.pm.saracura.ui.views.EquipmentRegisterWindow;
import br.ufmg.dcc.pm.saracura.util.ui.WindowUtil;


public class EquipmentRegisterController implements Controller {
  public EquipmentRegisterWindow equipmentRegisterWindow;



  public EquipmentRegisterController(EquipmentRegisterWindow equipmentRegisterWindow) {
    if (equipmentRegisterWindow == null)
      throw new IllegalArgumentException("equipmentRegisterWindow mustn't be null");


    this.equipmentRegisterWindow = equipmentRegisterWindow;
  }



  public void show(Window parent) {
    parent.setVisible(false);

    this.equipmentRegisterWindow.addWindowListener(
      WindowUtil.closeListener(
        (_listener) -> {
          parent.setVisible(true);
          this.equipmentRegisterWindow.removeWindowListener(_listener);
        }
      )
    );
    this.equipmentRegisterWindow.setVisible(true);
  }
}
