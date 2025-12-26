package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Therapy;

import java.nio.file.Path;
import java.util.UUID;

public class TherapyRepository extends FileRepository<UUID, Therapy> {
    public TherapyRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile() {}

    @Override
    public void saveToFile() {}
}
