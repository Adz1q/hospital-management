package com.adz1q.hospital.management.model;

import java.util.Objects;
import java.util.UUID;

public class Department implements Identifiable<UUID>, Describable {
    private final UUID id;
    private String name;
    private boolean active;

    public Department(String name) {
        validateName(name);
        this.id = UUID.randomUUID();
        this.name = name;
        this.active = true;
    }

    public Department(
            UUID id,
            String name,
            boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public void showDetails() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Active: " + active);
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
        if (!isActive()) {
            throw new IllegalStateException("Cannot rename a closed department.");
        }

        validateName(name);
        this.name = name;
    }

    public void close() {
        if (!active) {
            throw new IllegalStateException("Department is already closed.");
        }

        this.active = false;
    }

    public void reopen() {
        if (active) {
            throw new IllegalStateException("Department is already active.");
        }

        this.active = true;
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
        return "Department[" +
                "ID: " + id +
                " | Name: " + name +
                " | Active: " + active +
                "]";
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }
}
