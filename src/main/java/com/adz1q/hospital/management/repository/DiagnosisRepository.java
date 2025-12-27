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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class DiagnosisRepository extends FileRepository<UUID, Diagnosis> {
    private static final CSVFormat CSV_READ_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "description", "treatment_ids")
            .setSkipHeaderRecord(true)
            .build();
    private static final CSVFormat CSV_WRITE_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "description", "treatment_ids")
            .build();

    public DiagnosisRepository(Path filepath) {
        super(filepath);
    }

    public void loadFromFile(
            Map<UUID, Surgery> surgeries,
            Map<UUID, Medication> medications,
            Map<UUID, Therapy> therapies,
            Map<UUID, Rehabilitation> rehabilitations) {
        if (!Files.exists(filePath)) {
            Logger.warn("Diagnosis file does not exist. Starting with an empty repository.");
            return;
        }

        try {
            try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
                 CSVParser parser = new CSVParser(reader, CSV_READ_FORMAT)) {
                for (CSVRecord record : parser) {
                    try {
                        String idStr = record.get("id");
                        String description = record.get("description");
                        String treatmentIdsStr = record.get("treatment_ids");

                        UUID id = UUID.fromString(idStr);
                        Set<Treatment> treatments = new HashSet<>();

                        if (treatmentIdsStr != null && !treatmentIdsStr.isBlank()) {
                            String[] treatmentIds = treatmentIdsStr.split("\\|");
                            for (String treatmentIdStr : treatmentIds) {
                                UUID treatmentId = UUID.fromString(treatmentIdStr);

                                if (surgeries.containsKey(treatmentId)) {
                                    treatments.add(surgeries.get(treatmentId));
                                } else if (medications.containsKey(treatmentId)) {
                                    treatments.add(medications.get(treatmentId));
                                } else if (therapies.containsKey(treatmentId)) {
                                    treatments.add(therapies.get(treatmentId));
                                } else if (rehabilitations.containsKey(treatmentId)) {
                                    treatments.add(rehabilitations.get(treatmentId));
                                } else {
                                    Logger.warn("Unknown treatment ID " + treatmentId + " for diagnosis " + id);
                                }
                            }
                        }

                        Diagnosis diagnosis = new Diagnosis(
                                id,
                                description,
                                treatments);
                        data.put(id, diagnosis);
                    } catch (Exception e) {
                        Logger.error("Skipping corrupted record at line " + record.getRecordNumber() + ": " + e.getMessage());
                    }
                }

                Logger.info("Diagnoses loaded successfully. Count: " + data.size());
            }
        } catch (IOException e) {
            Logger.error("Failed to load diagnoses from file: " + e.getMessage());
            throw new UncheckedIOException("Failed to load diagnoses from file: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveToFile() {
        Path tempFile = filePath.resolveSibling(filePath.getFileName() + ".tmp");

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
                 CSVPrinter printer = new CSVPrinter(writer, CSV_WRITE_FORMAT)) {
                for (Diagnosis diagnosis : data.values()) {
                    String treatmentIdsStr = diagnosis.getTreatments()
                                    .stream()
                                    .map(Treatment::getId)
                                    .map(UUID::toString)
                                    .collect(Collectors.joining("|"));

                    printer.printRecord(
                            diagnosis.getId(),
                            diagnosis.getDescription(),
                            treatmentIdsStr);
                }
            }

            Files.move(tempFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            Logger.info("Diagnoses saved successfully. Count: " + data.size());
        } catch (IOException e) {
            Logger.warn("Failed to save diagnoses to file: " + e.getMessage());

            try {
                Files.deleteIfExists(tempFile);
            } catch (IOException ignored) {
                Logger.warn("Failed to cleanup temp file: " + ignored.getMessage());
            }

            throw new UncheckedIOException("Failed to save diagnoses to file: " + filePath, e);
        }
    }
}
