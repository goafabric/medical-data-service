package org.goafabric.medicaldataservice.persistence.repository;

import org.goafabric.medicaldataservice.persistence.entity.PatientEo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEo, String> {
}
