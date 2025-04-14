package org.goafabric.medicaldataservice.logic.mapper;

import org.goafabric.medicaldataservice.controller.dto.Patient;
import org.goafabric.medicaldataservice.persistence.entity.PatientEo;
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
