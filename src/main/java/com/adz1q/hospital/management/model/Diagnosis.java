package com.adz1q.hospital.management.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Diagnosis implements Identifiable<UUID> {
    private final UUID id;
    private final String description;
    private final Set<Treatment> treatments;

    public Diagnosis(
            String description,
            Set<Treatment> treatments) {
        validateDescription(description);
        validateTreatments(treatments);
        this.id = UUID.randomUUID();
        this.description = description;
        this.treatments = treatments;
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

    private void validateTreatments(Set<Treatment> treatments) {
        if (treatments == null) {
            throw new NullPointerException("Treatments cannot be null.");
        }
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

    @Override
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
