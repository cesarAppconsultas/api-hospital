package com.springboot.hospital;

import com.springboot.hospital.model.*;
import com.springboot.hospital.repository.CiteRepository;
import com.springboot.hospital.repository.ConsultRepository;
import com.springboot.hospital.repository.DoctorRepository;
import com.springboot.hospital.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiHospitralSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiHospitralSpringbootApplication.class, args);
	}

	//@Bean
	CommandLineRunner start(PatientRepository patientRepository,
							DoctorRepository doctorRepository,
							CiteRepository citeRepository,
							ConsultRepository consultRepository){
		return args -> {
			Stream.of("Christian","Raul","Lanudo")
					.forEach(nombre -> {
						patient patient = new patient();
						patient.setName(nombre);
						patient.setBirthDate(new Date());
						patient.setDisease(false);
						patientRepository.save(patient);
					});

			Stream.of("Biaggio","Julen","Mark")
					.forEach(name -> {
						Doctor doctor = new Doctor();
						doctor.setName(name);
						doctor.setEmail(name + ((int) Math.random() * 9) + "@gmail.com");
						doctor.setSpecialty(Math.random() > 0.5 ? "Cardiología":"Obstetricia");
						doctorRepository.save(doctor);
					});

			patient patient1 = patientRepository.findById(1L).orElse(null);

			Doctor doctor = doctorRepository.findByName("Biaggio");

			Cite cite1 = new Cite();
			cite1.setDateCite(new Date());
			cite1.setStatusCite(StatusCite.PENDIENTE);
			cite1.setDoctor(doctor);
			cite1.setPatient(patient1);
			citeRepository.save(cite1);

			Cite citeBBDD1 = citeRepository.findById(1L).orElse(null);

			Consult consult = new Consult();
			consult.setDateConsult(new Date());
			consult.setCite(citeBBDD1);
			consult.setReport("Informe de la consulta");
			consultRepository.save(consult);
		};
	}
}
