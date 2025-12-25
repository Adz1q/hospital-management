package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Rehabilitation extends Treatment {
    private final List<String> therapies;

    public Rehabilitation(
            LocalDate prescriptionDate,
            String description,
            List<String> therapies) {
        super(description, prescriptionDate);
        validateTherapies(therapies);
        this.therapies = therapies;
    }

    @Override
    public void showDetails() {
        System.out.println("ID: " + super.getId());
        System.out.println("Description: " + super.getDescription());
        System.out.println("Prescription Date: " + super.getPrescriptionDate());
        System.out.println("Therapies: " + therapies);
    }

    private void validateTherapies(List<String> therapies) {
        if (therapies == null) {
            throw new NullPointerException("Therapies cannot be null.");
        }

        if (therapies.isEmpty()) {
            throw new IllegalArgumentException("Therapies list cannot be empty.");
        }
    }

    @Override
    public String toString() {
        return "Rehabilitation[" +
                "ID: " + super.getId() +
                " | Description: " + super.getDescription() +
                " | Prescription Date: " + super.getPrescriptionDate() +
                " | Therapies: " + therapies +
                "]";
    }

    public List<String> getTherapies() {
        return Collections.unmodifiableList(therapies);
    }
}
