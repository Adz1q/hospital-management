package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Doctor;
import com.adz1q.hospital.management.model.Surgery;
import com.adz1q.hospital.management.model.SurgeryStatus;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SurgeryRepository extends FileRepository<UUID, Surgery> {
    private static final CSVFormat CSV_READ_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "description", "prescription_date", "surgery_date", "status", "doctor_id")
            .setSkipHeaderRecord(true)
            .build();
    private static final CSVFormat CSV_WRITE_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "description", "prescription_date", "surgery_date", "status", "doctor_id")
            .build();

    public SurgeryRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile(Map<UUID, Doctor> doctors) {
        if (!Files.exists(filePath)) {
            Logger.warn("Surgery file does not exist. Starting with an empty repository.");
            return;
        }

        try {
            try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
                 CSVParser parser = CSVParser.parse(reader, CSV_READ_FORMAT)) {
                for (CSVRecord record : parser) {
                    try {
                        String idStr = record.get("id");
                        String description = record.get("description");
                        String prescriptionDateStr = record.get("prescription_date");
                        String surgeryDateStr = record.get("surgery_date");
                        String statusStr = record.get("status");
                        String doctorIdStr = record.get("doctor_id");

                        UUID id = UUID.fromString(idStr);
                        LocalDate prescriptionDate = LocalDate.parse(prescriptionDateStr);
                        LocalDate surgeryDate = LocalDate.parse(surgeryDateStr);
                        SurgeryStatus status = SurgeryStatus.valueOf(statusStr.toUpperCase());

                        Doctor doctor = null;
                        if (doctorIdStr != null && !doctorIdStr.isBlank()) {
                            UUID doctorId = UUID.fromString(doctorIdStr);
                            doctor = doctors.get(doctorId);
                        }

                        Surgery surgery = new Surgery(
                                id,
                                description,
                                prescriptionDate,
                                doctor,
                                surgeryDate,
                                status);
                        data.put(id, surgery);
                    } catch (Exception e) {
                        Logger.error("Skipping corrupted record at line " + record.getRecordNumber() + ": " + e.getMessage());
                    }
                }

                Logger.info("Surgeries loaded successfully. Count: " + data.size());
            }
        } catch (IOException e) {
            Logger.error("Failed to load surgeries from file: " + e.getMessage());
            throw new UncheckedIOException("Failed to load surgeries from file: " + filePath, e);
        }
    }

    @Override
    public void saveToFile() {
        Path tempFile = filePath.resolveSibling(filePath.getFileName() + ".tmp");

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
                 CSVPrinter printer = new CSVPrinter(writer, CSV_WRITE_FORMAT)) {
                for (Surgery surgery : data.values()) {
                    printer.printRecord(
                            surgery.getId(),
                            surgery.getDescription(),
                            surgery.getPrescriptionDate(),
                            surgery.getSurgeryDate(),
                            surgery.getStatus(),
                            surgery.getDoctor() != null ? surgery.getDoctor().getId() : "");
                }
            }

            Files.move(tempFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            Logger.info("Surgeries saved successfully. Count: " + data.size());
        } catch (IOException e) {
            Logger.warn("Failed to save surgeries to file: " + e.getMessage());

            try {
                Files.deleteIfExists(tempFile);
            } catch (IOException ignored) {
                Logger.warn("Failed to cleanup temp file: " + ignored.getMessage());
            }

            throw new UncheckedIOException("Failed to save surgeries to file: " + filePath, e);
        }
    }

    public boolean existsByDoctorIdAndSurgeryDate(UUID doctorId, LocalDate surgeryDate) {
        return data.values()
                .stream()
                .anyMatch(surgery ->
                        surgery.getDoctor() != null
                                && surgery.getDoctor().getId().equals(doctorId)
                                && surgery.getSurgeryDate().equals(surgeryDate)
                                && surgery.isScheduled());
    }

    public List<Surgery> findByDoctorId(UUID doctorId) {
        return data.values()
                .stream()
                .filter(surgery -> surgery.getDoctor() != null)
                .filter(surgery -> surgery.getDoctor().getId().equals(doctorId))
                .toList();
    }
}
