package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Treatment;

import java.nio.file.Path;
import java.util.UUID;

public class TreatmentRepository extends FileRepository<UUID, Treatment> {
    public TreatmentRepository(Path filepath) {
        super(filepath);
        loadFromFile();
    }

    @Override
    protected void loadFromFile() {}

    @Override
    protected void saveToFile() {}
}
