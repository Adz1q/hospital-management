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
    public void showPersonalInfo() {
        System.out.println("------------------------- PATIENT -------------------------");
        System.out.println("ID: " + super.getId());
        System.out.println("First Name: " + super.getFirstName());
        System.out.println("Last Name: " + super.getLastName());
        System.out.println("Birth Date: " + super.getBirthDate());
        if (super.getPesel() != null) System.out.println("PESEL: " + super.getPesel());
        System.out.println("Department: " + department.getName());
        System.out.println("-----------------------------------------------------------");
    }

    public Department getDepartment() {
        return department;
    }

    public void changeDepartment(Department department) {
        if (department == null) {
            throw new NullPointerException("Department cannot be null.");
        }

        if (!this.department.equals(department)) {
            this.department = department;
        }
    }
}
