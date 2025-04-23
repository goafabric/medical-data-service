package org.goafabric.medicaldataservice.service.logic;

import jakarta.transaction.Transactional;
import org.goafabric.medicaldataservice.service.controller.dto.Patient;
import org.goafabric.medicaldataservice.service.logic.mapper.PatientMapper;
import org.goafabric.medicaldataservice.service.persistence.repository.PatientRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@Transactional
public class PatientLogic {
    private final PatientRepository repository;
    private final PatientMapper mapper;

    public PatientLogic(PatientRepository repository, PatientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Patient getById(@PathVariable String id) {
        return mapper.map(
                repository.getReferenceById(id));
    }

    public List<Patient> findAll(int page, int size) {
        return mapper.map(
                repository.findAll(PageRequest.of(page, size)));
    }


    public Patient save(@RequestBody Patient patient) {
        return mapper.map(
                repository.save(mapper.map(patient)));
    }

}
