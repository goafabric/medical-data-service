package org.goafabric.medicaldataservice.producer;

import org.goafabric.medicaldataservice.consumer.EventData;
import org.goafabric.medicaldataservice.service.extensions.TenantContext;
import org.goafabric.medicaldataservice.service.fhir.SimpleFhirMapper;
import org.goafabric.medicaldataservice.service.persistence.entity.PatientAware;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientAwareEventListenerTest {

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private EventProducer eventProducer;

    @Mock
    private SimpleFhirMapper simpleFhirMapper;

    @Mock
    private PatientAware patientAware;

    private PatientAwareEventListener eventListener;

    @BeforeEach
    void setUp() {
        eventListener = new PatientAwareEventListener();
        eventListener.setApplicationContext(applicationContext);
        
        when(applicationContext.getBean(EventProducer.class)).thenReturn(eventProducer);
        when(applicationContext.getBean(SimpleFhirMapper.class)).thenReturn(simpleFhirMapper);
        
        TenantContext.setContext(Map.of(
                "X-TenantId", "123",
                "X-OrganizationId", "456",
                "X-Auth-Request-Preferred-Username", "testuser"
        ));
    }

    @AfterEach
    void tearDown() {
        TenantContext.removeContext();
    }

    @Test
    void shouldProduceEventAfterCreate() {
        // Given
        String patientId = "patient-123";
        Object mappedPayload = new Object();
        when(patientAware.getPatientId()).thenReturn(patientId);
        when(simpleFhirMapper.map(patientAware)).thenReturn(mappedPayload);

        // When
        eventListener.afterCreate(patientAware);

        // Then
        verify(simpleFhirMapper).map(patientAware);
        verify(eventProducer).produce(
                eq("patient"), 
                eq(patientId), 
                any(EventData.class)
        );
    }

    @Test
    void shouldProduceEventAfterUpdate() {
        // Given
        String patientId = "patient-123";
        Object mappedPayload = new Object();
        when(patientAware.getPatientId()).thenReturn(patientId);
        when(simpleFhirMapper.map(patientAware)).thenReturn(mappedPayload);

        // When
        eventListener.afterUpdate(patientAware);

        // Then
        verify(simpleFhirMapper).map(patientAware);
        verify(eventProducer).produce(
                eq("patient"), 
                eq(patientId), 
                any(EventData.class)
        );
    }

    @Test
    void shouldProduceEventAfterDelete() {
        // Given
        String patientId = "patient-123";
        Object mappedPayload = new Object();
        when(patientAware.getPatientId()).thenReturn(patientId);
        when(simpleFhirMapper.map(patientAware)).thenReturn(mappedPayload);

        // When
        eventListener.afterDelete(patientAware);

        // Then
        verify(simpleFhirMapper).map(patientAware);
        verify(eventProducer).produce(
                eq("patient"), 
                eq(patientId), 
                any(EventData.class)
        );
    }
}
