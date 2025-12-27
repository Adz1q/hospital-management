package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Diagnosis;
import com.adz1q.hospital.management.model.Patient;
import com.adz1q.hospital.management.model.Treatment;
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
import java.util.*;
import java.util.stream.Collectors;

public class PatientRepository extends FileRepository<UUID, Patient> {
    private static final CSVFormat CSV_READ_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "pesel", "first_name", "last_name", "birth_date", "diagnosis_ids")
            .setSkipHeaderRecord(true)
            .build();
    private static final CSVFormat CSV_WRITE_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "pesel", "first_name", "last_name", "birth_date", "diagnosis_ids")
            .build();

    public PatientRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile(Map<UUID, Diagnosis> diagnoses) {
        if (!Files.exists(filePath)) {
            Logger.warn("Patient file does not exist. Starting with an empty repository.");
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
                        String diagnosisIdsStr = record.get("diagnosis_ids");

                        UUID id = UUID.fromString(idStr);
                        LocalDate birthDate = LocalDate.parse(birthDateStr);
                        List<Diagnosis> documentation = new ArrayList<>();

                        if (diagnosisIdsStr != null && !diagnosisIdsStr.isBlank()) {
                            String[] diagnosisIds = diagnosisIdsStr.split("\\|");
                            for (String diagnosisIdStr : diagnosisIds) {
                                UUID diagnosisId = UUID.fromString(diagnosisIdStr);
                                Diagnosis diagnosis = diagnoses.get(diagnosisId);

                                if (diagnosis != null) {
                                    documentation.add(diagnosis);
                                } else {
                                    Logger.warn("Diagnosis with ID " + diagnosisId + " not found for patient " + id);
                                }
                            }
                        }

                        Patient patient = new Patient(
                                id,
                                pesel,
                                firstName,
                                lastName,
                                birthDate,
                                documentation);
                        data.put(id, patient);
                    } catch (Exception e) {
                        Logger.warn("Failed to parse patient record: " + record.toString());
                    }
                }
                Logger.info("Patients loaded successfully. Count: " + data.size());
            }
        } catch (IOException e) {
            Logger.warn("Failed to load patients from file: " + filePath);
            throw new UncheckedIOException("Failed to load patients from file: " + filePath, e);
        }
    }

    @Override
    public void saveToFile() {
        Path tempFile = filePath.resolveSibling(filePath.getFileName() + ".tmp");

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
                 CSVPrinter printer = new CSVPrinter(writer, CSV_WRITE_FORMAT)) {
                for (Patient patient : data.values()) {
                    String diagnosisIds = patient.getDocumentation()
                            .stream()
                            .map(Diagnosis::getId)
                            .map(UUID::toString)
                            .collect(Collectors.joining("|"));

                    printer.printRecord(
                            patient.getId(),
                            patient.getPesel(),
                            patient.getFirstName(),
                            patient.getLastName(),
                            patient.getBirthDate(),
                            diagnosisIds);
                }
            }

            Files.move(tempFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            Logger.info("Patients saved successfully. Count: " + data.size());
        } catch (IOException e) {
            Logger.warn("Failed to save patients to file: " + filePath);

            try {
                Files.deleteIfExists(tempFile);
            } catch (Exception ignored) {
                Logger.warn("Failed to cleanup temp file: " + ignored.getMessage());
            }

            throw new UncheckedIOException("Failed to save patients to file: " + filePath, e);
        }
    }

    public Optional<Patient> findByPesel(String pesel) {
        return data.values().stream()
                .filter(patient -> patient.getPesel() != null)
                .filter(patient -> patient.getPesel().equals(pesel))
                .findFirst();
    }

    public List<Patient> findByFirstNameAndLastName(String firstName, String lastName) {
        return data.values()
                .stream()
                .filter(patient -> patient.getFirstName().equalsIgnoreCase(firstName))
                .filter(patient -> patient.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }

    public List<Patient> findByLastName(String lastName) {
        return data.values()
                .stream()
                .filter(patient -> patient.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }

    public List<Diagnosis> findPatientDocumentation(UUID patientId) {
        return data.get(patientId).getDocumentation();
    }

    public List<Treatment> findPatientTreatments(UUID patientId) {
        return data.get(patientId).getDocumentation()
                .stream()
                .flatMap(diagnosis -> diagnosis.getTreatments().stream())
                .toList();
    }
}
