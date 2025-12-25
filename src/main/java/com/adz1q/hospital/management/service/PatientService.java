package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.PatientNotFoundException;
import com.adz1q.hospital.management.model.Diagnosis;
import com.adz1q.hospital.management.model.Patient;
import com.adz1q.hospital.management.model.Treatment;
import com.adz1q.hospital.management.repository.PatientRepository;
import com.adz1q.hospital.management.util.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(
            PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient registerPatient(
            String firstName,
            String lastName,
            LocalDate birthDate) {
        Patient newPatient = new Patient(
                firstName,
                lastName,
                birthDate);
        Logger.info("Registered patient with ID: " + newPatient.getId());
        return patientRepository.save(newPatient);
    }

    public Patient registerPatient(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String pesel) {
        if (patientRepository.findByPesel(pesel).isPresent()) {
            throw new IllegalArgumentException("Patient with this PESEL already exists.");
        }

        Patient newPatient = new Patient(
                firstName,
                lastName,
                birthDate,
                pesel);
        Logger.info("Registered patient with ID: " + newPatient.getId());
        return patientRepository.save(newPatient);
    }

    public Patient getPatient(UUID id)
            throws PatientNotFoundException {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient with ID: " + id + " does not exist."));
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public List<Diagnosis> getPatientDocumentation(UUID patientId)
            throws PatientNotFoundException {
        Patient patient = getPatient(patientId);
        return patientRepository.findPatientDocumentation(patient.getId());
    }

    public List<Treatment> getPatientTreatments(UUID patientId)
            throws PatientNotFoundException {
        Patient patient = getPatient(patientId);
        return patientRepository.findPatientTreatments(patient.getId());
    }

    public List<Patient> getPatientsByFirstAndLastName(
            String firstName,
            String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("First name and last name cannot be null.");
        }

        if (firstName.isBlank() || lastName.isBlank()) {
            throw new IllegalArgumentException("First name and last name cannot be blank.");
        }

        return patientRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public List<Patient> getPatientsByLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Last name cannot be null.");
        }

        if (lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be blank.");
        }

        return patientRepository.findByLastName(lastName);
    }
}
