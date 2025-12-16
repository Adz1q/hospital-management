package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Department;

import java.nio.file.Path;
import java.util.UUID;

public class DepartmentRepository extends FileRepository<UUID, Department> {
    public DepartmentRepository(Path filepath) {
        super(filepath);
        loadFromFile();
    }

    @Override
    protected void loadFromFile() {}

    @Override
    protected void saveToFile() {}
}
