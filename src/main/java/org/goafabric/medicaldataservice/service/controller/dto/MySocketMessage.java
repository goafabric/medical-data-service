package org.goafabric.medicaldataservice.service.controller.dto;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;


@RegisterReflectionForBinding(MySocketMessage.class)
public record MySocketMessage(
    String message
) {}
