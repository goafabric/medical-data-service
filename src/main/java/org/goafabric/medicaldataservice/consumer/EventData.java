package org.goafabric.medicaldataservice.consumer;


import org.goafabric.medicaldataservice.service.extensions.TenantContext;

import java.util.Map;

public record EventData(
    Map<String, String> tenantInfos,
    String referenceId,
    String operation,
    Object payload
) {
    public EventData {
        TenantContext.setContext(tenantInfos); //little hacky, if the object is created on deserialization the tenantcontext will be set
    }
}
