package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Patient extends Person {
    private final List<Diagnosis> documentation;

    public Patient(
            String firstName,
            String lastName,
            LocalDate birthDate) {
        super(firstName, lastName, birthDate);
        this.documentation = new ArrayList<>();
    }

    public Patient(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String pesel) {
        super(firstName, lastName, birthDate, pesel);
        this.documentation = new ArrayList<>();
    }

    @Override
    public void showDetails() {
        System.out.println("ID: " + super.getId());
        System.out.println("First Name: " + super.getFirstName());
        System.out.println("Last Name: " + super.getLastName());
        System.out.println("Birth Date: " + super.getBirthDate());
        if (super.getPesel() != null) System.out.println("PESEL: " + super.getPesel());
        System.out.println("Documentation: " + documentation);
    }

    private void validateDiagnosis(Diagnosis diagnosis) {
        if (diagnosis == null) {
            throw new NullPointerException("Diagnosis cannot be null.");
        }
    }

    public void addDiagnosis(Diagnosis diagnosis) {
        validateDiagnosis(diagnosis);
        documentation.add(diagnosis);
    }

    @Override
    public String toString() {
        return "Patient[" +
                "ID: " + super.getId() +
                " | First Name: " + super.getFirstName() +
                " | Last Name: " + super.getLastName() +
                " | Birth Date: " + super.getBirthDate() +
                (super.getPesel() != null ? " | PESEL: " + super.getPesel() : "") +
                "]";
    }

    public List<Diagnosis> getDocumentation() {
        return Collections.unmodifiableList(documentation);
    }
}
