package org.goafabric.medicaldataservice.service.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.goafabric.medicaldataservice.producer.PatientAwareEventListener;
import org.hibernate.annotations.TenantId;

import java.time.LocalDate;

@Entity
@Table(name = "patient")
@EntityListeners(PatientAwareEventListener.class)
public class PatientEo implements PatientAware {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @TenantId
    private String organizationId;

    private String givenName;

    private String familyName;

    private String gender;

    public LocalDate birthDate;

    @Version //optimistic locking
    private Long version;

    PatientEo() {}

    public PatientEo(String id, String givenName, String familyName, String gender, LocalDate birthDate, Long version) {
        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    @JsonIgnore
    public String getPatientId() {
        return id;
    }
}
