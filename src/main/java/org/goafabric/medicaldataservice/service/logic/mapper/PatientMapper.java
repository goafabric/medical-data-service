package org.goafabric.medicaldataservice.service.logic.mapper;

import org.goafabric.medicaldataservice.service.controller.dto.Patient;
import org.goafabric.medicaldataservice.service.persistence.entity.PatientEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {
    Patient map(PatientEo value);

    PatientEo map(Patient value);

    List<Patient> map(List<PatientEo> value);

    List<Patient> map(Page<PatientEo> all);
}
