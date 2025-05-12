package org.goafabric.medicaldataservice.service.logic;

import org.goafabric.medicaldataservice.service.controller.dto.Patient;
import org.goafabric.medicaldataservice.service.logic.mapper.PatientMapper;
import org.goafabric.medicaldataservice.service.persistence.entity.PatientEo;
import org.goafabric.medicaldataservice.service.persistence.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientLogicTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientLogic patientLogic;

    @Test
    void shouldGetPatientById() {
        // Given
        String patientId = "123";
        PatientEo patientEo = new PatientEo(patientId, "John", "Doe", "male", LocalDate.of(1980, 1, 1), 1L);
        Patient expectedPatient = new Patient(patientId, 1L, "John", "Doe", "male", LocalDate.of(1980, 1, 1));
        
        when(patientRepository.getReferenceById(patientId)).thenReturn(patientEo);
        when(patientMapper.map(patientEo)).thenReturn(expectedPatient);

        // When
        Patient result = patientLogic.getById(patientId);

        // Then
        assertThat(result).isEqualTo(expectedPatient);
        verify(patientRepository).getReferenceById(patientId);
        verify(patientMapper).map(patientEo);
    }

    @Test
    void shouldFindAllPatients() {
        // Given
        int page = 0;
        int size = 10;
        PatientEo patientEo1 = new PatientEo("123", "John", "Doe", "male", LocalDate.of(1980, 1, 1), 1L);
        PatientEo patientEo2 = new PatientEo("456", "Jane", "Smith", "female", LocalDate.of(1985, 5, 15), 1L);
        Page<PatientEo> patientEoPage = new PageImpl<>(List.of(patientEo1, patientEo2));
        
        List<Patient> expectedPatients = List.of(
            new Patient("123", 1L, "John", "Doe", "male", LocalDate.of(1980, 1, 1)),
            new Patient("456", 1L, "Jane", "Smith", "female", LocalDate.of(1985, 5, 15))
        );
        
        when(patientRepository.findAll(PageRequest.of(page, size))).thenReturn(patientEoPage);
        when(patientMapper.map(patientEoPage)).thenReturn(expectedPatients);

        // When
        List<Patient> result = patientLogic.findAll(page, size);

        // Then
        assertThat(result).isEqualTo(expectedPatients);
        verify(patientRepository).findAll(PageRequest.of(page, size));
        verify(patientMapper).map(patientEoPage);
    }

    @Test
    void shouldSavePatient() {
        // Given
        Patient inputPatient = new Patient(null, null, "John", "Doe", "male", LocalDate.of(1980, 1, 1));
        PatientEo inputPatientEo = new PatientEo(null, "John", "Doe", "male", LocalDate.of(1980, 1, 1), null);
        PatientEo savedPatientEo = new PatientEo("123", "John", "Doe", "male", LocalDate.of(1980, 1, 1), 1L);
        Patient expectedPatient = new Patient("123", 1L, "John", "Doe", "male", LocalDate.of(1980, 1, 1));
        
        when(patientMapper.map(inputPatient)).thenReturn(inputPatientEo);
        when(patientRepository.save(inputPatientEo)).thenReturn(savedPatientEo);
        when(patientMapper.map(savedPatientEo)).thenReturn(expectedPatient);

        // When
        Patient result = patientLogic.save(inputPatient);

        // Then
        assertThat(result).isEqualTo(expectedPatient);
        verify(patientMapper).map(inputPatient);
        verify(patientRepository).save(inputPatientEo);
        verify(patientMapper).map(savedPatientEo);
    }
}
