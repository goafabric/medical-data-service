package org.goafabric.medicaldataservice.service.extensions;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").withSockJS();
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SubscriptionInterceptor());
    }

    static class SubscriptionInterceptor implements ChannelInterceptor {

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            var accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

            if (accessor != null && accessor.getDestination() != null & StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                System.err.println("## subscribe tenantid: " + accessor.getNativeHeader("X-TenantId"));

                if (accessor.getDestination().contains("/tenant/")) {
                    if (!accessor.getDestination().equals("/tenant/" + TenantContext.getTenantId())) {
                        System.err.println("Accesss to tenant denied");
                        throw new IllegalStateException("Accesss to tenant denied");
                    }
                }
            }

            return message;
        }
    }
}