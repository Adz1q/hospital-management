package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Nurse;

import java.nio.file.Path;
import java.util.UUID;

public class NurseRepository extends FileRepository<UUID, Nurse> {
    public NurseRepository(Path filePath) {
        super(filePath);
        loadFromFile();
    }

    @Override
    protected void loadFromFile() {}

    @Override
    protected void saveToFile() {}
}
