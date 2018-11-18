package br.ufmg.dcc.pm.saracura.util.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.function.Consumer;


public class WindowUtil {
  private WindowUtil() { }

  public static WindowListener closeListener(Consumer<WindowListener> action) {
    return new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        action.accept(this);
      }
    };
  }
}
