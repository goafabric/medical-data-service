package org.goafabric.medicaldataservice.service.persistence.extensions;

import jakarta.transaction.Transactional;
import org.goafabric.medicaldataservice.service.controller.dto.MedicalRecord;
import org.goafabric.medicaldataservice.service.controller.dto.MedicalRecordType;
import org.goafabric.medicaldataservice.service.controller.dto.Patient;
import org.goafabric.medicaldataservice.service.logic.MedicalRecordLogic;
import org.goafabric.medicaldataservice.service.logic.PatientLogic;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Transactional
public class DemoDataImporter implements CommandLineRunner {
    private final PatientLogic patientLogic;
    private final MedicalRecordLogic medicalRecordLogic;

    public DemoDataImporter(PatientLogic patientLogic, MedicalRecordLogic medicalRecordLogic, MedicalRecordLogic medicalRecordLogic1) {
        this.patientLogic = patientLogic;
        this.medicalRecordLogic = medicalRecordLogic;
    }


    @Override
    public void run(String... args) {
        var patients = createPatients().stream()
                .map(patientLogic::save)
                .toList();


        patients.forEach(patient -> 
            createMedicalRecords(patient.id()).forEach(medicalRecordLogic::save));
    }

    private List<Patient> createPatients() {
        return List.of(
            new Patient("Homer", "Simpson", "male", LocalDate.of(1978, 5, 12)),
            new Patient("Marge", "Simpson", "female", LocalDate.of(1979, 3, 19)),
            new Patient("Lisa", "Simpson", "female", LocalDate.of(2012, 5, 9)),
            new Patient("Bart", "Simpson", "male", LocalDate.of(2010, 2, 23)),
            new Patient("Maggie", "Simpson", "female", LocalDate.of(2020, 1, 14))
        );
    }

    private List<MedicalRecord> createMedicalRecords(String patientId) {
        var encounterId = "1";
        return List.of(
                new MedicalRecord(patientId, encounterId, MedicalRecordType.CONDITION, "Type 2 Diabetes", "E11"),
                new MedicalRecord(patientId, encounterId, MedicalRecordType.OBSERVATION, "Patient seems to eat to much", "E11"),

                new MedicalRecord(patientId, encounterId, MedicalRecordType.CONDITION, "Hypertension", "I10"),
                new MedicalRecord(patientId, encounterId, MedicalRecordType.OBSERVATION, "Patient complains about dizziness and neck pain", "I10"),

                new MedicalRecord(patientId, encounterId, MedicalRecordType.CONDITION, "Asthma", "J45"),
                new MedicalRecord(patientId, encounterId, MedicalRecordType.OBSERVATION, "Patient experiences shortness of breath during exercise", "J45"),

                new MedicalRecord(patientId, encounterId, MedicalRecordType.CONDITION, "Migraine", "G43"),
                new MedicalRecord(patientId, encounterId, MedicalRecordType.OBSERVATION, "Patient reports severe headache with sensitivity to light", "G43")
        );
    }
}
