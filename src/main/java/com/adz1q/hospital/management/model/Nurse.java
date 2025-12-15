package com.adz1q.hospital.management.model;

import java.time.LocalDate;

public class Nurse extends Person {
    private Department department;

    public Nurse(
            String firstName,
            String lastName,
            LocalDate birthDate,
            Department department) {
        super(firstName, lastName, birthDate);
        this.department = department;
    }

    public Nurse(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String pesel,
            Department department) {
        super(firstName, lastName, birthDate, pesel);
        this.department = department;
    }

    @Override
    public void showPersonalDetails() {
        System.out.println("------------------------- NURSE ---------------------------");
        System.out.println("ID: " + super.getId());
        System.out.println("First Name: " + super.getFirstName());
        System.out.println("Last Name: " + super.getLastName());
        System.out.println("Birth Date: " + super.getBirthDate());
        if (super.getPesel() != null) System.out.println("PESEL: " + super.getPesel());
        System.out.println("Department: " + department.getName());
        System.out.println("-----------------------------------------------------------");
    }

    private void validateDepartment(Department department) {
        if (department == null) {
            throw new NullPointerException("Department cannot be null.");
        }
    }

    public void changeDepartment(Department department) {
        validateDepartment(department);

        if (!this.department.equals(department)) {
            this.department = department;
        }
    }

    @Override
    public String toString() {
        return "Patient{" +
                "person=" + super.toString() +
                ", department=" + department +
                '}';
    }

    public Department getDepartment() {
        return department;
    }
}
