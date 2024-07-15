package com.springboot.hospital.mapper;

import com.springboot.hospital.dto.PatientDTO;
import com.springboot.hospital.model.patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDto(patient patient);

    patient toEntity(PatientDTO patientDTO);
}
