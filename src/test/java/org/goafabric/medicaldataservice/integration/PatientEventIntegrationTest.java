package org.goafabric.medicaldataservice.integration;

import org.goafabric.medicaldataservice.consumer.EventData;
import org.goafabric.medicaldataservice.producer.EventProducer;
import org.goafabric.medicaldataservice.producer.PatientAwareEventListener;
import org.goafabric.medicaldataservice.service.controller.dto.Patient;
import org.goafabric.medicaldataservice.service.extensions.TenantContext;
import org.goafabric.medicaldataservice.service.fhir.SimpleFhirMapper;
import org.goafabric.medicaldataservice.service.persistence.entity.PatientEo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientEventIntegrationTest {

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private EventProducer eventProducer;

    @Mock
    private SimpleFhirMapper simpleFhirMapper;

    private PatientAwareEventListener eventListener;
    private PatientEo patientEo;
    private Patient patientDto;

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
        
        patientEo = new PatientEo("patient-123", "John", "Doe", "male", LocalDate.of(1980, 1, 1), 1L);
        patientDto = new Patient("patient-123", 1L, "John", "Doe", "male", LocalDate.of(1980, 1, 1));
        
        when(simpleFhirMapper.map(patientEo)).thenReturn(patientDto);
    }

    @AfterEach
    void tearDown() {
        TenantContext.removeContext();
    }

    @Test
    void shouldProduceEventWithCorrectDataAfterCreate() {
        // Given
        ArgumentCaptor<EventData> eventDataCaptor = ArgumentCaptor.forClass(EventData.class);

        // When
        eventListener.afterCreate(patientEo);

        // Then
        verify(eventProducer).produce(eq("patient"), eq("patient-123"), eventDataCaptor.capture());
        
        EventData capturedEventData = eventDataCaptor.getValue();
        assertThat(capturedEventData.type()).isEqualTo("patient");
        assertThat(capturedEventData.operation()).isEqualTo("CREATE");
        assertThat(capturedEventData.payload()).isEqualTo(patientDto);
        assertThat(capturedEventData.tenantInfos()).containsEntry("X-TenantId", "123");
        assertThat(capturedEventData.tenantInfos()).containsEntry("X-OrganizationId", "456");
    }

    @Test
    void shouldProduceEventWithCorrectDataAfterUpdate() {
        // Given
        ArgumentCaptor<EventData> eventDataCaptor = ArgumentCaptor.forClass(EventData.class);

        // When
        eventListener.afterUpdate(patientEo);

        // Then
        verify(eventProducer).produce(eq("patient"), eq("patient-123"), eventDataCaptor.capture());
        
        EventData capturedEventData = eventDataCaptor.getValue();
        assertThat(capturedEventData.type()).isEqualTo("patient");
        assertThat(capturedEventData.operation()).isEqualTo("UPDATE");
        assertThat(capturedEventData.payload()).isEqualTo(patientDto);
    }

    @Test
    void shouldProduceEventWithCorrectDataAfterDelete() {
        // Given
        ArgumentCaptor<EventData> eventDataCaptor = ArgumentCaptor.forClass(EventData.class);

        // When
        eventListener.afterDelete(patientEo);

        // Then
        verify(eventProducer).produce(eq("patient"), eq("patient-123"), eventDataCaptor.capture());
        
        EventData capturedEventData = eventDataCaptor.getValue();
        assertThat(capturedEventData.type()).isEqualTo("patient");
        assertThat(capturedEventData.operation()).isEqualTo("DELETE");
        assertThat(capturedEventData.payload()).isEqualTo(patientDto);
    }
}
