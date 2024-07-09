package com.springboot.hospital.mapper;

import com.springboot.hospital.dto.PatientDTO;
import com.springboot.hospital.model.patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public PatientDTO toDto(patient patient){
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setName(patient.getName());
        patientDTO.setBirthDate(patient.getBirthDate());
        patientDTO.setDisease(patient.isDisease());
        return patientDTO;
    }

    public patient toEntity(PatientDTO patientDTO){
        patient patient = new patient();
        patient.setId(patientDTO.getId());
        patient.setName(patientDTO.getName());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setDisease(patientDTO.isDisease());
        return patient;
    }
}
