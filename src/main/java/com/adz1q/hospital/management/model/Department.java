package com.adz1q.hospital.management.model;

import java.util.Objects;
import java.util.UUID;

public class Department implements Identifiable<UUID> {
    private final UUID id;
    private String name;

    public Department(String name) {
        validateName(name);
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public void showDepartmentDetails() {
        System.out.println("------------------------- DEPARTMENT -------------------------");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("--------------------------------------------------------------");
    }

    private void validateName(String name) {
        if (name == null) {
            throw new NullPointerException("Department name cannot be null.");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("Department name cannot be blank.");
        }
    }

    public void rename(String name) {
        validateName(name);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
