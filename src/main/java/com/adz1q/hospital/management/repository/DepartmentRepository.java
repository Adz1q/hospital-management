package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Department;
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
import java.util.UUID;

public class DepartmentRepository extends FileRepository<UUID, Department> {
    private static final CSVFormat CSV_READ_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "name", "active")
            .setSkipHeaderRecord(true)
            .build();
    private static final CSVFormat CSV_WRITE_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "name", "active")
            .build();

    public DepartmentRepository(Path filepath) {
        super(filepath);
    }

    public void loadFromFile() {
        if (!Files.exists(filePath)) {
            Logger.info( "Department file does not exist. Starting with an empty repository.");
            return;
        }

        try {
            try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
                 CSVParser parser = CSVParser.parse(reader, CSV_READ_FORMAT)) {
                for (CSVRecord record : parser) {
                    try {
                        String idStr = record.get("id");
                        String name = record.get("name");
                        String activeStr = record.get("active");

                        UUID id = UUID.fromString(idStr);
                        boolean active = Boolean.parseBoolean(activeStr);

                        Department department = new Department(
                                id,
                                name,
                                active);
                        data.put(id, department);
                    } catch (Exception e) {
                        Logger.error("Skipping corrupted record at line " + record.getRecordNumber() + ": " + e.getMessage());
                    }
                }

                Logger.info("Departments loaded successfully. Count: " + data.size());
            }
        } catch (IOException e) {
            Logger.warn("Failed to load departments from file: " + e.getMessage());
            throw new UncheckedIOException("Failed to load departments from file: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveToFile() {
        Path tempFile = filePath.resolveSibling(filePath.getFileName() + ".tmp");

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
                 CSVPrinter printer = new CSVPrinter(writer, CSV_WRITE_FORMAT)) {

                for (Department department : data.values()) {
                    printer.printRecord(
                            department.getId(),
                            department.getName(),
                            Boolean.toString(department.isActive()));
                }
            }

            Files.move(tempFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            Logger.info("Departments saved successfully. Count: " + data.size());
        } catch (IOException e) {
            Logger.warn("Failed to save departments to file: " + e.getMessage());

            try {
                Files.deleteIfExists(tempFile);
            } catch (Exception ignored) {
                Logger.warn("Failed to cleanup temp file: " + ignored.getMessage());
            }

            throw new UncheckedIOException("Failed to save departments to file: " + e.getMessage(), e);
        }
    }
}
