package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Therapy;
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
import java.util.UUID;

public class TherapyRepository extends FileRepository<UUID, Therapy> {
    private static final CSVFormat CSV_READ_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "description", "prescription_date", "name")
            .setSkipHeaderRecord(true)
            .build();
    private static final CSVFormat CSV_WRITE_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "description", "prescription_date", "name")
            .build();

    public TherapyRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile() {
        if (!Files.exists(filePath)) {
            Logger.info("Therapy file does not exist. Starting with an empty repository.");
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
             CSVParser parser = CSVParser.parse(reader, CSV_READ_FORMAT)) {
            for (CSVRecord record : parser) {
                try {
                    String idStr = record.get("id");
                    String description = record.get("description");
                    String prescriptionDateStr = record.get("prescription_date");
                    String name = record.get("name");

                    UUID id = UUID.fromString(idStr);
                    LocalDate prescriptionDate = LocalDate.parse(prescriptionDateStr);

                    Therapy therapy = new Therapy(
                            id,
                            description,
                            prescriptionDate,
                            name);
                    data.put(id, therapy);
                } catch (Exception e) {
                    Logger.error("Skipping corrupted record at line " + record.getRecordNumber() + ": " + e.getMessage());
                }
            }

            Logger.info("Therapies loaded successfully. Count: " + data.size());
        } catch (IOException e) {
            Logger.error("Error loading medications from file: " + e.getMessage());
            throw new UncheckedIOException("Failed to load therapies from file.", e);
        }
    }

    @Override
    public void saveToFile() {
        Path tempFile = filePath.resolveSibling(filePath.getFileName() + ".tmp");

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
                 CSVPrinter printer = new CSVPrinter(writer, CSV_WRITE_FORMAT)) {
                for (Therapy therapy : data.values()) {
                    printer.printRecord(
                            therapy.getId(),
                            therapy.getDescription(),
                            therapy.getPrescriptionDate(),
                            therapy.getName());
                }
            }

            Files.move(tempFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            Logger.info("Therapies saved successfully to " + filePath);
        } catch (IOException e) {
            Logger.warn("Error while saving therapies to file: " + e.getMessage());

            try {
                Files.deleteIfExists(tempFile);
            } catch (Exception ignored) {
                Logger.warn("Failed to cleanup temp file: " + ignored.getMessage());
            }

            throw new UncheckedIOException("Failed to save therapies to file.", e);
        }
    }
}
