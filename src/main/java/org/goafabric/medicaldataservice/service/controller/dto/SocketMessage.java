package org.goafabric.medicaldataservice.service.controller.dto;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;


@RegisterReflectionForBinding(SocketMessage.class)
public record SocketMessage(
    String message
) {}
