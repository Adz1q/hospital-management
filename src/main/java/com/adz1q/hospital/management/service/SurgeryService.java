package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.SurgeryNotFoundException;
import com.adz1q.hospital.management.model.Doctor;
import com.adz1q.hospital.management.model.Surgery;
import com.adz1q.hospital.management.repository.SurgeryRepository;
import com.adz1q.hospital.management.util.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class SurgeryService {
    private final SurgeryRepository surgeryRepository;

    public SurgeryService(SurgeryRepository surgeryRepository) {
        this.surgeryRepository = surgeryRepository;
    }

    public Surgery scheduleSurgery(
            String description,
            LocalDate prescriptionDate,
            Doctor doctor,
            LocalDate surgeryDate) {
        Surgery newSurgery = new Surgery(
                description,
                prescriptionDate,
                doctor,
                surgeryDate);
        Logger.info("Scheduled surgery with ID: " + newSurgery.getId());
        return surgeryRepository.save(newSurgery);
    }

    public Surgery getSurgery(UUID id) throws SurgeryNotFoundException {
        return surgeryRepository.findById(id)
                .orElseThrow(() -> new SurgeryNotFoundException("Surgery not found with ID: " + id));
    }

    public List<Surgery> getAllSurgeries() {
        return surgeryRepository.findAll();
    }

    public void cancelSurgery(UUID id) throws SurgeryNotFoundException {
        getSurgery(id);
        surgeryRepository.deleteById(id);
        Logger.info("Cancelled surgery with ID: " + id);
    }

    public void rescheduleSurgery(
            UUID id,
            LocalDate newSurgeryDate)
            throws SurgeryNotFoundException {
        Surgery surgery = getSurgery(id);
        surgery.changeSurgeryDate(newSurgeryDate);
        surgeryRepository.save(surgery);
        Logger.info("Rescheduled surgery with ID: " + id);
    }
}
