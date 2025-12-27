package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.UUID;

public class Nurse extends Person {
    private Department department;
    private boolean active;

    public Nurse(
            String firstName,
            String lastName,
            LocalDate birthDate,
            Department department) {
        super(firstName, lastName, birthDate);
        validateDepartment(department);
        this.department = department;
        this.active = true;
    }

    public Nurse(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String pesel,
            Department department) {
        super(firstName, lastName, birthDate, pesel);
        validateDepartment(department);
        this.department = department;
    }

    public Nurse(
            UUID id,
            String pesel,
            String firstName,
            String lastName,
            LocalDate birthDate,
            Department department,
            boolean active) {
        super(id, pesel, firstName, lastName, birthDate);
        this.department = department;
        this.active = active;
    }

    @Override
    public void showDetails() {
        System.out.println("ID: " + super.getId());
        System.out.println("First Name: " + super.getFirstName());
        System.out.println("Last Name: " + super.getLastName());
        System.out.println("Birth Date: " + super.getBirthDate());
        if (super.getPesel() != null) System.out.println("PESEL: " + super.getPesel());
        System.out.println("Department: " + department);
        System.out.println("Active: " + active);
    }

    private void validateDepartment(Department department) {
        if (department == null) {
            throw new NullPointerException("Department cannot be null.");
        }

        if (!department.isActive()) {
            throw new IllegalArgumentException("Assigned department must be active.");
        }
    }

    public void changeDepartment(Department department) {
        validateDepartment(department);

        if (!this.department.equals(department)) {
            this.department = department;
        }
    }

    public void rehire() {
        if (active) {
            throw new IllegalStateException("Nurse is already hired.");
        }

        this.active = true;
    }

    public void dismiss() {
        if (!active) {
            throw new IllegalStateException("Nurse is already dismissed.");
        }

        this.active = false;
    }

    @Override
    public String toString() {
        return "Nurse[" +
                "ID: " + super.getId() +
                " | First Name: " + super.getFirstName() +
                " | Last Name: " + super.getLastName() +
                " | Birth Date: " + super.getBirthDate() +
                (super.getPesel() != null ? " | PESEL: " + super.getPesel() : "") +
                " | Department: " + department.getName() +
                " | Active: " + active +
                "]";
    }

    public Department getDepartment() {
        return department;
    }

    public boolean isActive() {
        return active;
    }
}
