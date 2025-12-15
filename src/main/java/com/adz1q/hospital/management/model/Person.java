package com.adz1q.hospital.management.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public abstract class Person {
    private final UUID id;
    private String pesel;
    private String firstName;
    private String lastName;
    private final LocalDate birthDate;

    public Person(
            String firstName,
            String lastName,
            LocalDate birthDate) {
        this(firstName, lastName, birthDate, null);
    }

    public Person(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String pesel) {
        validateFirstName(firstName);
        validateLastName(lastName);
        validateBirthDate(birthDate);

        if (pesel != null) {
            validatePesel(pesel);
        }

        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.pesel = pesel;
    }

    public abstract void showPersonalDetails();

    private void validatePesel(String pesel) {
        if (pesel == null) {
            throw new NullPointerException("PESEL cannot be null.");
        }

        if (pesel.isBlank()) {
            throw new IllegalArgumentException("PESEL cannot be blank.");
        }

        if (pesel.length() != 11) {
            throw new IllegalArgumentException("PESEL must contain exactly 11 digits.");
        }
    }

    private void validateFirstName(String firstName) {
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

    private void validateLastName(String lastName) {
        if (lastName == null) {
            throw new NullPointerException("Last name cannot be null.");
        }

        if (lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be blank.");
        }

        if (!lastName.matches("^[\\p{L} .'-]+$")) {
            throw new IllegalArgumentException("Last name contains invalid characters.");
        }
    }

    private void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new NullPointerException("Birth date cannot be null.");
        }

        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid birth date.");
        }
    }

    public void updatePesel(String pesel) {
        validatePesel(pesel);
        this.pesel = pesel;
    }

    public void changeFirstName(String firstName) {
        validateFirstName(firstName);
        this.firstName = firstName;
    }

    public void changeLastName(String lastName) {
        validateLastName(lastName);
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", pesel='" + pesel + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getPesel() {
        return pesel;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
