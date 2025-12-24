package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Surgery;

import java.nio.file.Path;
import java.time.LocalDate;
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

    public boolean existsByDoctorIdAndSurgeryDate(UUID doctorId, LocalDate surgeryDate) {
        return data.values()
                .stream()
                .anyMatch(surgery ->
                        surgery.getDoctor().getId().equals(doctorId)
                                && surgery.getSurgeryDate().equals(surgeryDate)
                                && surgery.isScheduled());
    }
}
