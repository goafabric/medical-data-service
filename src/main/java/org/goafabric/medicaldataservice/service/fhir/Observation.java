package org.goafabric.medicaldataservice.service.fhir;

@org.springframework.data.mongodb.core.mapping.Document ("#{@httpInterceptor.getTenantPrefix()}observation")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "#{@httpInterceptor.getTenantPrefix()}observation")
public record Observation(
        String id,
        Coding coding
)
{}
