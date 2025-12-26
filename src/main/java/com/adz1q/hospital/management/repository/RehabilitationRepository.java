package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Rehabilitation;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RehabilitationRepository extends FileRepository<UUID, Rehabilitation> {
    private static final CSVFormat CSV_READ_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "description", "prescription_date", "therapies")
            .setSkipHeaderRecord(true)
            .build();
    private static final CSVFormat CSV_WRITE_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "description", "prescription_date", "therapies")
            .build();

    public RehabilitationRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile() {
        if (!Files.exists(filePath)) {
            Logger.info("Rehabilitation file does not exist. Starting with an empty repository.");
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
                        String therapiesStr = record.get("therapies");

                        UUID id = UUID.fromString(idStr);
                        LocalDate prescriptionDate = LocalDate.parse(prescriptionDateStr);

                        List<String> therapies = new ArrayList<>();
                        if (therapiesStr != null && !therapiesStr.isBlank()) {
                            therapies.addAll(Arrays.asList(therapiesStr.split("\\|")));
                        }

                        Rehabilitation rehabilitation = new Rehabilitation(
                                id,
                                description,
                                prescriptionDate,
                                therapies);
                        data.put(id, rehabilitation);
                    } catch (Exception e) {
                        Logger.error("Skipping corrupted record at line " + record.getRecordNumber() + ": " + e.getMessage());
                    }
                }

                Logger.info("Rehabilitations loaded successfully. Count: " + data.size());
            }
        } catch (IOException e) {
            Logger.warn("Failed to load rehabilitation data from file: " + e.getMessage());
            throw new UncheckedIOException("Error reading rehabilitation file: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveToFile() {
        Path tempFile = filePath.resolveSibling(filePath.getFileName() + ".tmp");

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
                 CSVPrinter printer = new CSVPrinter(writer, CSV_WRITE_FORMAT)) {
                for (Rehabilitation rehabilitation : data.values()) {
                    printer.printRecord(
                            rehabilitation.getId(),
                            rehabilitation.getDescription(),
                            rehabilitation.getPrescriptionDate(),
                            String.join("|", rehabilitation.getTherapies()));
                }
            }

            Files.move(tempFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            Logger.info("Rehabilitation data successfully saved to file.");
        } catch (IOException e) {
            Logger.warn("Failed to save rehabilitation data to file: " + e.getMessage());

            try {
                Files.deleteIfExists(tempFile);
            } catch (IOException ex) {
                Logger.warn("Failed to delete temporary rehabilitation file: " + ex.getMessage());
            }

            throw new UncheckedIOException("Error writing to temporary rehabilitation file: " + e.getMessage(), e);
        }
    }
}
