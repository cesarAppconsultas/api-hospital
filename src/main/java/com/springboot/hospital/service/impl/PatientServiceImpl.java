package com.springboot.hospital.service.impl;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.dto.PatientDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.PatientMapper;
import com.springboot.hospital.model.Cite;
import com.springboot.hospital.model.patient;
import com.springboot.hospital.repository.PatientRepository;
import com.springboot.hospital.service.CiteService;
import com.springboot.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CiteService citeService;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private CitaMapper citaMapper;

    @Override
    public List<PatientDTO> getAllPatients() {
        List<patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PatientDTO> getPatientById(Long id) {
        Optional<patient> optionalPaciente = patientRepository.findById(id);
        return optionalPaciente.map(patientMapper::toDto);
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        patient patient = patientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Optional<patient> optionalPaciente = patientRepository.findById(id);
        if(optionalPaciente.isPresent()){
            patient patient = optionalPaciente.get();
            patient.setName(patientDTO.getName());
            patient.setBirthDate(patientDTO.getBirthDate());
            patient.setDisease(patientDTO.isDisease());

            patient = patientRepository.save(patient);
            return patientMapper.toDto(patient);
        }
        return null;
    }

    @Override
    public void deletePatient(Long id) {
        Optional<patient> optionalPaciente = patientRepository.findById(id);
        if(optionalPaciente.isPresent()){
            patient patient = optionalPaciente.get();

            for(Cite cite : patient.getCites()){
                citeService.deleteCite(cite.getId());
            }

            patientRepository.deleteById(id);
        }
    }

    @Override
    public Collection<CiteDTO> getCitesByPatientId(Long pacienteId) {
        Optional<patient> optionalPaciente = patientRepository.findById(pacienteId);
        return optionalPaciente.map(paciente -> paciente.getCites().stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList()))
                .orElse(null);
    }
}
