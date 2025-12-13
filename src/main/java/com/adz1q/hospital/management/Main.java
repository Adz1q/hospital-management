package com.adz1q.hospital.management;

import com.adz1q.hospital.management.model.Patient;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Piesek!");
        Patient patient = new Patient("Mikołaj", "Sałek", LocalDate.now());
        patient.changeFirstName(null);
    }
}
