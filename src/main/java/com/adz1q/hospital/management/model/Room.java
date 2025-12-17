package com.adz1q.hospital.management.model;

import java.util.*;

public class Room implements Identifiable<UUID> {
    private final UUID id;
    private String name;
    private Department department;
    private final Set<Patient> patients;
    private int availableSlots;

    public Room(
            String name,
            Department department,
            int availableSlots) {
        validateName(name);
        validateDepartment(department);
        validateAvailableSlots(availableSlots);
        this.id = UUID.randomUUID();
        this.name = name;
        this.department = department;
        this.patients = new HashSet<>();
        this.availableSlots = availableSlots;
    }

    public void showRoomDetails() {
        System.out.println("------------------------- ROOM -------------------------");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Department: " + department);
        System.out.println("Patients: " + patients);
        System.out.println("Available Slots: " + availableSlots);
        System.out.println("---------------------------------------------------------");
    }

    private void validateName(String name) {
        if (name == null) {
            throw new NullPointerException("Room name cannot be null.");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("Room name cannot be blank.");
        }
    }

    private void validateDepartment(Department department) {
        if (department == null) {
            throw new NullPointerException("Department cannot be null.");
        }
    }

    private void validatePatient(Patient patient) {
        if (patient == null) {
            throw new NullPointerException("Patient cannot be null.");
        }
    }

    private void validateAvailableSlots(int availableSlots) {
        if (availableSlots < 1) {
            throw new IllegalArgumentException("Room must contain at least one available slot.");
        }
    }

    public void rename(String name) {
        validateName(name);
        this.name = name;
    }

    public void changeDepartment(Department department) {
        validateDepartment(department);

        if (!this.department.equals(department)) {
            this.department = department;
        }
    }

    public void addPatient(Patient patient) {
        validatePatient(patient);

        if (availableSlots > 0) {
            patients.add(patient);
            availableSlots--;
        }
    }

    public void removePatient(Patient patient) {
        validatePatient(patient);
        patients.remove(patient);
        availableSlots++;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department=" + department +
                ", patients=" + patients +
                ", availableSlots=" + availableSlots +
                '}';
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }

    public Set<Patient> getPatients() {
        return Collections.unmodifiableSet(patients);
    }

    public int getAvailableSlots() {
        return availableSlots;
    }
}
