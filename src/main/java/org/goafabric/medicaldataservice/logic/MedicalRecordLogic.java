package org.goafabric.medicaldataservice.logic;

import jakarta.transaction.Transactional;
import org.goafabric.medicaldataservice.controller.dto.MedicalRecord;
import org.goafabric.medicaldataservice.logic.mapper.MedicalRecordMapper;
import org.goafabric.medicaldataservice.persistence.repository.MedicalRecordRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@Transactional
public class MedicalRecordLogic {
    private final MedicalRecordRepository repository;
    private final MedicalRecordMapper mapper;

    public MedicalRecordLogic(MedicalRecordRepository repository, MedicalRecordMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public MedicalRecord getById(@PathVariable String id) {
        return mapper.map(
                repository.getReferenceById(id));
    }

    public List<MedicalRecord> findAll(int page, int size) {
        return mapper.map(
                repository.findAll(PageRequest.of(page, size)));
    }



    public MedicalRecord save(@RequestBody MedicalRecord medicalRecord) {
        return mapper.map(
                repository.save(mapper.map(medicalRecord)));
    }
}
