package org.goafabric.medicaldataservice.consumer;

import org.goafabric.medicaldataservice.service.controller.dto.MySocketMessage;
import org.goafabric.medicaldataservice.service.extensions.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebsocketRelayConsumer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String CONSUMER_NAME = "WebsocketRelayConsumer";

    private final SimpMessagingTemplate msgTemplate;

    public WebsocketRelayConsumer(SimpMessagingTemplate msgTemplate) {
        this.msgTemplate = msgTemplate;
    }


    @KafkaListener(groupId = CONSUMER_NAME, topics = {"patient"}) //, containerFactory = "latestKafkaListenerContainerFactory")
    public void listen(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, EventData eventData) {
        log.info("inside relay consumer");
        msgTemplate.convertAndSend("/tenant/" + TenantContext.getTenantId(), new MySocketMessage(eventData.type() + " " + eventData.operation() + " for Tenant " + TenantContext.getTenantId()));
    }

}
