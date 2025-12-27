package com.adz1q.hospital.management.model;

public enum Specialization {
    PEDIATRICIAN        ("Pediatrician"),
    DERMATOLOGIST       ("Dermatologist"),
    SURGEON             ("Surgeon"),
    CARDIOLOGIST        ("Cardiologist"),
    NEUROLOGIST         ("Neurologist"),
    ORTHOPAEDIST        ("Orthopaedist"),
    ONCOLOGIST          ("Oncologist"),
    RADIOLOGIST         ("Radiologist"),
    GYNECOLOGIST        ("Gynecologist"),
    UROLOGIST           ("Urologist"),
    PSYCHIATRIST        ("Psychiatrist"),
    ENDOCRINOLOGIST     ("Endocrinologist"),
    GASTROENTEROLOGIST  ("Gastroenterologist"),
    PULMONOLOGIST       ("Pulmonologist"),
    NEPHROLOGIST        ("Nephrologist"),
    HEMATOLOGIST        ("Hematologist"),
    RHEUMATOLOGIST      ("Rheumatologist"),
    ALLERGOLOGIST       ("Allergologist"),
    OTOLARYNGOLOGIST    ("Otolaryngologist"),
    OPHTHALMOLOGIST     ("Ophthalmologist");

    private final String name;

    Specialization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
