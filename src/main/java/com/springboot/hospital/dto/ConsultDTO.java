package com.springboot.hospital.dto;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ConsultDTO {

    private Long id;
    private String dateConsult;
    private String report;
    private CiteDTO citeDTO;

    public Date getDateConsultAsDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(this.dateConsult);
    }

    public void setDateConsultFromDate(Date dateConsult){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateConsult = sdf.format(dateConsult);
    }
}
