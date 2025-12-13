package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Appointment {
    private final UUID id;
    private final Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private String diagnosis;
    private boolean completed;

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
        this.diagnosis = null;
        this.completed = false;
    }

    public void showAppointmentDetails() {
        System.out.println("------------------------- APPOINTMENT -------------------------");
        System.out.println("ID: " + id);
        System.out.println("Patient's name: " + patient.getFullName());
        System.out.println("Doctor's name: " + doctor.getFullName());
        System.out.println("Date: " + date);
        if (diagnosis != null) System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Completed: " + completed);
        System.out.println("---------------------------------------------------------------");
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

    private void validateDiagnosis(String diagnosis) {
        if (diagnosis == null) {
            throw new NullPointerException("Diagnosis cannot be null.");
        }

        if (diagnosis.isBlank()) {
            throw new IllegalArgumentException("Diagnosis cannot be blank.");
        }
    }

    public void changeDoctor(Doctor doctor) {
        if (completed) {
            throw new IllegalStateException("Cannot change the doctor after the appointment is completed.");
        }

        validateDoctor(doctor);

        if (!this.doctor.equals(doctor)) {
            this.doctor = doctor;
        }
    }

    public void changeDate(LocalDate date) {
        if (completed) {
            throw new IllegalStateException("Cannot reschedule a completed appointment.");
        }

        validateDate(date);

        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid date.");
        }

        this.date = date;
    }

    public void completeAppointment(String diagnosis) {
        if (completed) {
            throw new IllegalStateException("This appointment is already completed.");
        }

        validateDiagnosis(diagnosis);

        this.diagnosis = diagnosis;
        this.completed = true;
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
        return "Appointment{" +
                "id=" + id +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", date=" + date +
                ", diagnosis='" + diagnosis + '\'' +
                ", completed=" + completed +
                '}';
    }

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

    public String getDiagnosis() {
        return diagnosis;
    }

    public boolean isCompleted() {
        return completed;
    }
}
