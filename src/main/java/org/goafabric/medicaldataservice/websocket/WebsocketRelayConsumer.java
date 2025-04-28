package org.goafabric.medicaldataservice.websocket;

import org.goafabric.medicaldataservice.consumer.EventData;
import org.goafabric.medicaldataservice.service.controller.dto.MySocketMessage;
import org.goafabric.medicaldataservice.service.extensions.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebsocketRelayConsumer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final SimpMessagingTemplate msgTemplate;

    public WebsocketRelayConsumer(SimpMessagingTemplate msgTemplate) {
        this.msgTemplate = msgTemplate;
    }

    @KafkaListener(topics = {"patient"}, containerFactory = "latestKafkaListenerContainerFactory")
    public void process(EventData eventData) {
        log.info("inside relay consumer");
        msgTemplate.convertAndSend("/tenant/" + TenantContext.getTenantId(), //this works as long as the TenantContext is set by TenantAspect
                new MySocketMessage(eventData.type() + " " + eventData.operation() + " for Tenant " + TenantContext.getTenantId()));
    }

}
