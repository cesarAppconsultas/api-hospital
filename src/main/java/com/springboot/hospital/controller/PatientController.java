package com.springboot.hospital.controller;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.dto.PatientDTO;
import com.springboot.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> listPatient(){
        List<PatientDTO> patientDTOS = patientService.getAllPatients();
        return new ResponseEntity<>(patientDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> listPatientForId(@PathVariable Long id){
        return patientService.getPatientById(id)
                .map(patientDto -> new ResponseEntity<>(patientDto,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PatientDTO> savePatient(@RequestBody PatientDTO patientDTO){
        PatientDTO createdPatientDTO = patientService.createPatient(patientDTO);
        return new ResponseEntity<>(createdPatientDTO,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        PatientDTO updatePatient = patientService.updatePatient(id, patientDTO);
        if(updatePatient != null){
            return new ResponseEntity<>(updatePatient,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/cite")
    public ResponseEntity<Collection<CiteDTO>> listCiteForPatientId(@PathVariable Long id){
        Collection<CiteDTO> cite = patientService.getCitesByPatientId(id);
        return new ResponseEntity<>(cite,HttpStatus.OK);
    }
}
