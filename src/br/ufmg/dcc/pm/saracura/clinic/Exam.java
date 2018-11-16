package br.ufmg.dcc.pm.saracura.clinic;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public enum Exam {
  COMPUTERIZED_TOMOGRAPHY,
  MAGNETIC_RESONANCE,
  RADIOGRAPHY,
  MAMMOGRAPHY,
  NUCLEAR_MEDICINE,
  ULTRASOUND;

  public static final Map<Exam, String> textMap;
  public static final Map<String, Exam> valueMap;

  static {
    textMap = new HashMap<Exam, String>() {{
      put(COMPUTERIZED_TOMOGRAPHY, "TOMOGRAFIA COMPUTARIZADA");
      put(MAGNETIC_RESONANCE, "RESSONÂNCIA MAGNÉTICA");
      put(RADIOGRAPHY, "RADIOGRAFIA");
      put(MAMMOGRAPHY, "MAMOGRAFIA");
      put(NUCLEAR_MEDICINE, "MEDICINA NUCLEAR");
      put(ULTRASOUND, "ULTRASSOM");
    }};

    valueMap = textMap.entrySet()
                      .stream()
                      .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
  }
}
