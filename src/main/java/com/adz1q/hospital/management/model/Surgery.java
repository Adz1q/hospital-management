package com.adz1q.hospital.management.model;

import java.time.LocalDate;

public class Surgery extends Treatment {
    private final Doctor doctor;
    private final LocalDate surgeryDate;

    public Surgery(
            String description,
            LocalDate prescriptionDate,
            Doctor doctor,
            LocalDate surgeryDate) {
        super(description, prescriptionDate);
        validateDoctor(doctor);
        validateSurgeryDate(surgeryDate);
        this.doctor = doctor;
        this.surgeryDate = surgeryDate;
    }

    @Override
    public void showTreatmentDetails() {
        System.out.println("------------------------- SURGERY -------------------------");
        System.out.println("ID: " + super.getId());
        System.out.println("Description: " + super.getDescription());
        System.out.println("Prescription Date: " + super.getPrescriptionDate());
        System.out.println("Doctor: " + doctor.getFullName());
        System.out.println("Surgery Date: " + surgeryDate);
        System.out.println("-----------------------------------------------------------");
    }

    private void validateDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new NullPointerException("Doctor cannot be null.");
        }
    }

    private void validateSurgeryDate(LocalDate surgeryDate) {
        if (surgeryDate == null) {
            throw new NullPointerException("Surgery date cannot be null.");
        }
    }

    @Override
    public String toString() {
        return "Surgery{" +
                "doctor=" + doctor +
                ", surgeryDate=" + surgeryDate +
                '}';
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getSurgeryDate() {
        return surgeryDate;
    }
}
