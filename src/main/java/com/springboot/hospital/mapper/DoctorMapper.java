package com.springboot.hospital.mapper;

import com.springboot.hospital.dto.DoctorDTO;
import com.springboot.hospital.model.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public DoctorDTO toDTO(Doctor doctor){
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setName(doctor.getName());
        doctorDTO.setEmail(doctor.getEmail());
        doctorDTO.setSpecialty(doctor.getSpecialty());
        return doctorDTO;
    }

    public Doctor toEntity(DoctorDTO doctorDTO){
        Doctor doctor = new Doctor();
        doctor.setId(doctorDTO.getId());
        doctor.setName(doctorDTO.getName());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setSpecialty(doctorDTO.getSpecialty());
        return doctor;
    }
}
