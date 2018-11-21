package br.ufmg.dcc.pm.saracura.clinic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public enum Exam {
  COMPUTERIZED_TOMOGRAPHY,
  MAGNETIC_RESONANCE,
  RADIOGRAPHY,
  MAMMOGRAPHY,
  NUCLEAR_MEDICINE,
  ULTRASOUND;

  public static final Map<Exam, String> textMap;

  static {
    textMap = Collections.unmodifiableMap(
      new HashMap<Exam, String>() {{
        put(MAMMOGRAPHY, "Mamografia");
        put(NUCLEAR_MEDICINE, "Medicina nuclear");
        put(RADIOGRAPHY, "Radiografia");
        put(MAGNETIC_RESONANCE, "Ressonância magnética");
        put(COMPUTERIZED_TOMOGRAPHY, "Tomografia computarizada");
        put(ULTRASOUND, "Ultrassom");
      }}
    );
  }
}
