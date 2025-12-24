package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Appointment;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.UUID;

public class AppointmentRepository extends FileRepository<UUID, Appointment> {
    public AppointmentRepository(Path filePath) {
        super(filePath);
        loadFromFile();
    }

    @Override
    protected void loadFromFile() {}

    @Override
    protected void saveToFile() {}

    public boolean existsByDoctorIdAndDate(UUID doctorId, LocalDate date) {
        return data.values()
                .stream()
                .anyMatch(appointment ->
                        appointment.getDoctor().getId().equals(doctorId)
                                && appointment.getDate().equals(date)
                                && appointment.isScheduled());
    }
}
