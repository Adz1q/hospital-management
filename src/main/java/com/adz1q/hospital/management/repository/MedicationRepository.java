package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Medication;
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

public class MedicationRepository extends FileRepository<UUID, Medication> {
    private static final CSVFormat CSV_READ_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "description", "prescription_date", "medicines")
            .setSkipHeaderRecord(true)
            .build();
    private static final CSVFormat CSV_WRITE_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "description", "prescription_date", "medicines")
            .build();

    public MedicationRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile() {
        if (!Files.exists(filePath)) {
            Logger.info("Medication file does not exist. Starting with an empty repository.");
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
             CSVParser parser = CSVParser.parse(reader, CSV_READ_FORMAT)) {
            for (CSVRecord record : parser) {
                try {
                    String idStr = record.get("id");
                    String description = record.get("description");
                    String prescriptionDateStr = record.get("prescription_date");
                    String medicinesStr = record.get("medicines");

                    UUID id = UUID.fromString(idStr);
                    LocalDate prescriptionDate = LocalDate.parse(prescriptionDateStr);

                    List<String> medicines = new ArrayList<>();
                    if (medicinesStr != null && !medicinesStr.isBlank()) {
                        medicines.addAll(Arrays.asList(medicinesStr.split("\\|")));
                    }

                    Medication medication = new Medication(
                            id,
                            description,
                            prescriptionDate,
                            medicines);
                    data.put(id, medication);
                } catch (Exception e) {
                    Logger.error("Skipping corrupted record at line " + record.getRecordNumber() + ": " + e.getMessage());
                }
            }

            Logger.info("Medications loaded successfully. Count: " + data.size());
        } catch (IOException e) {
            Logger.error("Error loading medications from file: " + e.getMessage());
            throw new UncheckedIOException("Failed to load data", e);
        }
    }

    @Override
    public void saveToFile() {
        Path tempFile = filePath.resolveSibling(filePath.getFileName() + ".tmp");

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
                 CSVPrinter printer = new CSVPrinter(writer, CSV_WRITE_FORMAT)) {
                for (Medication medication : data.values()) {
                    printer.printRecord(
                            medication.getId(),
                            medication.getDescription(),
                            medication.getPrescriptionDate(),
                            String.join("|", medication.getMedicines())
                    );
                }
            }

            Files.move(tempFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            Logger.info("Medications saved successfully to " + filePath);
        } catch (IOException e) {
            Logger.warn("Error while saving medications to file: " + e.getMessage());

            try {
                Files.deleteIfExists(tempFile);
            } catch (Exception ignored) {
                Logger.warn("Failed to cleanup temp file: " + ignored.getMessage());
            }

            throw new UncheckedIOException("Failed to save data safely", e);
        }
    }
}
