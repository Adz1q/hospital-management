package com.adz1q.hospital.management.model;

import com.adz1q.hospital.management.util.PeselValidator;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public abstract class Person implements Identifiable<UUID>, Describable {
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

    public Person(
            UUID id,
            String pesel,
            String firstName,
            String lastName,
            LocalDate birthDate) {
        this.id = id;
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    private void validatePesel(String pesel) {
        PeselValidator.isValid(pesel);
    }

    private void validateFirstName(String firstName) {
        if (firstName == null) {
            throw new NullPointerException("First name cannot be null.");
        }

        if (firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be blank.");
        }
    }

    private void validateLastName(String lastName) {
        if (lastName == null) {
            throw new NullPointerException("Last name cannot be null.");
        }

        if (lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be blank.");
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
