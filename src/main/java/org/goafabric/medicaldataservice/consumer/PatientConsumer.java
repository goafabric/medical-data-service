package org.goafabric.medicaldataservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.goafabric.medicaldataservice.consumer.aop.TenantAwareKafkaListener;
import org.goafabric.medicaldataservice.service.fhir.Condition;
import org.goafabric.medicaldataservice.service.fhir.Observation;
import org.goafabric.medicaldataservice.service.fhir.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatientConsumer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String CONSUMER_NAME = "PatientConsumer";

    @Autowired(required = false)
    private MongoTemplate mongoTemplate;

    @Autowired(required = false)
    private ElasticsearchTemplate elasticsearchTemplate;

    @TenantAwareKafkaListener(groupId = CONSUMER_NAME, topics = {"patient"}) //only topics listed here will be autocreated, for production TOPICS should be created via Terraform
    public void processKafka(EventData eventData) {
        if ("patient".equals(eventData.type())) {
            var patient = getPayLoad(eventData, Patient.class);
            log.info("Received message from type patient {}", patient.name().getFirst().family());
            store(patient);
        }

        if ("condition".equals(eventData.type())) {
            var condition = getPayLoad(eventData, Condition.class);
            log.info("Received message from type condition {}", condition.coding().code());
            store(condition);
        }

        if ("observation".equals(eventData.type())) {
            var observation = getPayLoad(eventData, Observation.class);
            log.info("Received message from type observation {}", observation.coding().code());
            store(observation);
        }
    }

    private <T> T getPayLoad(EventData eventData, Class<T> clazz) {
        return objectMapper().convertValue(eventData.payload(), clazz);
    }

    private void store(Object object) {
        Optional.ofNullable(mongoTemplate).ifPresent(mongoTemplate -> mongoTemplate.save(object));
        Optional.ofNullable(elasticsearchTemplate).ifPresent(elasticsearchTemplate -> elasticsearchTemplate.save(object));
    }

    @Bean
    private ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
