package org.goafabric.medicaldataservice.fhir;

import org.goafabric.medicaldataservice.service.persistence.entity.MedicalRecordEo;
import org.goafabric.medicaldataservice.service.persistence.entity.PatientAware;
import org.goafabric.medicaldataservice.service.persistence.entity.PatientEo;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class SimpleFhirMapper {
    public Object map(PatientAware patientAware) {
        String className = patientAware.getClass().getSimpleName();
        return switch (className) {
            case "PatientEo" -> mapPatient((PatientEo) patientAware);
            case "MedicalRecordEo" -> {
                String type = ((MedicalRecordEo) patientAware).getType();
                yield switch (type) {
                    case "CONDITION" -> mapCondition((MedicalRecordEo) patientAware);
                    case "OBSERVATION" -> mapObservation((MedicalRecordEo) patientAware);
                    default -> throw new IllegalArgumentException("Unknown MedicalRecordEo type: " + type);
                };
            }
            default -> throw new IllegalArgumentException("Unknown PatientAware type: " + className);
        };

    }

    private Object mapPatient(PatientEo patient) {
        var humanName = Collections.singletonList(new HumanName("private", patient.getFamilyName(), Collections.singletonList(patient.getGivenName())));
        return new Patient(patient.getPatientId(), patient.getGender(), patient.getBirthDate(), humanName);
    }

    private Object mapCondition(MedicalRecordEo medicalRecordEo) {
        return new Condition(medicalRecordEo.getId(), new Coding("", medicalRecordEo.getCode(), medicalRecordEo.getDisplay()));
    }
    private Object mapObservation(MedicalRecordEo medicalRecordEo) {
        return new Observation(medicalRecordEo.getId(), new Coding("", medicalRecordEo.getCode(), medicalRecordEo.getDisplay()));
    }

}
