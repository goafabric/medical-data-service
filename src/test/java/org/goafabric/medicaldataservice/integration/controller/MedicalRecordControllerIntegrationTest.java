package org.goafabric.medicaldataservice.integration.controller;

import org.goafabric.medicaldataservice.service.controller.dto.MedicalRecord;
import org.goafabric.medicaldataservice.service.controller.dto.MedicalRecordType;
import org.goafabric.medicaldataservice.service.logic.MedicalRecordLogic;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Integration tests disabled due to Kafka configuration issues")
class MedicalRecordControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MedicalRecordLogic medicalRecordLogic;

    @Test
    void shouldSaveAndRetrieveMedicalRecord() {
        // Given
        String patientId = UUID.randomUUID().toString();
        MedicalRecord medicalRecord = new MedicalRecord(patientId, "1", MedicalRecordType.OBSERVATION, "Hypertension", "I10");
        
        // When
        MedicalRecord savedRecord = medicalRecordLogic.save(medicalRecord);
        
        // Then
        assertThat(savedRecord.id()).isNotNull();
        assertThat(savedRecord.patientId()).isEqualTo(patientId);
        assertThat(savedRecord.type()).isEqualTo(MedicalRecordType.OBSERVATION);
        assertThat(savedRecord.display()).isEqualTo("Hypertension");
        assertThat(savedRecord.code()).isEqualTo("I10");
        
        // When
        MedicalRecord retrievedRecord = medicalRecordLogic.getById(savedRecord.id());
        
        // Then
        assertThat(retrievedRecord).isNotNull();
        assertThat(retrievedRecord.id()).isEqualTo(savedRecord.id());
        assertThat(retrievedRecord.patientId()).isEqualTo(patientId);
        assertThat(retrievedRecord.type()).isEqualTo(MedicalRecordType.OBSERVATION);
        assertThat(retrievedRecord.display()).isEqualTo("Hypertension");
        assertThat(retrievedRecord.code()).isEqualTo("I10");
    }

    @Test
    void shouldFindAllMedicalRecords() {
        // Given
        String patientId1 = UUID.randomUUID().toString();
        String patientId2 = UUID.randomUUID().toString();
        
        medicalRecordLogic.save(new MedicalRecord(patientId1, "1", MedicalRecordType.OBSERVATION, "Hypertension", "I10"));
        medicalRecordLogic.save(new MedicalRecord(patientId2, "1", MedicalRecordType.CONDITION, "Diabetes", "E11"));
        
        // When
        List<MedicalRecord> records = medicalRecordLogic.findAll(0, 10);
        
        // Then
        assertThat(records).isNotNull();
        assertThat(records.size()).isGreaterThanOrEqualTo(2);
        
        // Verify that our saved records are in the list
        boolean foundHypertension = false;
        boolean foundDiabetes = false;
        
        for (MedicalRecord record : records) {
            if ("Hypertension".equals(record.display()) && "I10".equals(record.code())) {
                foundHypertension = true;
            }
            if ("Diabetes".equals(record.display()) && "E11".equals(record.code())) {
                foundDiabetes = true;
            }
        }
        
        assertThat(foundHypertension).isTrue();
        assertThat(foundDiabetes).isTrue();
    }
}
