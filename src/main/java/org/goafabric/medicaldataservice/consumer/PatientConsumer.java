package org.goafabric.medicaldataservice.consumer;

import org.goafabric.medicaldataservice.service.persistence.entity.PatientAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PatientConsumer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String CONSUMER_NAME = "PatientConsumer";

    @KafkaListener(groupId = CONSUMER_NAME, topics = {"patient"}) //only topics listed here will be autocreated
    public void processKafka(EventData eventData) {
        //withTenantInfos(() -> process(topic, eventData));
        var patientAware = (PatientAware) eventData.payload();
        log.info("Received message from topic patient {}", patientAware.getClass().getSimpleName());
    }
}
