package org.goafabric.medicaldataservice.fhir;

import java.time.LocalDate;
import java.util.List;

public record Patient(
        String id,
        String gender, // "male", "female", "other", "unknown"
        LocalDate birthDate,
        List<HumanName> name
) {}
