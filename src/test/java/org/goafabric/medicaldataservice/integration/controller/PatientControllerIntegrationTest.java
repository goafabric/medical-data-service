package org.goafabric.medicaldataservice.integration.controller;

import org.goafabric.medicaldataservice.service.controller.dto.Patient;
import org.goafabric.medicaldataservice.service.logic.PatientLogic;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Integration tests disabled due to Kafka configuration issues")
class PatientControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private PatientLogic patientLogic;

    @Test
    void shouldSaveAndRetrievePatient() {
        // Given
        Patient patient = new Patient("John", "Doe", "male", LocalDate.of(1980, 1, 1));
        
        // When
        Patient savedPatient = patientLogic.save(patient);
        
        // Then
        assertThat(savedPatient.id()).isNotNull();
        assertThat(savedPatient.givenName()).isEqualTo("John");
        assertThat(savedPatient.familyName()).isEqualTo("Doe");
        
        // When
        Patient retrievedPatient = patientLogic.getById(savedPatient.id());
        
        // Then
        assertThat(retrievedPatient).isNotNull();
        assertThat(retrievedPatient.id()).isEqualTo(savedPatient.id());
        assertThat(retrievedPatient.givenName()).isEqualTo("John");
        assertThat(retrievedPatient.familyName()).isEqualTo("Doe");
        assertThat(retrievedPatient.gender()).isEqualTo("male");
        assertThat(retrievedPatient.birthDate()).isEqualTo(LocalDate.of(1980, 1, 1));
    }

    @Test
    void shouldFindAllPatients() {
        // Given
        patientLogic.save(new Patient("John", "Doe", "male", LocalDate.of(1980, 1, 1)));
        patientLogic.save(new Patient("Jane", "Smith", "female", LocalDate.of(1985, 5, 15)));
        
        // When
        List<Patient> patients = patientLogic.findAll(0, 10);
        
        // Then
        assertThat(patients).isNotNull();
        assertThat(patients.size()).isGreaterThanOrEqualTo(2);
        
        // Verify that our saved patients are in the list
        boolean foundJohn = false;
        boolean foundJane = false;
        
        for (Patient p : patients) {
            if ("John".equals(p.givenName()) && "Doe".equals(p.familyName())) {
                foundJohn = true;
            }
            if ("Jane".equals(p.givenName()) && "Smith".equals(p.familyName())) {
                foundJane = true;
            }
        }
        
        assertThat(foundJohn).isTrue();
        assertThat(foundJane).isTrue();
    }
}
