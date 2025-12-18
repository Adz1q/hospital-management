package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.DoctorNotFoundException;
import com.adz1q.hospital.management.model.Doctor;
import com.adz1q.hospital.management.model.Specialization;
import com.adz1q.hospital.management.repository.DoctorRepository;
import com.adz1q.hospital.management.util.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor hireDoctor(
            String firstName,
            String lastName,
            LocalDate birthDate,
            Set<Specialization> specializations) {
        Doctor newDoctor = new Doctor(
                firstName,
                lastName,
                birthDate,
                specializations);
        Logger.info("Hired doctor with ID: " + newDoctor.getId());
        return doctorRepository.save(newDoctor);
    }

    public Doctor hireDoctor(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String pesel,
            Set<Specialization> specializations) {
        if (doctorRepository.findByPesel(pesel).isPresent()) {
            throw new IllegalArgumentException("Doctor with this PESEL already exists.");
        }

        Doctor newDoctor = new Doctor(
                firstName,
                lastName,
                birthDate,
                pesel,
                specializations);
        Logger.info("Hired doctor with ID: " + newDoctor.getId());
        return doctorRepository.save(newDoctor);
    }

    public Doctor getDoctor(UUID id) throws DoctorNotFoundException {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with ID: " + id + " does not exits."));
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public void dismissDoctor(UUID id)
            throws DoctorNotFoundException {
        Doctor doctor = getDoctor(id);
        doctor.dismiss();
        Logger.info("Dismissed doctor with ID: " + id);
    }

    public void rehireDoctor(UUID id)
            throws DoctorNotFoundException {
        Doctor doctor = getDoctor(id);
        doctor.rehire();
        Logger.info("Rehired doctor with ID: " + id);
    }

    public void updateSpecializations(
            UUID id,
            Specialization newSpecialization)
            throws DoctorNotFoundException {
        Doctor doctor = getDoctor(id);
        doctor.addSpecialization(newSpecialization);
        Logger.info("Added new specialization to doctor with ID: " + doctor.getId());
    }

    public void updateSpecializations(
            UUID id,
            Set<Specialization> newSpecializations)
            throws DoctorNotFoundException {
        for (Specialization newSpecialization : newSpecializations) {
            updateSpecializations(id, newSpecialization);
        }
    }
}
