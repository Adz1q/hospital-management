package com.adz1q.hospital.management.model;

import java.util.Objects;
import java.util.UUID;

public class Room {
    private final UUID id;
    private String name;
    private Department department;

    public Room(
            String name,
            Department department) {
        validateName(name);
        validateDepartment(department);
        this.id = UUID.randomUUID();
        this.name = name;
        this.department = department;
    }

    public void showRoomDetails() {
        System.out.println("------------------------- ROOM -------------------------");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
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

    // check if the dept. already exists
    private void validateDepartment(Department department) {
        if (department == null) {
            throw new NullPointerException("Department cannot be null.");
        }
    }

    public void rename(String name) {
        validateName(name);
        this.name = name;
    }

    // if the dept. had list of rooms, need to delete this room from the dept's room list
    public void changeDepartment(Department department) {
        validateDepartment(department);

        if (!this.department.equals(department)) {
            this.department = department;
        }
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
                ", department=" + department.getName() +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }
}
