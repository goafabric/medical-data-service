package org.goafabric.medicaldataservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.goafabric.medicaldataservice.service.persistence.entity.PatientEo;
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

        if (eventData.type().equals("PatientEo")) {
            var patient = getPayLoad(eventData, PatientEo.class);
            log.info("Received message from type patient {}", patient);
        }
    }

    private <T> T getPayLoad(EventData eventData, Class<T> clazz) {
        return new ObjectMapper().convertValue(eventData.payload(), clazz);
    }
}
