package br.ufmg.dcc.pm.saracura.clinic;

import java.util.LinkedHashMap;
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
    textMap = new LinkedHashMap<Exam, String>() {{
      put(MAMMOGRAPHY, "Mamografia");
      put(NUCLEAR_MEDICINE, "Medicina nuclear");
      put(RADIOGRAPHY, "Radiografia");
      put(MAGNETIC_RESONANCE, "Ressonância magnética");
      put(COMPUTERIZED_TOMOGRAPHY, "Tomografia computarizada");
      put(ULTRASOUND, "Ultrassom");
    }};

    valueMap = textMap.entrySet()
                      .stream()
                      .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
  }
}
