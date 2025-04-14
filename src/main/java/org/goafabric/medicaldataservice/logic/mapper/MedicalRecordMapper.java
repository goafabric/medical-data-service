package org.goafabric.medicaldataservice.logic.mapper;

import org.goafabric.medicaldataservice.controller.dto.MedicalRecord;
import org.goafabric.medicaldataservice.persistence.entity.MedicalRecordEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalRecordMapper {
    MedicalRecord map(MedicalRecordEo value);

    MedicalRecordEo map(MedicalRecord value);

    List<MedicalRecord> map(List<MedicalRecordEo> value);

    List<MedicalRecord> map(Page<MedicalRecordEo> all);
}
