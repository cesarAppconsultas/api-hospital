package com.springboot.hospital.service;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.model.Cite;
import com.springboot.hospital.model.StatusCite;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface CiteService {

    List<CiteDTO> getAllCite();

    Optional<CiteDTO> getCiteById(Long id);

    Cite createCite(CiteDTO citeDTO, Long idPaciente, Long idMedico) throws ParseException;

    CiteDTO updateCite(Long id, CiteDTO citeDTO) throws ParseException;

    void deleteCite(Long id);

    List<CiteDTO> getCiteByPatientId(Long patientId);

    List<CiteDTO> getCitesByDoctorId(Long doctorId);

    List<CiteDTO> getCitesByStatusCite(StatusCite statusCite);
}
