package com.springboot.hospital.service;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.dto.PatientDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<PatientDTO> getAllPatients();

    Optional<PatientDTO> getPatientById(Long id);

    PatientDTO createPatient(PatientDTO patientDTO);

    PatientDTO updatePatient(Long id, PatientDTO patientDTO);

    void deletePatient(Long id);

    Collection<CiteDTO> getCitesByPatientId(Long patientId);
}
