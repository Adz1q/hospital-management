package com.adz1q.hospital.management.model;

import java.time.LocalDate;

public class Surgery extends Treatment {
    private Doctor doctor;
    private LocalDate surgeryDate;
    private SurgeryStatus status;

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
        this.status = SurgeryStatus.SCHEDULED;
    }

    @Override
    public void showDetails() {
        System.out.println("ID: " + super.getId());
        System.out.println("Description: " + super.getDescription());
        System.out.println("Prescription Date: " + super.getPrescriptionDate());
        System.out.println("Doctor: " + doctor.getFullName());
        System.out.println("Surgery Date: " + surgeryDate);
        System.out.println("Status: " + status.getStatus());
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

    public void changeDoctor(Doctor doctor) {
        if (status == SurgeryStatus.COMPLETED
                || status == SurgeryStatus.CANCELLED) {
            throw new IllegalStateException("Cannot change doctor for a completed or cancelled surgery.");
        }

        validateDoctor(doctor);
        this.doctor = doctor;
    }

    public void changeSurgeryDate(LocalDate surgeryDate) {
        if (status == SurgeryStatus.COMPLETED
                || status == SurgeryStatus.CANCELLED) {
            throw new IllegalStateException("Cannot change doctor for a completed or cancelled surgery.");
        }

        validateSurgeryDate(surgeryDate);

        if (surgeryDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid surgery date.");
        }

        this.surgeryDate = surgeryDate;
    }

    public void completeSurgery() {
        if (status == SurgeryStatus.CANCELLED
                || status == SurgeryStatus.COMPLETED) {
            throw new IllegalStateException("Cannot complete a cancelled or already completed surgery.");
        }

        this.status = SurgeryStatus.COMPLETED;
    }

    public void cancelSurgery() {
        if (status == SurgeryStatus.CANCELLED
                || status == SurgeryStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a cancelled or already completed surgery.");
        }

        this.status = SurgeryStatus.CANCELLED;
    }

    @Override
    public String toString() {
        return "Surgery{" +
                "doctor=" + doctor +
                ", surgeryDate=" + surgeryDate +
                ", status=" + status +
                '}';
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getSurgeryDate() {
        return surgeryDate;
    }

    public SurgeryStatus getStatus() {
        return status;
    }
}
