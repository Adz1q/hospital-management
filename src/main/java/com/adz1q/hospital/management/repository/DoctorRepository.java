package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Doctor;

import java.nio.file.Path;
import java.util.UUID;

public class DoctorRepository extends FileRepository<UUID, Doctor> {
    public DoctorRepository(Path filePath) {
        super(filePath);
        loadFromFile();
    }

    @Override
    protected void loadFromFile() {}

    @Override
    protected void saveToFile() {}
}
