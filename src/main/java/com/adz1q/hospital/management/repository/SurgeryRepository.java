package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Surgery;

import java.nio.file.Path;
import java.util.UUID;

public class SurgeryRepository extends FileRepository<UUID, Surgery> {
    public SurgeryRepository(Path filePath) {
        super(filePath);
        loadFromFile();
    }

    @Override
    protected void loadFromFile() {}

    @Override
    protected void saveToFile() {}
}
