package org.goafabric.medicaldataservice.fhir;

public record Coding(
        String system,
        String code,
        String display
) {}