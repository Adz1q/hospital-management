package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Department;
import com.adz1q.hospital.management.model.Nurse;
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
import java.util.Optional;
import java.util.UUID;

public class NurseRepository extends FileRepository<UUID, Nurse> {
    private static final CSVFormat CSV_READ_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "pesel", "first_name", "last_name", "birth_date", "active", "department_id")
            .setSkipHeaderRecord(true)
            .build();
    private static final CSVFormat CSV_WRITE_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "pesel", "first_name", "last_name", "birth_date", "active", "department_id")
            .build();

    public NurseRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile(Map<UUID, Department> departments) {
        if (!Files.exists(filePath)) {
            Logger.warn("Nurse file does not exist. Starting with an empty repository.");
            return;
        }

        try {
            try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
                 CSVParser parser = CSVParser.parse(reader, CSV_READ_FORMAT)) {
                for (CSVRecord record : parser) {
                    try {
                        String idStr = record.get("id");
                        String pesel = record.get("pesel");
                        String firstName = record.get("first_name");
                        String lastName = record.get("last_name");
                        String birthDateStr = record.get("birth_date");
                        String activeStr = record.get("active");
                        String departmentIdStr = record.get("department_id");

                        UUID id = UUID.fromString(idStr);
                        LocalDate birthDate = LocalDate.parse(birthDateStr);
                        boolean active = Boolean.parseBoolean(activeStr);
                        Department department = null;

                        if (departmentIdStr != null && !departmentIdStr.isBlank()) {
                            UUID departmentId = UUID.fromString(departmentIdStr);
                            department = departments.get(departmentId);
                        }

                        Nurse nurse = new Nurse(
                                id,
                                pesel,
                                firstName,
                                lastName,
                                birthDate,
                                department,
                                active);
                        data.put(id, nurse);
                    } catch (Exception e) {
                        Logger.error("Skipping corrupted record at line " + record.getRecordNumber() + ": " + e.getMessage());
                    }
                }

                Logger.info("Nurses loaded successfully. Count: " + data.size());
            }
        } catch (IOException e) {
            Logger.warn("Failed to load nurses from file: " + e.getMessage());
            throw new UncheckedIOException("Failed to load nurses from file: " + filePath, e);
        }
    }

    @Override
    public void saveToFile() {
        Path tempFile = filePath.resolveSibling(filePath.getFileName() + ".tmp");

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
                 CSVPrinter printer = new CSVPrinter(writer, CSV_WRITE_FORMAT)) {

                for (Nurse nurse : data.values()) {
                    printer.printRecord(
                            nurse.getId(),
                            nurse.getPesel(),
                            nurse.getFirstName(),
                            nurse.getLastName(),
                            nurse.getBirthDate(),
                            nurse.isActive(),
                            nurse.getDepartment() != null ? nurse.getDepartment().getId() : "");
                }
            }

            Files.move(tempFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            Logger.info("Nurses saved successfully. Count: " + data.size());
        } catch (IOException e) {
            Logger.warn("Failed to save nurses to file: " + e.getMessage());

            try {
                Files.deleteIfExists(tempFile);
            } catch (IOException ignored) {
                Logger.warn("Failed to cleanup temp file: " + ignored.getMessage());
            }

            throw new UncheckedIOException("Failed to save nurses to file: " + filePath, e);
        }
    }

    public Optional<Nurse> findByPesel(String pesel) {
        return data.values()
                .stream()
                .filter(nurse -> nurse.getPesel() != null)
                .filter(nurse -> nurse.getPesel().equals(pesel))
                .findFirst();
    }

    public List<Nurse> findByDepartmentId(UUID departmentId) {
        return data.values()
                .stream()
                .filter(nurse -> nurse.getDepartment() != null)
                .filter(nurse -> nurse.getDepartment().getId().equals(departmentId))
                .toList();
    }

    public List<Nurse> findByFirstNameAndLastName(String firstName, String lastName) {
        return data.values()
                .stream()
                .filter(nurse -> nurse.getFirstName().equalsIgnoreCase(firstName))
                .filter(nurse -> nurse.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }

    public List<Nurse> findByLastName(String lastName) {
        return data.values()
                .stream()
                .filter(nurse -> nurse.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }
}
