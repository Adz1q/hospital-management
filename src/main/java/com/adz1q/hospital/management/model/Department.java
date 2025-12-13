package com.adz1q.hospital.management.model;

import java.util.Objects;
import java.util.UUID;

// Make additional hashmap with "name" as a key in repository to make sure every department is unique
// Make method in repo "findByNameOrCreate()"
public class Department {
    private final UUID id;
    private String name;

    public Department(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public void showDepartmentInfo() {
        System.out.println("------------------------- DEPARTMENT -------------------------");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("--------------------------------------------------------------");
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
