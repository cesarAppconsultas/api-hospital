package com.springboot.hospital.controller;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.dto.PatientDTO;
import com.springboot.hospital.exception.ErrorMessages;
import com.springboot.hospital.exception.ResourceNotFoundException;
import com.springboot.hospital.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
@Tag(name = "Patient")

public class PatientController {

    @Autowired
    private PatientService patientService;

    @Operation(summary = "List Patient")

    @GetMapping
    public ResponseEntity<List<PatientDTO>> listPatient(){
        List<PatientDTO> patientDTOS = patientService.getAllPatients();
        return new ResponseEntity<>(patientDTOS, HttpStatus.OK);
    }

    @Operation(summary = "list Patient ForId")

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> listPatientForId(@PathVariable Long id){
        return patientService.getPatientById(id)
                .map(patientDto -> new ResponseEntity<>(patientDto,HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.RESOURCE_NOT_FOUND  + id));

    }

    @Operation(summary = "Save Patient")

    @PostMapping
    public ResponseEntity<PatientDTO> savePatient(@RequestBody PatientDTO patientDTO) {
        try {
            PatientDTO createdPatientDTO = patientService.createPatient(patientDTO);
            return new ResponseEntity<>(createdPatientDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResourceNotFoundException(ErrorMessages.PATIENT_CREATION_FAILED);
        }
    }

    @Operation(summary = "Update Patient")

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        PatientDTO updatePatient = patientService.updatePatient(id, patientDTO);
        if(updatePatient != null){
            return new ResponseEntity<>(updatePatient,HttpStatus.OK);
        }
        else{
            throw new ResourceNotFoundException(ErrorMessages.PATIENT_UPDATE_FAILED + id);

        }
    }

    @Operation(summary = "Delete Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResourceNotFoundException(ErrorMessages.PATIENT_DELETION_FAILED + id);
        }
    }


    @Operation(summary = "List Cite For PatientId")

    @GetMapping("/{id}/cite")
    public ResponseEntity<Collection<CiteDTO>> listCiteForPatientId(@PathVariable Long id){
        Collection<CiteDTO> cite = patientService.getCitesByPatientId(id);
        return new ResponseEntity<>(cite,HttpStatus.OK);



    }
}
