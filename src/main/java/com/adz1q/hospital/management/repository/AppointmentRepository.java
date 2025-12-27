package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.*;
import com.adz1q.hospital.management.util.Logger;
import org.apache.commons.csv.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class AppointmentRepository extends FileRepository<UUID, Appointment> {
    private static final CSVFormat CSV_READ_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "date", "status", "patient_id", "doctor_id", "diagnosis_id")
            .setSkipHeaderRecord(true)
            .build();
    private static final CSVFormat CSV_WRITE_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "date", "status", "patient_id", "doctor_id", "diagnosis_id")
            .build();

    public AppointmentRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile(
            Map<UUID, Patient> patients,
            Map<UUID, Doctor> doctors,
            Map<UUID, Diagnosis> diagnoses) {
        if (!Files.exists(filePath)) {
            Logger.warn("Appointment file does not exist. Starting with an empty repository.");
            return;
        }

        try {
            try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
                 CSVParser parser = CSVParser.parse(reader, CSV_READ_FORMAT)) {
                for (CSVRecord record : parser) {
                    try {
                        String idStr = record.get("id");
                        String dateStr = record.get("date");
                        String statusStr = record.get("status");
                        String patientIdStr = record.get("patient_id");
                        String doctorIdStr = record.get("doctor_id");
                        String diagnosisIdStr = record.get("diagnosis_id");

                        UUID id = UUID.fromString(idStr);
                        LocalDate date = LocalDate.parse(dateStr);
                        AppointmentStatus status = AppointmentStatus.valueOf(statusStr.toUpperCase());
                        Patient patient = null;
                        Doctor doctor = null;
                        Diagnosis diagnosis = null;

                        if (patientIdStr != null && !patientIdStr.isBlank()) {
                            UUID patientId = UUID.fromString(patientIdStr);
                            patient = patients.get(patientId);
                        }

                        if (doctorIdStr != null && !doctorIdStr.isBlank()) {
                            UUID doctorId = UUID.fromString(doctorIdStr);
                            doctor = doctors.get(doctorId);
                        }

                        if (diagnosisIdStr != null && !diagnosisIdStr.isBlank()) {
                            UUID diagnosisId = UUID.fromString(diagnosisIdStr);
                            diagnosis = diagnoses.get(diagnosisId);
                        }

                        Appointment appointment = new Appointment(
                                id,
                                patient,
                                doctor,
                                date,
                                diagnosis,
                                status);
                        data.put(id, appointment);
                    } catch (Exception e) {
                        Logger.warn("Failed to parse appointment record: " + record.toString() + ". Error: " + e.getMessage());
                    }
                }

                Logger.info("Appointments loaded successfully. Count: " + data.size());
            }
        } catch (IOException e) {
            Logger.warn("Failed to load appointments from file: " + filePath);
            throw new UncheckedIOException("Failed to load appointments from file: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveToFile() {
        Path tempFile = filePath.resolveSibling(filePath.getFileName() + ".tmp");

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
                 CSVPrinter printer = new CSVPrinter(writer, CSV_WRITE_FORMAT)) {
                for (Appointment appointment : data.values()) {
                    printer.printRecord(
                            appointment.getId(),
                            appointment.getDate(),
                            appointment.getStatus(),
                            appointment.getPatient() != null ? appointment.getPatient().getId() : "",
                            appointment.getDoctor() != null ? appointment.getDoctor().getId() : "",
                            appointment.getDiagnosis() != null ? appointment.getDiagnosis().getId() : ""
                    );
                }

                Files.move(tempFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                Logger.info("Appointments saved successfully. Count: " + data.size());
            }
        } catch (IOException e) {
            Logger.warn("Failed to save appointments to file: " + e.getMessage());

            try {
                Files.deleteIfExists(tempFile);
            } catch (IOException ignored) {
                Logger.warn("Failed to cleanup temp file: " + ignored.getMessage());
            }

            throw new UncheckedIOException("Failed to save appointments to file: " + e.getMessage(), e);
        }
    }

    public boolean existsByDoctorIdAndDate(UUID doctorId, LocalDate date) {
        return data.values()
                .stream()
                .anyMatch(appointment ->
                        appointment.getDoctor().getId().equals(doctorId)
                                && appointment.getDate().equals(date)
                                && appointment.isScheduled());
    }
}
