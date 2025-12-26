package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Doctor;
import com.adz1q.hospital.management.model.Surgery;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SurgeryRepository extends FileRepository<UUID, Surgery> {
    public SurgeryRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile(Map<UUID, Doctor> doctors) {}

    @Override
    public void saveToFile() {}

    public boolean existsByDoctorIdAndSurgeryDate(UUID doctorId, LocalDate surgeryDate) {
        return data.values()
                .stream()
                .anyMatch(surgery ->
                        surgery.getDoctor().getId().equals(doctorId)
                                && surgery.getSurgeryDate().equals(surgeryDate)
                                && surgery.isScheduled());
    }

    public List<Surgery> findByDoctorId(UUID doctorId) {
        return data.values()
                .stream()
                .filter(surgery -> surgery.getDoctor().getId().equals(doctorId))
                .toList();
    }
}
