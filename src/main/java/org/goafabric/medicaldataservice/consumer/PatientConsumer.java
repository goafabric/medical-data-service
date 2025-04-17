package org.goafabric.medicaldataservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.goafabric.medicaldataservice.service.persistence.entity.MedicalRecordEo;
import org.goafabric.medicaldataservice.service.persistence.entity.PatientEo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PatientConsumer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String CONSUMER_NAME = "PatientConsumer";

    @KafkaListener(groupId = CONSUMER_NAME, topics = {"patient"}) //only topics listed here will be autocreated, for production TOPICS should be created via Terraform
    public void processKafka(EventData eventData) {
        //withTenantInfos(() -> process(topic, eventData));

        if ("patient".equals(eventData.type())) {
            var patient = getPayLoad(eventData, PatientEo.class);
            log.info("Received message from type patient {}", patient.getGivenName());
        }

        if ("observation".equals(eventData.type())) {
            var medicalRecord = getPayLoad(eventData, MedicalRecordEo.class);
            log.info("Received message from type observation {}", medicalRecord.getCode());
        }

        if ("condition".equals(eventData.type())) {
            var medicalRecord = getPayLoad(eventData, MedicalRecordEo.class);
            log.info("Received message from type condition {}", medicalRecord.getCode());
        }
    }

    private <T> T getPayLoad(EventData eventData, Class<T> clazz) {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.convertValue(eventData.payload(), clazz);
    }
}
