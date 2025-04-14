package org.goafabric.medicaldataservice.controller.dto;

public enum MedicalRecordType {

    OBSERVATION("OBSERVATION"),
    CONDITION("CONDITION"),
    CHARGEITEM("CHARGE"),
    FINDING("FINDING"),
    THERAPY("THERAPY"),
    BODY_METRICS("BODY_METRICS");
    
    private final String value;

    MedicalRecordType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
