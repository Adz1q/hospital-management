package com.adz1q.hospital.management.model;

import java.time.LocalDate;

public class Therapy extends Treatment {
    private final String name;

    public Therapy(
            String description,
            LocalDate prescriptionDate,
            String name) {
        super(description, prescriptionDate);
        validateName(name);
        this.name = name;
    }

    @Override
    public void showDetails() {
        System.out.println("ID: " + super.getId());
        System.out.println("Name: " + name);
        System.out.println("Description: " + super.getDescription());
        System.out.println("Prescription Date: " + super.getPrescriptionDate());
    }

    private void validateName(String name) {
        if (name == null) {
            throw new NullPointerException("Therapy name cannot be null.");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("Therapy name cannot be blank.");
        }
    }

    @Override
    public String toString() {
        return "Therapy[" +
                "ID: " + super.getId() +
                " | Name: " + name +
                " | Description: " + super.getDescription() +
                " | Prescription Date: " + super.getPrescriptionDate() +
                "]";
    }

    public String getName() {
        return name;
    }
}
