package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public abstract class Treatment {
    private final UUID id;
    private final LocalDate prescriptionDate;
    private boolean completed;

    public Treatment(LocalDate prescriptionDate) {
        validatePrescriptionDate(prescriptionDate);
        this.id = UUID.randomUUID();
        this.prescriptionDate = prescriptionDate;
        this.completed = false;
    }

    public abstract void showTreatmentDetails();

    private void validatePrescriptionDate(LocalDate prescriptionDate) {
        if (prescriptionDate == null) {
            throw new NullPointerException("Prescription date cannot be null");
        }

        if (prescriptionDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Prescription date cannot be from future.");
        }
    }

    public abstract void completeTreatment();

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
    public String toString() {
        return "Treatment{" +
                "id=" + id +
                ", prescriptionDate=" + prescriptionDate +
                ", completed=" + completed +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getPrescriptionDate() {
        return prescriptionDate;
    }

    public boolean isCompleted() {
        return completed;
    }
}
