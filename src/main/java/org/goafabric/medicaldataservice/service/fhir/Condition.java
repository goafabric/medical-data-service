package org.goafabric.medicaldataservice.service.fhir;

@org.springframework.data.mongodb.core.mapping.Document ("#{@httpInterceptor.getTenantPrefix()}condition")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "#{@httpInterceptor.getTenantPrefix()}condition")
public record Condition(
        String id,
        Coding coding
)
{}
