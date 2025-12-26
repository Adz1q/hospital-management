package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.*;

import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

public class DiagnosisRepository extends FileRepository<UUID, Diagnosis> {
    public DiagnosisRepository(Path filepath) {
        super(filepath);
    }

    public void loadFromFile(
            Map<UUID, Surgery> surgeries,
            Map<UUID, Medication> medications,
            Map<UUID, Therapy> therapies,
            Map<UUID, Rehabilitation> rehabilitations) {}

    @Override
    public void saveToFile() {}
}
