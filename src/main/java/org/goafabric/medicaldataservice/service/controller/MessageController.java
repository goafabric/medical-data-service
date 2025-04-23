package org.goafabric.medicaldataservice.service.controller;

import org.goafabric.medicaldataservice.service.controller.dto.MedicalRecord;
import org.goafabric.medicaldataservice.service.controller.dto.MedicalRecordType;
import org.goafabric.medicaldataservice.service.controller.dto.Patient;
import org.goafabric.medicaldataservice.service.logic.MedicalRecordLogic;
import org.goafabric.medicaldataservice.service.logic.PatientLogic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@MessageMapping("messages")
public class MessageController {
    private final PatientLogic patientLogic;
    private final MedicalRecordLogic medicalRecordLogic;

    public MessageController(PatientLogic patientLogic, MedicalRecordLogic medicalRecordLogic) {
        this.patientLogic = patientLogic;
        this.medicalRecordLogic = medicalRecordLogic;
    }

    @MessageMapping("create-patient")
    public void createPatient() {
        patientLogic.save(new Patient("Homer", "Simpson " + System.currentTimeMillis(), "male", LocalDate.of(1978, 5, 12)));
    }

    @MessageMapping("create-observation")
    public void createObservation() {
        medicalRecordLogic.save(new MedicalRecord(UUID.randomUUID().toString(), "1", MedicalRecordType.CONDITION, "Hypertension", "I10"));
    }


}
