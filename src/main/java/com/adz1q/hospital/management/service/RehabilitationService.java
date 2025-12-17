package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.RehabilitationNotFoundException;
import com.adz1q.hospital.management.model.Rehabilitation;
import com.adz1q.hospital.management.repository.RehabilitationRepository;
import com.adz1q.hospital.management.util.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class RehabilitationService {
    private final RehabilitationRepository rehabilitationRepository;

    public RehabilitationService(
            RehabilitationRepository rehabilitationRepository) {
        this.rehabilitationRepository = rehabilitationRepository;
    }

    public Rehabilitation prescribeRehabilitation(
            LocalDate prescriptionDate,
            String description,
            List<String> therapies) {
        Rehabilitation newRehabilitation = new Rehabilitation(
                prescriptionDate,
                description,
                therapies);
        Logger.info("Prescribed rehabilitation with ID: " + newRehabilitation.getId());
        return rehabilitationRepository.save(newRehabilitation);
    }

    public Rehabilitation getRehabilitation(UUID id)
            throws RehabilitationNotFoundException {
        return rehabilitationRepository.findById(id)
                .orElseThrow(() -> new RehabilitationNotFoundException("Rehabilitation with this this ID does not exist."));
    }

    public List<Rehabilitation> getAllRehabilitations() {
        return rehabilitationRepository.findAll();
    }

    public void deleteRehabilitation(UUID id)
            throws RehabilitationNotFoundException {
        getRehabilitation(id);
        rehabilitationRepository.deleteById(id);
        Logger.info("Deleted rehabilitation with ID: " + id);
    }
}
