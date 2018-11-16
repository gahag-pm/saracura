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

  public static Map<Exam, String> getMap(){
    Map<String, Exam> map = new HashMap<String, Exam>();
    map.put("TOMOGRAFIA COMPUTARIZADA", COMPUTERIZED_TOMOGRAPHY);
    map.put("RESSONÂNCIA MAGNÉTICA", MAGNETIC_RESONANCE);
    map.put("RADIOGRAFIA", RADIOGRAPHY);
    map.put("MAMOGRAFIA", MAMMOGRAPHY);
    map.put("MEDICINA NUCLEAR", NUCLEAR_MEDICINE);
    map.put("ULTRASSOM", ULTRASOUND);

    Map<Exam, String> mapInversed =
      map.entrySet()
      .stream()
      .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    return mapInversed;
  }
}
