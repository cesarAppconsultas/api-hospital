package com.springboot.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateCite;

    private boolean cancel;

    @Enumerated(EnumType.STRING)
    private StatusCite statusCite;

    @ManyToOne
    private patient patient;

    @ManyToOne
    private Doctor doctor;

    @OneToOne(mappedBy = "cite")
    private Consult consult;
}
