package com.springboot.hospital.mapper;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.model.Cite;
import com.springboot.hospital.model.Doctor;
import com.springboot.hospital.model.patient;
import com.springboot.hospital.model.StatusCite;
import com.springboot.hospital.repository.DoctorRepository;
import com.springboot.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class CitaMapper {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public CiteDTO toDTO(Cite cite){
        CiteDTO citeDTO = new CiteDTO();

        citeDTO.setId(cite.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formmatedFecha = sdf.format(cite.getDateCite());

        citeDTO.setDateCite(formmatedFecha);

        citeDTO.setCancel(cite.isCancel());
        citeDTO.setStatusCite(cite.getStatusCite().name());
        citeDTO.setPatientId(cite.getPatient().getId());
        citeDTO.setDoctorId(cite.getDoctor().getId());
        return citeDTO;
    }

    public Cite toEntity(CiteDTO citeDTO, patient patient, Doctor doctor) throws ParseException {
        Cite cite = new Cite();

        cite.setId(citeDTO.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = sdf.parse(citeDTO.getDateCite());
        cite.setDateCite(fecha);

        cite.setCancel(citeDTO.isCancel());
        cite.setStatusCite(StatusCite.valueOf(citeDTO.getStatusCite()));
        cite.setPatient(patient);
        cite.setDoctor(doctor);

        return cite;
    }

    public Cite toEntity(CiteDTO citeDTO) throws ParseException {
        Cite cite = new Cite();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = sdf.parse(citeDTO.getDateCite());
        cite.setDateCite(fecha);

        cite.setCancel(citeDTO.isCancel());
        cite.setStatusCite(StatusCite.valueOf(citeDTO.getStatusCite()));

        Optional<patient> paciente = patientRepository.findById(citeDTO.getPatientId());
        patient patientBBDD = paciente.get();
        cite.setPatient(patientBBDD);

        Optional<Doctor> medico = doctorRepository.findById(citeDTO.getDoctorId());
        Doctor doctorBBDD = medico.get();
        cite.setDoctor(doctorBBDD);

        return cite;
    }
}
