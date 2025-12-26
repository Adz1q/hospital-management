package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Rehabilitation;

import java.nio.file.Path;
import java.util.UUID;

public class RehabilitationRepository extends FileRepository<UUID, Rehabilitation> {
    public RehabilitationRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile() {}

    @Override
    public void saveToFile() {}
}
