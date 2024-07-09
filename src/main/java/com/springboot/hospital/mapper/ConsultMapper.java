package com.springboot.hospital.mapper;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.dto.ConsultDTO;
import com.springboot.hospital.model.Cite;
import com.springboot.hospital.model.Consult;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class ConsultMapper {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ConsultDTO toDTO(Consult consult){
        ConsultDTO consultDTO = new ConsultDTO();
        consultDTO.setId(consult.getId());
        consultDTO.setDateConsult(dateFormat.format(consult.getDateConsult()));
        consultDTO.setReport(consult.getReport());

        if(consult.getCite() != null){
            Cite cite = consult.getCite();
            CiteDTO citeDTO = new CiteDTO();

            citeDTO.setId(cite.getId());
            citeDTO.setDateCite(dateFormat.format(cite.getDateCite()));
            citeDTO.setCancel(cite.isCancel());
            citeDTO.setStatusCite(cite.getStatusCite().toString());
            citeDTO.setPatientId(citeDTO.getPatientId());
            citeDTO.setDoctorId(citeDTO.getDoctorId());
            consultDTO.setCiteDTO(citeDTO);
        }
        return consultDTO;
    }

    public Consult toEntity(ConsultDTO consultDTO) throws ParseException {
        Consult consult = new Consult();
        consult.setId(consultDTO.getId());
        consult.setDateConsult((dateFormat.parse(consultDTO.getDateConsult())));
        consult.setReport(consult.getReport());

        if(consultDTO.getCiteDTO() != null){
            CiteDTO citeDTO = consultDTO.getCiteDTO();
            Cite cite = new Cite();
            cite.setId(citeDTO.getId());
            consult.setCite(cite);
        }
        return consult;
    }
}
