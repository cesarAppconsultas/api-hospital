package com.springboot.hospital.dto;

import lombok.Data;

@Data
public class DoctorDTO {

    private Long id;
    private String name;
    private String email;
    private String specialty;

}
