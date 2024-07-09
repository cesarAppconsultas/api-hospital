package com.springboot.hospital.service;

import com.springboot.hospital.dto.ConsultDTO;
import com.springboot.hospital.model.Cite;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface ConsultService {

    List<ConsultDTO> getAllConsult();

    Optional<ConsultDTO> getConsultById(Long id);

    ConsultDTO createConsult(Long citeId, ConsultDTO consultDTO) throws ParseException;

    ConsultDTO updateConsult(Long id, ConsultDTO consultDTO) throws ParseException;

    void deleteConsult(Long id);

    List<ConsultDTO> getConsultByInformContaining(String searchTerm);

    List<ConsultDTO> getConsultByCite(Cite cite);

    List<ConsultDTO> getConsultByCite(Long citeId) throws ParseException;

}
