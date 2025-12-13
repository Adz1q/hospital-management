package com.adz1q.hospital.management.model;

public enum Specialization {
    PEDIATRICIAN    ("Pediatrician"),
    DERMATOLOGIST   ("Dermatologist"),
    SURGEON         ("Surgeon"),
    CARDIOLOGIST    ("Cardiologist"),
    NEUROLOGIST     ("Neurologist"),
    ORTHOPAEDIST    ("Orthopaedist"),
    ONCOLOGIST      ("Oncologist");

    private final String name;

    Specialization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
