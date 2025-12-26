package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Appointment;
import com.adz1q.hospital.management.model.Diagnosis;
import com.adz1q.hospital.management.model.Doctor;
import com.adz1q.hospital.management.model.Patient;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class AppointmentRepository extends FileRepository<UUID, Appointment> {
    public AppointmentRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile(
            Map<UUID, Patient> patients,
            Map<UUID, Doctor> doctors,
            Map<UUID, Diagnosis> diagnoses) {}

    @Override
    public void saveToFile() {}

    public boolean existsByDoctorIdAndDate(UUID doctorId, LocalDate date) {
        return data.values()
                .stream()
                .anyMatch(appointment ->
                        appointment.getDoctor().getId().equals(doctorId)
                                && appointment.getDate().equals(date)
                                && appointment.isScheduled());
    }
}
