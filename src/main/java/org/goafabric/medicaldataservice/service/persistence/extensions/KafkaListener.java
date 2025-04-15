package org.goafabric.medicaldataservice.service.persistence.extensions;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.goafabric.medicaldataservice.service.persistence.entity.MedicalRecordEo;
import org.goafabric.medicaldataservice.service.persistence.entity.PatientEo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class KafkaListener implements ApplicationContextAware {
    private static ApplicationContext context;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private enum DbOperation { CREATE, READ, UPDATE, DELETE }

    @Override
    @SuppressWarnings("java:S2696")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @PostPersist
    public void afterCreate(Object object)  {
        switch (object) {
            case PatientEo patient -> log.info("Patient {} has been created", patient.getId());
            case MedicalRecordEo record -> log.info("Medical Record {} has been created", record.getPatientId());
            default -> {}
        }
        //insertAudit(DbOperation.CREATE,  getId(object), null, object);
    }

    @PostUpdate
    public void afterUpdate(Object object) {

    }

    @PostRemove
    public void afterDelete(Object object) {
        //insertAudit(DbOperation.DELETE, getId(object), object, null);
    }
}
