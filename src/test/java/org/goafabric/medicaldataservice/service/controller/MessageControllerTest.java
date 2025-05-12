package org.goafabric.medicaldataservice.service.controller;

import org.goafabric.medicaldataservice.service.controller.dto.MedicalRecord;
import org.goafabric.medicaldataservice.service.controller.dto.MedicalRecordType;
import org.goafabric.medicaldataservice.service.controller.dto.Patient;
import org.goafabric.medicaldataservice.service.logic.MedicalRecordLogic;
import org.goafabric.medicaldataservice.service.logic.PatientLogic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @Mock
    private PatientLogic patientLogic;

    @Mock
    private MedicalRecordLogic medicalRecordLogic;

    @InjectMocks
    private MessageController messageController;

    @Test
    void shouldCreatePatient() {
        // Given
        Patient savedPatient = new Patient("1", 1L, "Homer", "Simpson 123456789", "male", LocalDate.of(1978, 5, 12));
        when(patientLogic.save(any(Patient.class))).thenReturn(savedPatient);

        // When
        messageController.createPatient();

        // Then
        verify(patientLogic).save(any(Patient.class));
    }

    @Test
    void shouldCreateObservation() {
        // Given
        String patientId = "1";
        MedicalRecord savedRecord = new MedicalRecord("1", 1L, patientId, "1", MedicalRecordType.OBSERVATION, "Hypertension", "I10");
        when(medicalRecordLogic.save(any(MedicalRecord.class))).thenReturn(savedRecord);

        // When
        messageController.createObservation();

        // Then
        verify(medicalRecordLogic).save(any(MedicalRecord.class));
    }
}
