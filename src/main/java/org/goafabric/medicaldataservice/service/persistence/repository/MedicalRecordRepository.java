package org.goafabric.medicaldataservice.service.persistence.repository;

import org.goafabric.medicaldataservice.service.persistence.entity.MedicalRecordEo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecordEo, String> {
}
