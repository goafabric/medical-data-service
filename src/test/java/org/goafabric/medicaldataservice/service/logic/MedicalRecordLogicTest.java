package org.goafabric.medicaldataservice.service.logic;

import org.goafabric.medicaldataservice.service.controller.dto.MedicalRecord;
import org.goafabric.medicaldataservice.service.controller.dto.MedicalRecordType;
import org.goafabric.medicaldataservice.service.logic.mapper.MedicalRecordMapper;
import org.goafabric.medicaldataservice.service.persistence.entity.MedicalRecordEo;
import org.goafabric.medicaldataservice.service.persistence.repository.MedicalRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRecordLogicTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private MedicalRecordMapper medicalRecordMapper;

    @InjectMocks
    private MedicalRecordLogic medicalRecordLogic;

    @Test
    void shouldGetMedicalRecordById() {
        // Given
        String recordId = "123";
        String patientId = UUID.randomUUID().toString();
        MedicalRecordEo recordEo = new MedicalRecordEo(recordId, "1", patientId, "OBSERVATION", "Hypertension", "I10", "General", 1L);
        MedicalRecord expectedRecord = new MedicalRecord(recordId, 1L, patientId, "1", MedicalRecordType.OBSERVATION, "Hypertension", "I10");
        
        when(medicalRecordRepository.getReferenceById(recordId)).thenReturn(recordEo);
        when(medicalRecordMapper.map(recordEo)).thenReturn(expectedRecord);

        // When
        MedicalRecord result = medicalRecordLogic.getById(recordId);

        // Then
        assertThat(result).isEqualTo(expectedRecord);
        verify(medicalRecordRepository).getReferenceById(recordId);
        verify(medicalRecordMapper).map(recordEo);
    }

    @Test
    void shouldFindAllMedicalRecords() {
        // Given
        int page = 0;
        int size = 10;
        String patientId1 = UUID.randomUUID().toString();
        String patientId2 = UUID.randomUUID().toString();
        
        MedicalRecordEo recordEo1 = new MedicalRecordEo("123", "1", patientId1, "OBSERVATION", "Hypertension", "I10", "General", 1L);
        MedicalRecordEo recordEo2 = new MedicalRecordEo("456", "1", patientId2, "CONDITION", "Diabetes", "E11", "Endocrinology", 1L);
        Page<MedicalRecordEo> recordEoPage = new PageImpl<>(List.of(recordEo1, recordEo2));
        
        List<MedicalRecord> expectedRecords = List.of(
            new MedicalRecord("123", 1L, patientId1, "1", MedicalRecordType.OBSERVATION, "Hypertension", "I10"),
            new MedicalRecord("456", 1L, patientId2, "1", MedicalRecordType.CONDITION, "Diabetes", "E11")
        );
        
        when(medicalRecordRepository.findAll(PageRequest.of(page, size))).thenReturn(recordEoPage);
        when(medicalRecordMapper.map(recordEoPage)).thenReturn(expectedRecords);

        // When
        List<MedicalRecord> result = medicalRecordLogic.findAll(page, size);

        // Then
        assertThat(result).isEqualTo(expectedRecords);
        verify(medicalRecordRepository).findAll(PageRequest.of(page, size));
        verify(medicalRecordMapper).map(recordEoPage);
    }

    @Test
    void shouldSaveMedicalRecord() {
        // Given
        String patientId = UUID.randomUUID().toString();
        MedicalRecord inputRecord = new MedicalRecord(patientId, "1", MedicalRecordType.OBSERVATION, "Hypertension", "I10");
        MedicalRecordEo inputRecordEo = new MedicalRecordEo(null, "1", patientId, "OBSERVATION", "Hypertension", "I10", "General", null);
        MedicalRecordEo savedRecordEo = new MedicalRecordEo("123", "1", patientId, "OBSERVATION", "Hypertension", "I10", "General", 1L);
        MedicalRecord expectedRecord = new MedicalRecord("123", 1L, patientId, "1", MedicalRecordType.OBSERVATION, "Hypertension", "I10");
        
        when(medicalRecordMapper.map(inputRecord)).thenReturn(inputRecordEo);
        when(medicalRecordRepository.save(inputRecordEo)).thenReturn(savedRecordEo);
        when(medicalRecordMapper.map(savedRecordEo)).thenReturn(expectedRecord);

        // When
        MedicalRecord result = medicalRecordLogic.save(inputRecord);

        // Then
        assertThat(result).isEqualTo(expectedRecord);
        verify(medicalRecordMapper).map(inputRecord);
        verify(medicalRecordRepository).save(inputRecordEo);
        verify(medicalRecordMapper).map(savedRecordEo);
    }
}
