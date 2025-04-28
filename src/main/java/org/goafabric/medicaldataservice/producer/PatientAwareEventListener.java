package org.goafabric.medicaldataservice.producer;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.goafabric.medicaldataservice.consumer.EventData;
import org.goafabric.medicaldataservice.service.fhir.SimpleFhirMapper;
import org.goafabric.medicaldataservice.service.extensions.TenantContext;
import org.goafabric.medicaldataservice.service.persistence.entity.PatientAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class PatientAwareEventListener implements ApplicationContextAware {
    private static ApplicationContext context;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private enum DbOperation { CREATE, READ, UPDATE, DELETE }

    @Override
    @SuppressWarnings("java:S2696")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @PostPersist
    public void afterCreate(PatientAware patientAware)  {
        produce(patientAware, DbOperation.CREATE);
    }

    @PostUpdate
    public void afterUpdate(PatientAware patientAware) {
        produce(patientAware, DbOperation.UPDATE);
    }

    @PostRemove
    public void afterDelete(PatientAware patientAware) {
        produce(patientAware, DbOperation.DELETE);
    }

    public void produce(PatientAware patientAware, DbOperation operation) {
        log.info("producer tenant is: {}" , TenantContext.getTenantId());
        var payload = context.getBean(SimpleFhirMapper.class).map(patientAware);
        var topic = payload.getClass().getSimpleName().toLowerCase();
        context.getBean(EventProducer.class).produce("patient", patientAware.getPatientId(),
                new EventData(topic, operation.toString(), payload, TenantContext.getAdapterHeaderMap()));
    }


}
