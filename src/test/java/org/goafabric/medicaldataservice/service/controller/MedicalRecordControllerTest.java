package org.goafabric.medicaldataservice.service.controller;

import org.goafabric.medicaldataservice.service.controller.dto.MedicalRecord;
import org.goafabric.medicaldataservice.service.controller.dto.MedicalRecordType;
import org.goafabric.medicaldataservice.service.logic.MedicalRecordLogic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRecordControllerTest {

    @Mock
    private MedicalRecordLogic logic;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @Test
    void shouldGetMedicalRecordById() {
        // Given
        String recordId = "123";
        String patientId = UUID.randomUUID().toString();
        MedicalRecord expectedRecord = new MedicalRecord(recordId, 1L, patientId, "1", MedicalRecordType.OBSERVATION, "Hypertension", "I10");
        
        when(logic.getById(recordId)).thenReturn(expectedRecord);

        // When
        MedicalRecord result = medicalRecordController.getById(recordId);

        // Then
        assertThat(result).isEqualTo(expectedRecord);
        verify(logic).getById(recordId);
    }

    @Test
    void shouldFindAllMedicalRecords() {
        // Given
        int page = 0;
        int size = 10;
        String patientId1 = UUID.randomUUID().toString();
        String patientId2 = UUID.randomUUID().toString();
        
        List<MedicalRecord> expectedRecords = List.of(
            new MedicalRecord("123", 1L, patientId1, "1", MedicalRecordType.OBSERVATION, "Hypertension", "I10"),
            new MedicalRecord("456", 1L, patientId2, "1", MedicalRecordType.CONDITION, "Diabetes", "E11")
        );
        
        when(logic.findAll(page, size)).thenReturn(expectedRecords);

        // When
        List<MedicalRecord> result = medicalRecordController.findAll(page, size);

        // Then
        assertThat(result).isEqualTo(expectedRecords);
        verify(logic).findAll(page, size);
    }

    @Test
    void shouldSaveMedicalRecord() {
        // Given
        String patientId = UUID.randomUUID().toString();
        MedicalRecord inputRecord = new MedicalRecord(patientId, "1", MedicalRecordType.OBSERVATION, "Hypertension", "I10");
        MedicalRecord savedRecord = new MedicalRecord("123", 1L, patientId, "1", MedicalRecordType.OBSERVATION, "Hypertension", "I10");
        
        when(logic.save(inputRecord)).thenReturn(savedRecord);

        // When
        MedicalRecord result = medicalRecordController.save(inputRecord);

        // Then
        assertThat(result).isEqualTo(savedRecord);
        verify(logic).save(inputRecord);
    }
}
