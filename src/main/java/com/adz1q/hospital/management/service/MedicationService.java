package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.MedicationNotFoundException;
import com.adz1q.hospital.management.model.Medication;
import com.adz1q.hospital.management.repository.MedicationRepository;
import com.adz1q.hospital.management.util.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MedicationService {
    private final MedicationRepository medicationRepository;

    public MedicationService(
            MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public Medication prescribeMedication(
            LocalDate prescriptionDate,
            String description,
            List<String> medicines) {
        Medication newMedication = new Medication(
                prescriptionDate,
                description,
                medicines);
        Logger.info("Prescribed medication with ID: " + newMedication.getId());
        return medicationRepository.save(newMedication);
    }

    public Medication getMedication(UUID id)
            throws MedicationNotFoundException {
        return medicationRepository.findById(id)
                .orElseThrow(() -> new MedicationNotFoundException("Medication with this ID does not exist."));
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }
}
