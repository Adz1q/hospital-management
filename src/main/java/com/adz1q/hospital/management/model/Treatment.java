package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public abstract class Treatment implements Identifiable<UUID>, Describable{
    private final UUID id;
    private final String description;
    private final LocalDate prescriptionDate;

    public Treatment(
            String description,
            LocalDate prescriptionDate) {
        validateDescription(description);
        validatePrescriptionDate(prescriptionDate);
        this.id = UUID.randomUUID();
        this.description = description;
        this.prescriptionDate = prescriptionDate;
    }

    public Treatment(
            UUID id,
            String description,
            LocalDate prescriptionDate) {
        this.id = id;
        this.description = description;
        this.prescriptionDate = prescriptionDate;
    }

    private void validateDescription(String description) {
        if (description == null) {
            throw new NullPointerException("Description cannot be null.");
        }

        if (description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank.");
        }
    }

    private void validatePrescriptionDate(LocalDate prescriptionDate) {
        if (prescriptionDate == null) {
            throw new NullPointerException("Prescription date cannot be null");
        }

        if (prescriptionDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Prescription date cannot be from future.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Treatment treatment = (Treatment) o;
        return Objects.equals(id, treatment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getPrescriptionDate() {
        return prescriptionDate;
    }
}
