package com.springboot.hospital.service.impl;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.dto.DoctorDTO;
import com.springboot.hospital.dto.PatientDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.DoctorMapper;
import com.springboot.hospital.mapper.PatientMapper;
import com.springboot.hospital.model.*;
import com.springboot.hospital.repository.CiteRepository;
import com.springboot.hospital.repository.ConsultRepository;
import com.springboot.hospital.repository.DoctorRepository;
import com.springboot.hospital.repository.PatientRepository;
import com.springboot.hospital.service.CiteService;
import com.springboot.hospital.service.DoctorService;
import com.springboot.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CiteServiceImpl implements CiteService {


    @Autowired
    private CiteRepository citeRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsultRepository consultRepository;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Override
    public List<CiteDTO> getAllCite() {
        List<Cite> cites = citeRepository.findAll();
        return cites.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CiteDTO> getCiteById(Long id) {
        Optional<Cite> citaOptional = citeRepository.findById(id);
        return citaOptional.map(citaMapper::toDTO);
    }

    @Override
    public Cite createCite(CiteDTO citeDTO, Long idPaciente, Long idMedico) throws ParseException {
        PatientDTO patientDTO = patientService.getPatientById(idPaciente).orElse(null);
        DoctorDTO doctorDTO = doctorService.getDoctorById(idMedico).orElse(null);

        if(patientDTO == null || doctorDTO == null){
            return null;
        }

        patient patient = patientMapper.toEntity(patientDTO);
        Doctor doctor = doctorMapper.toEntity(doctorDTO);
        Cite cite = citaMapper.toEntity(citeDTO, patient, doctor);

        return citeRepository.save(cite);
    }

    @Override
    public CiteDTO updateCite(Long id, CiteDTO citeDTO) throws ParseException {
        Optional<Cite> citaOptional = citeRepository.findById(id);

        if(citaOptional.isPresent()){
            Cite cite = citaOptional.get();

            cite.setId(citeDTO.getId());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fecha = dateFormat.parse(citeDTO.getDateCite());

            cite.setCancel(citeDTO.isCancel());
            cite.setStatusCite(StatusCite.valueOf(citeDTO.getStatusCite()));

            Optional<patient> pacienteOptional = patientRepository.findById(citeDTO.getPatientId());
            cite.setPatient(pacienteOptional.get());

            Optional<Doctor> medicoOptional = doctorRepository.findById(citeDTO.getDoctorId());
            cite.setDoctor(medicoOptional.get());

            return citaMapper.toDTO(citeRepository.save(cite));
        }
        return null;
    }

    @Override
    public void deleteCite(Long id) {
        Optional<Cite> citaOptional = citeRepository.findById(id);

        if(citaOptional.isPresent()){
            Cite cite = citaOptional.get();

            if(cite.getConsult() != null){
                Consult consult = cite.getConsult();
                consult.setCite(null);
                consultRepository.delete(consult);
            }
            citeRepository.delete(cite);
        }
    }

    @Override
    public List<CiteDTO> getCiteByPatientId(Long pacienteId) {
        List<Cite> cites = citeRepository.findByPatientId(pacienteId);
        return cites.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CiteDTO> getCitesByDoctorId(Long medicoId) {
        List<Cite> cites = citeRepository.findByDoctorId(medicoId);
        return cites.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CiteDTO> getCitesByStatusCite(StatusCite statusCite) {
        List<Cite> cites = citeRepository.findByStatusCite(statusCite);
        return cites.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
