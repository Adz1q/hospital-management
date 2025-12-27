package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Appointment implements Identifiable<UUID>, Describable {
    private final UUID id;
    private final Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private Diagnosis diagnosis;
    private AppointmentStatus status;

    public Appointment(
            Patient patient,
            Doctor doctor,
            LocalDate date) {
        validatePatient(patient);
        validateDoctor(doctor);
        validateDate(date);
        this.id = UUID.randomUUID();
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.diagnosis = null;
        this.status = AppointmentStatus.SCHEDULED;
    }

    public Appointment(
            UUID id,
            Patient patient,
            Doctor doctor,
            LocalDate date,
            Diagnosis diagnosis,
            AppointmentStatus status) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.diagnosis = diagnosis;
        this.status = status;
    }

    public void showDetails() {
        System.out.println("ID: " + id);
        System.out.println("Patient's name: " + patient.getFullName());
        System.out.println("Doctor's name: " + doctor.getFullName());
        System.out.println("Date: " + date);
        if (diagnosis != null) System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Status: " + status.getStatus());
    }

    private void validatePatient(Patient patient) {
        if (patient == null) {
            throw new NullPointerException("Patient cannot be null.");
        }
    }

    private void validateDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new NullPointerException("Doctor cannot be null.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new NullPointerException("Date cannot be null.");
        }
    }

    private void validateDiagnosis(Diagnosis diagnosis) {
        if (diagnosis == null) {
            throw new NullPointerException("Diagnosis cannot be null.");
        }
    }

    public void changeDoctor(Doctor doctor) {
        if (status == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot change the doctor after the appointment is completed.");
        }

        validateDoctor(doctor);

        if (!this.doctor.equals(doctor)) {
            this.doctor = doctor;
        }
    }

    public void changeDate(LocalDate date) {
        if (status == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot reschedule a completed appointment.");
        }

        validateDate(date);

        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid date.");
        }

        this.date = date;
    }

    public void completeAppointment(Diagnosis diagnosis) {
        if (status == AppointmentStatus.COMPLETED
                || status == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("This appointment is already completed.");
        }

        validateDiagnosis(diagnosis);

        this.diagnosis = diagnosis;
        this.status = AppointmentStatus.COMPLETED;

        patient.addDiagnosis(diagnosis);
    }

    public void cancelAppointment() {
        if (status == AppointmentStatus.COMPLETED
                || status == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("This appointment is already completed or cancelled.");
        }

        this.status = AppointmentStatus.CANCELLED;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Appointment[" +
                "ID: " + id +
                " | Patient: " + patient.getFullName() +
                " | Doctor: " + doctor.getFullName() +
                " | Date: " + date +
                (diagnosis != null ? " | Diagnosis: " + diagnosis : "") +
                " | Status: " + status.getStatus() +
                "]";
    }

    @Override
    public UUID getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public boolean isCompleted() {
        return this.status == AppointmentStatus.COMPLETED;
    }

    public boolean isScheduled() {
        return this.status == AppointmentStatus.SCHEDULED;
    }
}
