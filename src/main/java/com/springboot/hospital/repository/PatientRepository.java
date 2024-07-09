package com.springboot.hospital.repository;

import com.springboot.hospital.model.patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<patient,Long> {

    patient findByName(String name);

}
