package com.springboot.hospital.repository;

import com.springboot.hospital.model.Cite;
import com.springboot.hospital.model.StatusCite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiteRepository extends JpaRepository<Cite,Long> {

    List<Cite> findByPatientId(Long patientId);

    List<Cite> findByDoctorId(Long doctorId);

    List<Cite> findByStatusCite(StatusCite statusCite);

}
