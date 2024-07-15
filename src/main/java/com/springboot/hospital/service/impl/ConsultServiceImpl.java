package com.springboot.hospital.service.impl;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.dto.ConsultDTO;
import com.springboot.hospital.mapper.CiteMapper;
import com.springboot.hospital.mapper.ConsultMapper;
import com.springboot.hospital.model.Cite;
import com.springboot.hospital.model.Consult;
import com.springboot.hospital.model.StatusCite;
import com.springboot.hospital.repository.CiteRepository;
import com.springboot.hospital.repository.ConsultRepository;
import com.springboot.hospital.service.CiteService;
import com.springboot.hospital.service.ConsultService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultServiceImpl implements ConsultService {

    @Autowired
    private ConsultRepository consultRepository;

    @Autowired
    private CiteMapper citeMapper;

    @Autowired
    private ConsultMapper consultMapper;

    @Autowired
    private CiteService citeService;

    @Autowired
    private CiteRepository citeRepository;

    @Override
    public List<ConsultDTO> getAllConsult() {
        List<Consult> consults = consultRepository.findAll();
        return consults.stream()
                .map(consultMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ConsultDTO> getConsultById(Long id) {
        Optional<Consult> consulta = consultRepository.findById(id);
        return consulta.map(consultMapper::toDTO);
    }

    @Override
    public ConsultDTO createConsult(Long citeId, ConsultDTO consultDTO) throws ParseException {
        CiteDTO citeDTO = citeService.getCiteById(citeId).orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));

        Consult consult = new Consult();
        consult.setCite(citeMapper.toEntity(citeDTO));
        consult.setDateConsult(new Date());
        consult.setReport(consultDTO.getReport());

        Consult createdConsult = consultRepository.save(consult);
        return consultMapper.toDTO(createdConsult);
    }

    @Override
    public ConsultDTO updateConsult(Long id, ConsultDTO consultDTO) throws ParseException {
        Optional<Consult> consultaOptional = consultRepository.findById(id);
        if(consultaOptional.isPresent()){
            Consult consult = consultaOptional.get();

            consult.setDateConsult(consultDTO.getDateConsultAsDate());
            consult.setReport(consultDTO.getReport());

            Consult updateConsult = consultRepository.save(consult);

            CiteDTO citeDTO = consultDTO.getCiteDTO();

            if(citeDTO != null){

                Cite cite = consult.getCite();
                if(cite != null){
                    cite.setDateCite(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(citeDTO.getDateCite()));
                    cite.setStatusCite(StatusCite.valueOf(citeDTO.getStatusCite()));

                    citeRepository.save(cite);
                }

                citeService.updateCite(cite.getId(), citeDTO);
            }
            return consultMapper.toDTO(updateConsult);
        }
        return null;
    }

    @Override
    public void deleteConsult(Long id) {
        consultRepository.deleteById(id);
    }

    @Override
    public List<ConsultDTO> getConsultByInformContaining(String searchTerm) {
        List<Consult> consults = consultRepository.findByReportContainingIgnoreCase(searchTerm);
        return consults.stream()
                .map(consultMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultDTO> getConsultByCite(Cite cite) {
        List<Consult> consults = consultRepository.findByCite(cite);
        return consults.stream()
                .map(consultMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultDTO> getConsultByCite(Long citaId) throws ParseException {
        Optional<Cite> citaOptional = citeRepository.findById(citaId);

        if(citaOptional.isPresent()){
            Cite cite = citaOptional.get();

            if(cite.getId() != null){
                citeRepository.save(cite);
            }

            List<Consult> consults = consultRepository.findByCite(cite);

            List<ConsultDTO> consultDTOS = new ArrayList<>();
            for(Consult consult : consults){
                ConsultDTO consultDTO = new ConsultDTO();
                consultDTO.setId(consult.getId());
                consultDTO.setDateConsult(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(consult.getDateConsult()));
                consultDTO.setReport(consult.getReport());

                consultDTOS.add(consultDTO);
            }
            return consultDTOS;
        }
        else{
            throw new EntityNotFoundException("Cita no encontrada con el ID : " + citaId);
        }
    }
}
