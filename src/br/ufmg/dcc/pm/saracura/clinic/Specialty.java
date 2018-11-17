package br.ufmg.dcc.pm.saracura.clinic;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


public enum Specialty {
  ACUPUNCTURE,
  ALLERGY_AND_IMMUNOLOGY,
  ANESTHESIOLOGY,
  ANGIOLOGY,
  CARDIOLOGY,
  CARDIOVASCULAR_SURGERY,
  CLINICAL_PATHOLOGY,
  COLOPROCTOLOGY,
  DERMATOLOGY,
  DIGESTIVE_SYSTEM_SURGERY,
  EMERGENCY_MEDICINE,
  ENDOCRINOLOGY_AND_METABOLISM,
  ENDOSCOPY,
  FAMILY_AND_COMMUNITY_MEDICINE,
  GASTROENTEROLOGY,
  GENERAL_SURGERY,
  GERIATRICS,
  GYNECOLOGY_AND_OBSTETRICS,
  INTERNAL_MEDICINE,
  HAND_SURGERY,
  HEAD_AND_NECK_SURGERY,
  HEMATOLOGY_AND_HEMOTHERAPY,
  HOMEOPATHY,
  INFECTOLOGY,
  INTENSIVE_MEDICINE,
  LEGAL_MEDICINE,
  MASTOLOGY,
  MEDICAL_GENETICS,
  MEDICINE_OF_TRAFFIC,
  NEPHROLOGY,
  NEUROLOGY,
  NEUROSURGERY,
  NUCLEAR_MEDICINE,
  NUTROLOGY,
  OBSTETRICS,
  OCCUPATIONAL_MEDICINE,
  ONCOLOGY,
  OPHTHALMOLOGY,
  ORTHOPEDICS_AND_TRAUMATOLOGY,
  OTORHINOLARYNGOLOGY,
  PATHOLOGY,
  PEDIATRIC_SURGERY,
  PEDIATRICS,
  PHYSICAL_MEDICINE,
  PLASTIC_SURGERY,
  PNEUMOLOGY,
  PREVENTIVE_AND_SOCIAL_MEDICINE,
  PSYCHIATRY,
  RADIOLOGY_AND_DIAGNOSTIC_IMAGING,
  RADIOTHERAPY,
  RHEUMATOLOGY,
  SPORTS_MEDICINE,
  THORACIC_SURGERY,
  UROLOGY,
  VASCULAR_SURGERY;

  public static final Map<Specialty, String> textMap;
  public static final Map<String, Specialty> valueMap;

  static {
    textMap = new LinkedHashMap<Specialty, String>() {{
      put(ACUPUNCTURE, "Acupuntura");
      put(ANGIOLOGY, "Angiologia");
      put(HEAD_AND_NECK_SURGERY, "Cirurgia de cabeça e pescoço");
      put(GENERAL_SURGERY, "Cirurgia geral");
      put(PEDIATRIC_SURGERY, "Cirurgia pediátrica");
      put(PLASTIC_SURGERY, "Cirurgia plástica");
      put(INTERNAL_MEDICINE, "Clínica médica");
      put(DERMATOLOGY, "Dermatologia");
      put(ENDOSCOPY, "Endoscopia");
      put(GYNECOLOGY_AND_OBSTETRICS, "Ginecologia e obstetrícia");
      put(INFECTOLOGY, "Infectologia");
      put(MASTOLOGY, "Mastologia");
      put(FAMILY_AND_COMMUNITY_MEDICINE, "Medicina de família e comunidade");
      put(OCCUPATIONAL_MEDICINE, "Medicina do trabalho");
      put(MEDICINE_OF_TRAFFIC, "Medicina do tráfego");
      put(SPORTS_MEDICINE, "Medicina esportiva");
      put(PHYSICAL_MEDICINE, "Medicina física e reabilitação");
      put(NEUROLOGY, "Neurologia");
      put(NUTROLOGY, "Nutrologia");
      put(OPHTHALMOLOGY, "Oftalmologia");
      put(OTORHINOLARYNGOLOGY, "Otorrinolaringologia");
      put(PATHOLOGY, "Patologia");
      put(UROLOGY, "Urologia");
      put(ALLERGY_AND_IMMUNOLOGY, "Alergia e imunologia");
      put(ANESTHESIOLOGY, "Anestesiologia");
      put(CARDIOLOGY, "Cardiologia");
      put(CARDIOVASCULAR_SURGERY, "Cirurgia cardiovascular");
      put(HAND_SURGERY, "Cirurgia da mão");
      put(DIGESTIVE_SYSTEM_SURGERY, "Cirurgia do aparelho digestivo");
      put(THORACIC_SURGERY, "Cirurgia torácica");
      put(VASCULAR_SURGERY, "Cirurgia vascular");
      put(COLOPROCTOLOGY, "Coloproctologia");
      put(ENDOCRINOLOGY_AND_METABOLISM, "Endocrinologia e metabologia");
      put(GASTROENTEROLOGY, "Gastroenterologia");
      put(MEDICAL_GENETICS, "Genética médica");
      put(GERIATRICS, "Geriatria");
      put(HEMATOLOGY_AND_HEMOTHERAPY, "Hematologia e hemoterapia");
      put(HOMEOPATHY, "Homeopatia");
      put(EMERGENCY_MEDICINE, "Medicina de emergência");
      put(LEGAL_MEDICINE, "Medicina forense");
      put(INTENSIVE_MEDICINE, "Medicina intensiva");
      put(NUCLEAR_MEDICINE, "Medicina nuclear");
      put(PREVENTIVE_AND_SOCIAL_MEDICINE, "Medicina preventiva e social");
      put(NEPHROLOGY, "Nefrologia");
      put(NEUROSURGERY, "Neurocirurgia");
      put(OBSTETRICS, "Obstetrícia");
      put(ONCOLOGY, "Oncologia");
      put(ORTHOPEDICS_AND_TRAUMATOLOGY, "Ortopedia e traumatologia");
      put(CLINICAL_PATHOLOGY, "Patologia clínica");
      put(PEDIATRICS, "Pediatria");
      put(PNEUMOLOGY, "Pneumologia");
      put(PSYCHIATRY, "Psiquiatria");
      put(RADIOLOGY_AND_DIAGNOSTIC_IMAGING, "Radiologia e diagnóstico por imagem");
      put(RADIOTHERAPY, "Radioterapia");
      put(RHEUMATOLOGY, "Reumatologia");
    }};

    valueMap = textMap.entrySet()
                      .stream()
                      .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
  }
}
