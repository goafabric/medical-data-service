package org.goafabric.medicaldataservice.consumer;

import org.goafabric.medicaldataservice.consumer.aop.TenantAwareKafkaListener;
import org.goafabric.medicaldataservice.service.controller.dto.MySocketMessage;
import org.goafabric.medicaldataservice.service.extensions.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebsocketRelayConsumer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final SimpMessagingTemplate msgTemplate;

    public WebsocketRelayConsumer(SimpMessagingTemplate msgTemplate) {
        this.msgTemplate = msgTemplate;
    }

    @TenantAwareKafkaListener(topics = {"patient"}, containerFactory = "latestKafkaListenerContainerFactory")
    public void process(EventData eventData) {
        log.info("inside relay consumer");
        msgTemplate.convertAndSend("/tenant/" + TenantContext.getTenantId(), new MySocketMessage(eventData.type() + " " + eventData.operation() + " for Tenant " + TenantContext.getTenantId()));
    }

}
