package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Medication extends Treatment {
    private final List<String> medicines;

    public Medication(
            String description,
            LocalDate prescriptionDate,
            List<String> medicines) {
        super(description, prescriptionDate);
        validateMedicines(medicines);
        this.medicines = medicines;
    }

    @Override
    public void showDetails() {
        System.out.println("ID: " + super.getId());
        System.out.println("Description: " + super.getDescription());
        System.out.println("Prescription Date: " + super.getPrescriptionDate());
        System.out.println("Medicines: " + medicines);
    }

    private void validateMedicines(List<String> medicines) {
        if (medicines == null) {
            throw new NullPointerException("Medicines cannot be null.");
        }

        if (medicines.isEmpty()) {
            throw new IllegalArgumentException("Medicines list cannot be empty.");
        }
    }

    @Override
    public String toString() {
        return "Medication[" +
                "ID: " + super.getId() +
                " | Description: " + super.getDescription() +
                " | Prescription Date: " + super.getPrescriptionDate() +
                " | Medicines: " + medicines +
                "]";
    }

    public List<String> getMedicines() {
        return Collections.unmodifiableList(medicines);
    }
}
