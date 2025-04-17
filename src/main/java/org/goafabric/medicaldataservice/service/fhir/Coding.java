package org.goafabric.medicaldataservice.service.fhir;

public record Coding(
        String system,
        String code,
        String display
) {}