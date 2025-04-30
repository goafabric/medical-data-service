package org.goafabric.medicaldataservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketIntegrationTest {

    private static final String WS_URI = "ws://localhost:8080/websocket"; // adjust if needed
    private static final String DESTINATION = "/tenant/0";

    @Test
    void shouldReceiveMessageOnSubscription() throws Exception {
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe(DESTINATION, new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return String.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        blockingQueue.offer((String) payload);
                    }
                });
            }
        };

        var future = stompClient.connect(WS_URI, new WebSocketHttpHeaders(), sessionHandler);
        StompSession session = future.get(5, TimeUnit.SECONDS);

        // Send a test message (adjust as needed)
        session.send(DESTINATION, "Hello from test");

        String message = blockingQueue.poll(5, TimeUnit.SECONDS);
        assertThat(message).isEqualTo("Hello from test");
    }
}
