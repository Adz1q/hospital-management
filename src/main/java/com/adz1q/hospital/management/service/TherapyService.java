package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.TherapyNotFoundException;
import com.adz1q.hospital.management.model.Therapy;
import com.adz1q.hospital.management.repository.TherapyRepository;
import com.adz1q.hospital.management.util.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TherapyService {
    private final TherapyRepository therapyRepository;

    public TherapyService(TherapyRepository therapyRepository) {
        this.therapyRepository = therapyRepository;
    }

    public Therapy prescribeTherapy(
            String description,
            LocalDate prescriptionDate,
            String name) {
        Therapy newTherapy = new Therapy(
                description,
                prescriptionDate,
                name);
        Logger.info("Created therapy with ID: " + newTherapy.getId());
        return therapyRepository.save(newTherapy);
    }

    public Therapy getTherapy(UUID id)
            throws TherapyNotFoundException {
        return therapyRepository.findById(id)
                .orElseThrow(() -> new TherapyNotFoundException("Therapy with this ID does not exist."));
    }

    public List<Therapy> getAllTherapies() {
        return therapyRepository.findAll();
    }
}
