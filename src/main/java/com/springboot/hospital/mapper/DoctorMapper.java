package com.springboot.hospital.mapper;

import com.springboot.hospital.dto.DoctorDTO;
import com.springboot.hospital.model.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDTO toDTO(Doctor doctor);

    Doctor toEntity(DoctorDTO doctorDTO);
}
