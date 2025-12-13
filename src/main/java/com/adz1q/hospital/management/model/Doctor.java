package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
    public void showPersonalInfo() {
        System.out.println("------------------------- DOCTOR -------------------------");
        System.out.println("ID: " + super.getId());
        System.out.println("First Name: " + super.getFirstName());
        System.out.println("Last Name: " + super.getLastName());
        System.out.println("Birth Date: " + super.getBirthDate());
        if (super.getPesel() != null) System.out.println("PESEL: " + super.getPesel());
        System.out.println("Specializations: " + specializations);
        System.out.println("-----------------------------------------------------------");
    }

    public List<Specialization> getSpecializations() {
        return specializations;
    }

    public void addSpecialization(Specialization specialization) {
        if (specialization == null) {
            throw new NullPointerException("Specialization cannot be null.");
        }

        if (!specializations.contains(specialization)) {
            specializations.add(specialization);
        }
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "specializations=" + specializations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(specializations, doctor.specializations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specializations);
    }
}
