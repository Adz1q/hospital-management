package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Diagnosis;

import java.nio.file.Path;
import java.util.UUID;

public class DiagnosisRepository extends FileRepository<UUID, Diagnosis> {
    public DiagnosisRepository(Path filepath) {
        super(filepath);
        loadFromFile();
    }

    @Override
    protected void loadFromFile() {}

    @Override
    protected void saveToFile() {}
}
