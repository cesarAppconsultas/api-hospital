package com.springboot.hospital.controller;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.dto.DoctorDTO;
import com.springboot.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> listarMedicos(){
        List<DoctorDTO> medicos = doctorService.getAllDoctor();
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> listDoctorForId(@PathVariable Long id){
        return doctorService.getDoctorById(id)
                .map(doc -> new ResponseEntity<>(doc,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> saveDoctor(@RequestBody DoctorDTO doctorDTO){
        DoctorDTO createdDoctor = doctorService.createDoctor(doctorDTO);
        return new ResponseEntity<>(createdDoctor,HttpStatus.CREATED);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

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

    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<DoctorDTO>> lisDoctorForSpecialty(@PathVariable String specialty){
        List<DoctorDTO> doc = doctorService.getDoctorBySpecialty(specialty);
        return new ResponseEntity<>(doc,HttpStatus.OK);
    }
}
