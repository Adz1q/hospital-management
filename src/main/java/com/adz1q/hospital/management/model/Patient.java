package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public void showPersonalInfo() {
        System.out.println("------------------------- PATIENT -------------------------");
        System.out.println("ID: " + super.getId());
        System.out.println("First Name: " + super.getFirstName());
        System.out.println("Last Name: " + super.getLastName());
        System.out.println("Birth Date: " + super.getBirthDate());
        if (super.getPesel() != null) System.out.println("PESEL: " + super.getPesel());
        System.out.println("Documentation: " + documentation);
        System.out.println("-----------------------------------------------------------");
    }

    public List<String> getDocumentation() {
        return documentation;
    }

    public void addDiagnosis(String diagnosis) {
        if (diagnosis == null) {
            throw new NullPointerException("Diagnostics cannot be null.");
        }

        if (diagnosis.isBlank()) {
            throw new IllegalArgumentException("Diagnostics cannot be blank.");
        }

        documentation.add(diagnosis);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "documentation=" + documentation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(documentation, patient.documentation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), documentation);
    }
}
