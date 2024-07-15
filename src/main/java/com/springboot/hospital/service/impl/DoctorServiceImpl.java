package com.springboot.hospital.service.impl;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.dto.DoctorDTO;
import com.springboot.hospital.mapper.CiteMapper;
import com.springboot.hospital.mapper.DoctorMapper;
import com.springboot.hospital.model.Cite;
import com.springboot.hospital.model.Doctor;
import com.springboot.hospital.repository.DoctorRepository;
import com.springboot.hospital.service.CiteService;
import com.springboot.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private CiteService citeService;

    @Autowired
    private CiteMapper citeMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public List<DoctorDTO> getAllDoctor() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DoctorDTO> getDoctorById(Long id) {
        Optional<Doctor> optionalMedico = doctorRepository.findById(id);
        return optionalMedico.map(doctorMapper::toDTO);
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = doctorMapper.toEntity(doctorDTO);
        doctor = doctorRepository.save(doctor);
        return doctorMapper.toDTO(doctor);
    }

    @Override
    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO) {
        Optional<Doctor> optionalMedico = doctorRepository.findById(id);
        if(optionalMedico.isPresent()){
            Doctor doctor = optionalMedico.get();
            doctor.setName(doctorDTO.getName());
            doctor.setEmail(doctorDTO.getEmail());
            doctor.setSpecialty(doctorDTO.getSpecialty());

            doctor = doctorRepository.save(doctor);
            return doctorMapper.toDTO(doctor);
        }
        return null;
    }

    @Override
    public void deleteDoctor(Long id) {
        Optional<Doctor> optionalMedico = doctorRepository.findById(id);
        if(optionalMedico.isPresent()){
            Doctor doctor = optionalMedico.get();

            for(Cite cite : doctor.getCites()){
                citeService.deleteCite(cite.getId());
            }

            doctorRepository.deleteById(id);
        }
    }

    @Override
    public Collection<CiteDTO> getCitesByDoctorId(Long medicoId) {
        Optional<Doctor> optionalMedico = doctorRepository.findById(medicoId);
        return optionalMedico.map(medico -> medico.getCites().stream()
                .map(citeMapper::toDTO)
                .collect(Collectors.toList()))
                .orElse(null);
    }

    @Override
    public List<DoctorDTO> getDoctorBySpecialty(String specialty) {
        List<Doctor> doctors = doctorRepository.findBySpecialty(specialty);
        return doctors.stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
    }
}
