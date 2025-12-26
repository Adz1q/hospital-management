package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Medication;
import com.adz1q.hospital.management.util.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class MedicationRepository extends FileRepository<UUID, Medication> {
    private static final CSVFormat CSV_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "description", "prescription_date", "medicines")
            .build();

    public MedicationRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile() {}

    @Override
    public void saveToFile() {
        Path tempFile = filePath.resolveSibling(filePath.getFileName() + ".tmp");

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
                 CSVPrinter printer = new CSVPrinter(writer, CSV_FORMAT)) {
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
            Logger.error("Error saving medications to file: " + e.getMessage());

            try {
                Files.deleteIfExists(tempFile);
            } catch (IOException ignored) {
                Logger.error("Error while deleting temporary file: " + ignored.getMessage());
            }

            throw new UncheckedIOException("Failed to save data safely", e);
        }
    }
}
