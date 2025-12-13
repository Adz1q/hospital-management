package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.List;

public class Doctor extends Person {
    private final List<Specialization> specializations;

    public Doctor(
            String firstName,
            String lastName,
            LocalDate birthDate,
            List<Specialization> specializations) {
        super(firstName, lastName, birthDate);
        this.specializations = specializations;
    }

    public Doctor(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String pesel,
            List<Specialization> specializations) {
        super(firstName, lastName, birthDate, pesel);
        this.specializations = specializations;
    }

    @Override
    public void showPersonalDetails() {
        System.out.println("------------------------- DOCTOR -------------------------");
        System.out.println("ID: " + super.getId());
        System.out.println("First Name: " + super.getFirstName());
        System.out.println("Last Name: " + super.getLastName());
        System.out.println("Birth Date: " + super.getBirthDate());
        if (super.getPesel() != null) System.out.println("PESEL: " + super.getPesel());
        System.out.println("Specializations: " + specializations);
        System.out.println("-----------------------------------------------------------");
    }

    private void validateSpecialization(Specialization specialization) {
        if (specialization == null) {
            throw new NullPointerException("Specialization cannot be null.");
        }
    }

    public void addSpecialization(Specialization specialization) {
        validateSpecialization(specialization);

        if (!specializations.contains(specialization)) {
            specializations.add(specialization);
        }
    }

    @Override
    public String toString() {
        return "Patient{" +
                "person=" + super.toString() +
                ", specializations=" + specializations +
                '}';
    }

    public List<Specialization> getSpecializations() {
        return specializations;
    }
}
