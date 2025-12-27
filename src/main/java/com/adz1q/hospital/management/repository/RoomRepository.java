package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Department;
import com.adz1q.hospital.management.model.Patient;
import com.adz1q.hospital.management.model.Room;
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
import java.util.*;
import java.util.stream.Collectors;

public class RoomRepository extends FileRepository<UUID, Room> {
    private static final CSVFormat CSV_READ_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "name", "available_slots", "department_id", "patient_ids")
            .setSkipHeaderRecord(true)
            .build();
    private static final CSVFormat CSV_WRITE_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "name", "available_slots", "department_id", "patient_ids")
            .build();

    public RoomRepository(Path filepath) {
        super(filepath);
    }

    public void loadFromFile(
            Map<UUID, Department> departments,
            Map<UUID, Patient> patients) {
        if (!Files.exists(filePath)) {
            Logger.warn("Room file does not exist. Starting with an empty repository.");
            return;
        }

        try {
            try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
                 CSVParser parser = CSVParser.parse(reader, CSV_READ_FORMAT)) {
                for (CSVRecord record : parser) {
                    try {
                        String idStr = record.get("id");
                        String name = record.get("name");
                        String availableSlotsStr = record.get("available_slots");
                        String departmentIdStr = record.get("department_id");
                        String patientIdsStr = record.get("patient_ids");

                        UUID id = UUID.fromString(idStr);
                        int availableSlots = Integer.parseInt(availableSlotsStr);
                        Department department = null;
                        Set<Patient> roomPatients = new HashSet<>();

                        if (departmentIdStr != null && !departmentIdStr.isBlank()) {
                            UUID departmentId = UUID.fromString(departmentIdStr);
                            department = departments.get(departmentId);
                        }

                        if (patientIdsStr != null && !patientIdsStr.isBlank()) {
                            String[] patientIds = patientIdsStr.split("\\|");

                            for (String patientIdStr : patientIds) {
                                UUID patientId = UUID.fromString(patientIdStr);
                                Patient patient = patients.get(patientId);

                                if (patient != null) {
                                    roomPatients.add(patient);
                                }
                            }
                        }

                        Room room = new Room(
                                id,
                                name,
                                department,
                                roomPatients,
                                availableSlots);
                        data.put(id, room);
                    } catch (Exception e) {
                        Logger.error("Skipping corrupted record at line " + record.getRecordNumber() + ": " + e.getMessage());
                    }
                }

                Logger.info("Rooms loaded successfully. Count: " + data.size());
            }
        } catch (IOException e) {
            Logger.warn("Failed to load rooms from file: " + filePath);
            throw new UncheckedIOException("Failed to load rooms from file: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveToFile() {
        Path tempFile = filePath.resolveSibling(filePath.getFileName() + ".tmp");

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
                 CSVPrinter csvPrinter = CSV_WRITE_FORMAT.print(writer)) {
                for (Room room : data.values()) {
                    String departmentIdStr = room.getDepartment() != null ? room.getDepartment().getId().toString() : "";
                    String patientIdsStr = room.getPatients()
                            .stream()
                            .map(Patient::getId)
                            .map(UUID::toString)
                            .collect(Collectors.joining("|"));

                    csvPrinter.printRecord(
                            room.getId().toString(),
                            room.getName(),
                            room.getAvailableSlots(),
                            departmentIdStr,
                            patientIdsStr
                    );
                }
            }

            Files.move(tempFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            Logger.info("Rooms saved successfully. Count: " + data.size());
        } catch (IOException e) {
            Logger.warn("Failed to save rooms to file: " + filePath);

            try {
                Files.deleteIfExists(tempFile);
            } catch (IOException ignored) {
                Logger.warn("Failed to cleanup temp file: " + ignored.getMessage());
            }

            throw new UncheckedIOException("Failed to save rooms to file: " + e.getMessage(), e);
        }
    }

    public List<Room> findByDepartmentId(UUID departmentId) {
        return data.values()
                .stream()
                .filter(room -> room.getDepartment() != null)
                .filter(room -> room.getDepartment().getId().equals(departmentId))
                .toList();
    }

    public Set<Patient> findPatientsInRoom(UUID roomId) {
        return data.get(roomId).getPatients();
    }
}
