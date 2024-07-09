package com.springboot.hospital.service;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.dto.DoctorDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DoctorService {

    List<DoctorDTO> getAllDoctor();

    Optional<DoctorDTO> getDoctorById(Long id);

    DoctorDTO createDoctor(DoctorDTO doctorDTO);

    DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO);

    void deleteDoctor(Long id);

    Collection<CiteDTO> getCitesByDoctorId(Long medicoId);

    List<DoctorDTO> getDoctorBySpecialty(String specialty);

}
