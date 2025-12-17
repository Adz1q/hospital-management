package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Medication;

import java.nio.file.Path;
import java.util.UUID;

public class MedicationRepository extends FileRepository<UUID, Medication> {
    public MedicationRepository(Path filePath) {
        super(filePath);
        loadFromFile();
    }

    @Override
    protected void loadFromFile() {}

    @Override
    protected void saveToFile() {}
}
