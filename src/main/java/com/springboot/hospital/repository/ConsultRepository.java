package com.springboot.hospital.repository;

import com.springboot.hospital.model.Cite;
import com.springboot.hospital.model.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultRepository extends JpaRepository<Consult,Long> {

    List<Consult> findByCite(Cite cite);

    List<Consult> findByReportContainingIgnoreCase(String searchTerm);

}
