package com.springboot.hospital.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PatientDTO {

    private Long id;
    private String name;
    private Date birthDate;
    private boolean disease;

}
