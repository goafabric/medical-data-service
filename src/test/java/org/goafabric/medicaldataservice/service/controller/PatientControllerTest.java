package org.goafabric.medicaldataservice.service.controller;

import org.goafabric.medicaldataservice.service.controller.dto.Patient;
import org.goafabric.medicaldataservice.service.logic.PatientLogic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    private PatientLogic logic;

    @InjectMocks
    private PatientController controller;

    @Test
    void shouldGetPatientById() {
        // Given
        String patientId = "123";
        Patient expectedPatient = new Patient("John", "Doe", "male", LocalDate.of(1980, 1, 1));
        when(logic.getById(patientId)).thenReturn(expectedPatient);

        // When
        Patient result = controller.getById(patientId);

        // Then
        assertThat(result).isEqualTo(expectedPatient);
    }

    @Test
    void shouldFindAllPatients() {
        // Given
        int page = 0;
        int size = 10;
        List<Patient> expectedPatients = List.of(
                new Patient("John", "Doe", "male", LocalDate.of(1980, 1, 1)),
                new Patient("Jane", "Smith", "female", LocalDate.of(1985, 5, 15))
        );
        when(logic.findAll(page, size)).thenReturn(expectedPatients);

        // When
        List<Patient> result = controller.findAll(page, size);

        // Then
        assertThat(result).isEqualTo(expectedPatients);
    }

    @Test
    void shouldSavePatient() {
        // Given
        Patient inputPatient = new Patient("John", "Doe", "male", LocalDate.of(1980, 1, 1));
        Patient savedPatient = new Patient("123", 1L, "John", "Doe", "male", LocalDate.of(1980, 1, 1));
        when(logic.save(any(Patient.class))).thenReturn(savedPatient);

        // When
        Patient result = controller.save(inputPatient);

        // Then
        assertThat(result).isEqualTo(savedPatient);
    }
}
