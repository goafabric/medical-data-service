package org.goafabric.medicaldataservice.producer;

import org.goafabric.medicaldataservice.consumer.EventData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventProducerTest {

    @Mock
    private KafkaTemplate<String, EventData> kafkaTemplate;

    @InjectMocks
    private EventProducer eventProducer;

    @Test
    void shouldProduceEvent() {
        // Given
        String topic = "test-topic";
        String key = "test-key";
        EventData eventData = new EventData("test-type", "CREATE", "test-payload", Map.of("X-TenantId", "1"));

        // When
        eventProducer.produce(topic, key, eventData);

        // Then
        verify(kafkaTemplate).send(topic, key, eventData);
    }
}
