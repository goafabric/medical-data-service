package org.goafabric.medicaldataservice.service.controller.dto;

public record MedicalRecord (
        String id,
        Long version,
        String patientId,
        String encounterId,
        MedicalRecordType type,
        String display,
        String code) {
    public MedicalRecord(String patientId, String encounterId, MedicalRecordType type, String display, String code) {
        this(null, null, patientId, encounterId, type, display, code);
    }

}


