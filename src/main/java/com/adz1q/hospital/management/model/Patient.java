package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    private final List<String> documentation;

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
    public void showPersonalDetails() {
        System.out.println("------------------------- PATIENT -------------------------");
        System.out.println("ID: " + super.getId());
        System.out.println("First Name: " + super.getFirstName());
        System.out.println("Last Name: " + super.getLastName());
        System.out.println("Birth Date: " + super.getBirthDate());
        if (super.getPesel() != null) System.out.println("PESEL: " + super.getPesel());
        System.out.println("Documentation: " + documentation);
        System.out.println("-----------------------------------------------------------");
    }

    private void validateDiagnosis(String diagnosis) {
        if (diagnosis == null) {
            throw new NullPointerException("Diagnostics cannot be null.");
        }

        if (diagnosis.isBlank()) {
            throw new IllegalArgumentException("Diagnostics cannot be blank.");
        }
    }

    public void addDiagnosis(String diagnosis) {
        validateDiagnosis(diagnosis);
        documentation.add(diagnosis);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "person=" + super.toString() +
                ", documentation=" + documentation +
                '}';
    }

    public List<String> getDocumentation() {
        return documentation;
    }
}
