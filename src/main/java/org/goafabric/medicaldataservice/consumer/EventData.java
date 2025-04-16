package org.goafabric.medicaldataservice.consumer;


import org.goafabric.medicaldataservice.service.extensions.TenantContext;

import java.util.Map;

public record EventData(
    String type,
    String operation, //CREATE, UPDATE, DELETE
    Object payload,
    Map<String, String> tenantInfos
) {
    public EventData {
        TenantContext.setContext(tenantInfos); //little hacky, if the object is created on deserialization the tenantcontext will be set
    }
}
