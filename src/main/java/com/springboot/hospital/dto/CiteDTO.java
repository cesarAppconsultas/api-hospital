package com.springboot.hospital.dto;

import lombok.Data;

@Data
public class CiteDTO {

    private Long id;
    private String dateCite;
    private String statusCite;
    private boolean cancel;
    private Long patientId;
    private Long doctorId;

}
