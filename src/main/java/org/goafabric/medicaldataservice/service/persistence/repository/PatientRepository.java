package org.goafabric.medicaldataservice.service.persistence.repository;

import org.goafabric.medicaldataservice.service.persistence.entity.PatientEo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEo, String> {
}
