package org.goafabric.medicaldataservice.service.fhir;


import java.time.LocalDate;
import java.util.List;

@org.springframework.data.mongodb.core.mapping.Document ("#{@httpInterceptor.getTenantPrefix()}patient")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "#{@httpInterceptor.getTenantPrefix()}patient")
public record Patient(
        String id,
        String gender, // "male", "female", "other", "unknown"
        LocalDate birthDate,
        List<HumanName> name
) {}
