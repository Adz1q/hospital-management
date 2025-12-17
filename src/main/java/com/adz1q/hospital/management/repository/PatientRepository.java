package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Patient;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class PatientRepository extends FileRepository<UUID, Patient> {
    public PatientRepository(Path filePath) {
        super(filePath);
        loadFromFile();
    }

    @Override
    protected void loadFromFile() {}

    @Override
    protected void saveToFile() {}

    public Optional<Patient> findByPesel(String pesel) {
        return findAll().stream()
                .filter(patient -> patient.getPesel() != null)
                .filter(patient -> patient.getPesel().equals(pesel))
                .findFirst();
    }
}
