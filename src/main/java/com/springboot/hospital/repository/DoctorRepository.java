package com.springboot.hospital.repository;

import com.springboot.hospital.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    Doctor findByName(String name);

    List<Doctor> findBySpecialty(String specialty);

}
