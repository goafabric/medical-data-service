package org.goafabric.medicaldataservice.service.extensions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .addInterceptors(new CustomHandshakeInterceptor())
                .withSockJS();
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SubscriptionInterceptor());
    }

    static class SubscriptionInterceptor implements ChannelInterceptor {
        private final Logger log = LoggerFactory.getLogger(this.getClass());


        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            var accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

            if (accessor != null && accessor.getDestination() != null & StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                String tenantId = (String) accessor.getSessionAttributes().get("tenantId");
                log.info("## subscribe to tenant: {}", tenantId);

                if (accessor.getDestination().contains("/tenant/")) {
                    if (!accessor.getDestination().equals("/tenant/" + tenantId)) {
                        log.error("Access to tenant denied: {}", accessor.getDestination());
                        throw new IllegalStateException("Access to tenant denied");
                    }
                }
            }

            return message;
        }
    }

    static class CustomHandshakeInterceptor implements HandshakeInterceptor {

        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            String tenantId = request.getHeaders().getFirst("X-TenantId");
            attributes.put("tenantId", tenantId);
            return true;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

        }
    }
}