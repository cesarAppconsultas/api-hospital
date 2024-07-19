package com.springboot.hospital.controller;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.dto.DoctorDTO;
import com.springboot.hospital.exception.ErrorMessages;
import com.springboot.hospital.exception.ResourceNotFoundException;
import com.springboot.hospital.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@Tag(name = "Doctor")

public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Operation(summary = "Consult for id")
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> listDoctor(){
        List<DoctorDTO> medicos = doctorService.getAllDoctor();
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    @Operation(summary = "List Doctor For Id")
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> listDoctorForId(@PathVariable Long id){
        return doctorService.getDoctorById(id)
                .map(doc -> new ResponseEntity<>(doc,HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.RESOURCE_NOT_FOUND  + id));
    }

    @Operation(summary = "Save Doctor")
    @PostMapping
    public ResponseEntity<DoctorDTO> saveDoctor(@RequestBody DoctorDTO doctorDTO){
        try {
            DoctorDTO createdDoctor = doctorService.createDoctor(doctorDTO);
            return new ResponseEntity<>(createdDoctor,HttpStatus.CREATED);
        }catch ( Exception e){
            throw new ResourceNotFoundException(ErrorMessages.DOCTOR_CREATION_FAILED);
        }



    }

    @Operation(summary = "Update Doctor")
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO){
        DoctorDTO updateDoctor = doctorService.updateDoctor(id, doctorDTO);
        if(updateDoctor != null){
            return new ResponseEntity<>(updateDoctor,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Delete Doctor")

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        try {
            doctorService.deleteDoctor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch ( Exception e){
            throw new ResourceNotFoundException(ErrorMessages.DOCTOR_DELETE_FAILED);
        }


    }

    @Operation(summary = "list Cite For Doctor Id")

    @GetMapping("/{id}/cite")
    public ResponseEntity<Collection<CiteDTO>> listCiteForDoctorId(@PathVariable Long id){
        Collection<CiteDTO> cite = doctorService.getCitesByDoctorId(id);
        if(cite != null){
            return new ResponseEntity<>(cite,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Lis Doctor For Specialty")
    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<DoctorDTO>> lisDoctorForSpecialty(@PathVariable String specialty){

        try {
            List<DoctorDTO> doc = doctorService.getDoctorBySpecialty(specialty);
            return new ResponseEntity<>(doc,HttpStatus.OK);
        }

        catch ( Exception e){
            throw new ResourceNotFoundException(ErrorMessages.DOCTOR_GET_FAILED);
        }



    }
}
