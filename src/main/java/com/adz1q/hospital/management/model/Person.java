package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public abstract class Person {
    private final UUID id;
    private String firstName;
    private String lastName;
    private final LocalDate birthDate;
    private String pesel;

    public Person(
            String firstName,
            String lastName,
            LocalDate birthDate) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.pesel = null;
    }

    public Person(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String pesel) {
        this(firstName, lastName, birthDate);
        this.pesel = pesel;
    }

    public void showPersonalInfo() {
        System.out.println("------------------------- PERSON -------------------------");
        System.out.println("ID: " + id);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Birth Date: " + birthDate);
        if (pesel != null) System.out.println("PESEL: " + pesel);
        System.out.println("-----------------------------------------------------------");
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new NullPointerException("First name cannot be null.");
        }

        if (firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be blank.");
        }

        if (!firstName.matches("^[\\p{L} .'-]+$")) {
            throw new IllegalArgumentException("First name must contain only letters, spaces, hyphens or apostrophes.");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            throw new NullPointerException("Last name cannot be null.");
        }

        if (lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be blank.");
        }

        if (!lastName.matches("^[\\p{L} .'-]+$")) {
            throw new IllegalArgumentException("Last name contains invalid characters.");
        }

        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        if (pesel == null) {
            throw new NullPointerException("PESEL cannot be null.");
        }

        if (pesel.isBlank()) {
            throw new IllegalArgumentException("PESEL cannot be blank.");
        }

        if (pesel.length() != 11) {
            throw new IllegalArgumentException("PESEL must contain exactly 11 letters.");
        }

        this.pesel = pesel;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", pesel='" + pesel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id)
                && Objects.equals(firstName, person.firstName)
                && Objects.equals(lastName, person.lastName)
                && Objects.equals(birthDate, person.birthDate)
                && Objects.equals(pesel, person.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, pesel);
    }
}
