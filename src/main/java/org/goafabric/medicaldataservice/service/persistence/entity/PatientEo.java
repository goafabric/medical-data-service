package org.goafabric.medicaldataservice.service.persistence.entity;

import jakarta.persistence.*;
import org.goafabric.medicaldataservice.service.persistence.extensions.KafkaListener;
import org.hibernate.annotations.TenantId;

import java.time.LocalDate;

@Entity
@Table(name = "patient")
@EntityListeners(KafkaListener.class)
public class PatientEo {
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

    private PatientEo() {}

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
}
