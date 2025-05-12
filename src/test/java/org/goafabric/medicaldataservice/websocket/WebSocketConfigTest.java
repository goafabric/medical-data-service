package org.goafabric.medicaldataservice.websocket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebSocketConfigTest {

    @InjectMocks
    private WebSocketConfig webSocketConfig;

    @Test
    void testCustomHandshakeInterceptorBeforeHandshake() throws Exception {
        // Given
        WebSocketConfig.CustomHandshakeInterceptor interceptor = new WebSocketConfig.CustomHandshakeInterceptor();
        ServerHttpRequest request = mock(ServerHttpRequest.class);
        ServerHttpResponse response = mock(ServerHttpResponse.class);
        WebSocketHandler wsHandler = mock(WebSocketHandler.class);
        Map<String, Object> attributes = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-TenantId", "123");
        
        when(request.getHeaders()).thenReturn(headers);

        // When
        boolean result = interceptor.beforeHandshake(request, response, wsHandler, attributes);

        // Then
        assertThat(result).isTrue();
        assertThat(attributes).containsEntry("tenantId", "123");
    }

    @Test
    void testCustomHandshakeInterceptorBeforeHandshakeWithNoTenantId() throws Exception {
        // Given
        WebSocketConfig.CustomHandshakeInterceptor interceptor = new WebSocketConfig.CustomHandshakeInterceptor();
        ServerHttpRequest request = mock(ServerHttpRequest.class);
        ServerHttpResponse response = mock(ServerHttpResponse.class);
        WebSocketHandler wsHandler = mock(WebSocketHandler.class);
        Map<String, Object> attributes = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        
        when(request.getHeaders()).thenReturn(headers);

        // When
        boolean result = interceptor.beforeHandshake(request, response, wsHandler, attributes);

        // Then
        assertThat(result).isTrue();
        assertThat(attributes).containsEntry("tenantId", "0");
    }

    @Test
    void testTenantAuthorizationInterceptorDenySendCommand() {
        // Given
        WebSocketConfig.TenantAuthorizationInterceptor interceptor = new WebSocketConfig.TenantAuthorizationInterceptor();
        
        // Create a real StompHeaderAccessor for SEND command
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.SEND);
        
        // Create a real message with the accessor
        GenericMessage<String> message = new GenericMessage<>("test", accessor.getMessageHeaders());

        // When/Then
        assertThrows(IllegalStateException.class, () -> 
            interceptor.preSend(message, null)
        );
    }

    @Test
    void testTenantAuthorizationInterceptorDenyWrongTenant() {
        // Given
        WebSocketConfig.TenantAuthorizationInterceptor interceptor = new WebSocketConfig.TenantAuthorizationInterceptor();
        
        // Create a real StompHeaderAccessor for SUBSCRIBE command
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.SUBSCRIBE);
        accessor.setDestination("/tenant/456");
        
        // Set session attributes
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("tenantId", "123");
        accessor.setSessionAttributes(sessionAttributes);
        
        // Create a real message with the accessor
        GenericMessage<String> message = new GenericMessage<>("test", accessor.getMessageHeaders());

        // When/Then
        assertThrows(IllegalStateException.class, () -> 
            interceptor.preSend(message, null)
        );
    }

    @Test
    void testTenantAuthorizationInterceptorAllowCorrectTenant() {
        // Given
        WebSocketConfig.TenantAuthorizationInterceptor interceptor = new WebSocketConfig.TenantAuthorizationInterceptor();
        
        // Create a real StompHeaderAccessor for SUBSCRIBE command
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.SUBSCRIBE);
        accessor.setDestination("/tenant/123");
        
        // Set session attributes
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("tenantId", "123");
        accessor.setSessionAttributes(sessionAttributes);
        
        // Create a real message with the accessor
        GenericMessage<String> message = new GenericMessage<>("test", accessor.getMessageHeaders());

        // When
        Object result = interceptor.preSend(message, null);

        // Then
        assertThat(result).isNotNull();
    }
}
