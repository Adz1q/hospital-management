package com.adz1q.hospital.management.model;

import java.util.*;

public class Diagnosis {
    private final UUID id;
    private String description;
    private final Set<Treatment> treatments;

    public Diagnosis(String description) {
        validateDescription(description);
        this.id = UUID.randomUUID();
        this.description = description;
        this.treatments = new HashSet<>();
    }

    public void showDiagnosisDetails() {
        System.out.println("------------------------- DIAGNOSIS -------------------------");
        System.out.println("ID: " + id);
        System.out.println("Description: " + description);
        System.out.println("Treatments: " + treatments);
        System.out.println("-------------------------------------------------------------");
    }

    private void validateDescription(String description) {
        if (description == null) {
            throw new NullPointerException("Description cannot be null.");
        }

        if (description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank.");
        }
    }

    private void validateTreatment(Treatment treatment) {
        if (treatment == null) {
            throw new NullPointerException("Treatment cannot be null.");
        }
    }

    public void changeDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    public void addTreatment(Treatment treatment) {
        validateTreatment(treatment);
        treatments.add(treatment);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Diagnosis diagnosis = (Diagnosis) o;
        return Objects.equals(id, diagnosis.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", treatments=" + treatments +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Set<Treatment> getTreatments() {
        return Collections.unmodifiableSet(treatments);
    }
}
