package com.adz1q.hospital.management;

import com.adz1q.hospital.management.model.Diagnosis;
import com.adz1q.hospital.management.model.Patient;

import java.time.LocalDate;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Piesek!");
        Patient patient = new Patient(
                "Mikołaj",
                "Sałek",
                LocalDate.of(2008, 11, 22));

        Diagnosis diagnosis = new Diagnosis("cascsa", new HashSet<>());
        patient.addDiagnosis(diagnosis);
        patient.showPersonalDetails();
        System.out.println(patient.getId());
    }
}
