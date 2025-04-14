package org.goafabric.medicaldataservice.controller.dto;

import java.time.LocalDate;

public record Patient(
    String id,
    Long version,
    String givenName,
    String familyName,

    String gender,
    LocalDate birthDate

) {
    public Patient(String givenName, String familyName, String gender, LocalDate birthDate) {
        this(null, null, givenName, familyName, gender, birthDate);
    }
}
