package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class Doctor extends Person {
    private final Set<Specialization> specializations;
    private boolean active;

    public Doctor(
            String firstName,
            String lastName,
            LocalDate birthDate,
            Set<Specialization> specializations) {
        super(firstName, lastName, birthDate);
        validateSpecializations(specializations);
        this.specializations = specializations;
        this.active = true;
    }

    public Doctor(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String pesel,
            Set<Specialization> specializations) {
        super(firstName, lastName, birthDate, pesel);
        validateSpecializations(specializations);
        this.specializations = specializations;
    }

    public Doctor(
            UUID id,
            String pesel,
            String firstName,
            String lastName,
            LocalDate birthDate,
            Set<Specialization> specializations,
            boolean active) {
        super(id, pesel, firstName, lastName, birthDate);
        this.specializations = specializations;
        this.active = active;
    }

    @Override
    public void showDetails() {
        System.out.println("ID: " + super.getId());
        System.out.println("First Name: " + super.getFirstName());
        System.out.println("Last Name: " + super.getLastName());
        System.out.println("Birth Date: " + super.getBirthDate());
        if (super.getPesel() != null) System.out.println("PESEL: " + super.getPesel());
        System.out.println("Specializations: " + specializations);
        System.out.println("Active: " + active);
    }

    private void validateSpecialization(Specialization specialization) {
        if (specialization == null) {
            throw new NullPointerException("Specialization cannot be null.");
        }
    }

    private void validateSpecializations(Set<Specialization> specializations) {
        if (specializations == null) {
            throw new NullPointerException("Specializations cannot be null.");
        }

        if (specializations.isEmpty()) {
            throw new IllegalArgumentException("Specializations must contain at least one specialization.");
        }
    }

    public void addSpecialization(Specialization specialization) {
        validateSpecialization(specialization);
        specializations.add(specialization);
    }

    public void rehire() {
        if (active) {
            throw new IllegalStateException("Doctor is already hired.");
        }

        this.active = true;
    }

    public void dismiss() {
        if (!active) {
            throw new IllegalStateException("Doctor is already dismissed.");
        }

        this.active = false;
    }

    @Override
    public String toString() {
        return "Doctor[" +
                "ID: " + super.getId() +
                " | First Name: " + super.getFirstName() +
                " | Last Name: " + super.getLastName() +
                " | Birth Date: " + super.getBirthDate() +
                (super.getPesel() != null ? " | PESEL: " + super.getPesel() : "") +
                " | Specializations: " + specializations +
                " | Active: " + active +
                "]";
    }

    public Set<Specialization> getSpecializations() {
        return Collections.unmodifiableSet(specializations);
    }

    public boolean isActive() {
        return active;
    }
}
