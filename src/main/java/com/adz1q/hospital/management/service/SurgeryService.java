package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.DoctorNotFoundException;
import com.adz1q.hospital.management.exception.SurgeryNotFoundException;
import com.adz1q.hospital.management.model.Doctor;
import com.adz1q.hospital.management.model.Surgery;
import com.adz1q.hospital.management.repository.DoctorRepository;
import com.adz1q.hospital.management.repository.SurgeryRepository;
import com.adz1q.hospital.management.util.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class SurgeryService {
    private final SurgeryRepository surgeryRepository;
    private final DoctorRepository doctorRepository;

    public SurgeryService(
            SurgeryRepository surgeryRepository,
            DoctorRepository doctorRepository) {
        this.surgeryRepository = surgeryRepository;
        this.doctorRepository = doctorRepository;
    }

    public Surgery scheduleSurgery(
            String description,
            LocalDate prescriptionDate,
            Doctor doctor,
            LocalDate surgeryDate) {
        if (existsAnySurgeryByDoctorIdAndDate(
                doctor.getId(),
                surgeryDate)) {
            throw new IllegalArgumentException("Doctor already has a surgery scheduled on this date.");
        }

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

    public void cancelSurgery(UUID id)
            throws SurgeryNotFoundException {
        Surgery surgery = getSurgery(id);
        surgery.cancelSurgery();
        Logger.info("Cancelled surgery with ID: " + id);
    }

    public void changeSurgeryDoctor(
            UUID surgeryId,
            Doctor newDoctor)
            throws SurgeryNotFoundException {
        Surgery surgery = getSurgery(surgeryId);

        if (existsAnySurgeryByDoctorIdAndDate(
                newDoctor.getId(),
                surgery.getSurgeryDate())) {
            throw new IllegalArgumentException("Doctor already has a surgery scheduled on this date.");
        }

        surgery.changeDoctor(newDoctor);
        Logger.info("Changed doctor for surgery with ID: " + surgeryId);
    }

    public void rescheduleSurgery(
            UUID id,
            LocalDate newSurgeryDate)
            throws SurgeryNotFoundException {
        Surgery surgery = getSurgery(id);

        if (existsAnySurgeryByDoctorIdAndDate(
                surgery.getDoctor().getId(),
                newSurgeryDate)) {
            throw new IllegalArgumentException("Doctor already has a surgery scheduled on this date.");
        }

        surgery.changeSurgeryDate(newSurgeryDate);
        surgeryRepository.save(surgery);
        Logger.info("Rescheduled surgery with ID: " + id);
    }

    public void completeSurgery(UUID id)
            throws SurgeryNotFoundException {
        Surgery surgery = getSurgery(id);
        surgery.completeSurgery();
        Logger.info("Completed surgery with ID: " + id);
    }

    public boolean existsAnySurgeryByDoctorIdAndDate(
            UUID doctorId,
            LocalDate date) {
        return surgeryRepository.existsByDoctorIdAndSurgeryDate(doctorId, date);
    }

    public List<Surgery> getSurgeriesByDoctorId(UUID doctorId)
            throws DoctorNotFoundException {
        Doctor doctor = getDoctor(doctorId);
        return surgeryRepository.findByDoctorId(doctor.getId());
    }

    public Doctor getDoctor(UUID id)
            throws DoctorNotFoundException {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with ID: " + id + " does not exits."));
    }
}
