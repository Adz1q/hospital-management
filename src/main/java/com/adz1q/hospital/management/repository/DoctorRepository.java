package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Doctor;
import com.adz1q.hospital.management.model.Specialization;
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

public class DoctorRepository extends FileRepository<UUID, Doctor> {
    private static final CSVFormat CSV_READ_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "pesel", "first_name", "last_name", "birth_date", "specializations", "active")
            .setSkipHeaderRecord(true)
            .build();
    private static final CSVFormat CSV_WRITE_FORMAT = CSVFormat.Builder.create()
            .setDelimiter(';')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("id", "pesel", "first_name", "last_name", "birth_date", "specializations", "active")
            .build();

    public DoctorRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile() {
        if (!Files.exists(filePath)) {
            Logger.warn("Doctor file does not exist. Starting with an empty repository.");
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
                        String specializationsStr = record.get("specializations");
                        String activeStr = record.get("active");

                        UUID id = UUID.fromString(idStr);
                        LocalDate birthDate = LocalDate.parse(birthDateStr);
                        boolean active = Boolean.parseBoolean(activeStr);
                        Set<Specialization> specializations = new HashSet<>();

                        if (specializationsStr != null && !specializationsStr.isBlank()) {
                            String[] specializationsArr = specializationsStr.split("\\|");
                            for (String specializationStr : specializationsArr) {
                                try {
                                    Specialization specialization = Specialization.valueOf(specializationStr.toUpperCase());
                                    specializations.add(specialization);
                                } catch (IllegalArgumentException e) {
                                    Logger.warn("Unknown specialization '" + specializationStr + "' for doctor ID " + id);
                                }
                            }
                        }

                        Doctor doctor = new Doctor(
                                id,
                                pesel.isBlank() ? null : pesel,
                                firstName,
                                lastName,
                                birthDate,
                                specializations,
                                active);
                        data.put(id, doctor);
                    } catch (Exception e) {
                        Logger.error("Skipping corrupted record at line " + record.getRecordNumber() + ": " + e.getMessage());
                    }
                }

                Logger.info("Doctors loaded successfully. Count: " + data.size());
            }
        } catch (IOException e) {
            Logger.warn("Failed to load doctor data from file: " + e.getMessage());
            throw new UncheckedIOException("Failed to load data", e);
        }
    }

    @Override
    public void saveToFile() {
        Path tempFile = filePath.resolveSibling(filePath.getFileName() + ".tmp");

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
                 CSVPrinter printer = new CSVPrinter(writer, CSV_WRITE_FORMAT)) {
                for (Doctor doctor : data.values()) {
                    String specializationsStr = doctor.getSpecializations()
                            .stream()
                            .map(Enum::name)
                            .collect(Collectors.joining("|"));

                    printer.printRecord(
                            doctor.getId(),
                            doctor.getPesel() != null ? doctor.getPesel() : "",
                            doctor.getFirstName(),
                            doctor.getLastName(),
                            doctor.getBirthDate(),
                            specializationsStr,
                            doctor.isActive());
                }
            }

            Files.move(tempFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            Logger.info("Doctors saved successfully to " + filePath);
        } catch (IOException e) {
            Logger.warn("Error while saving doctors to file: " + e.getMessage());

            try {
                Files.deleteIfExists(tempFile);
            } catch (Exception ignored) {
                Logger.warn("Failed to cleanup temp file: " + ignored.getMessage());
            }

            throw new UncheckedIOException("Error writing to temporary doctor file: " + e.getMessage(), e);
        }
    }

    public Optional<Doctor> findByPesel(String pesel) {
        return data.values()
                .stream()
                .filter(doctor -> doctor.getPesel() != null)
                .filter(doctor -> doctor.getPesel().equals(pesel))
                .findFirst();
    }

    public List<Doctor> findByFirstNameAndLastName(String firstName, String lastName) {
        return data.values()
                .stream()
                .filter(doctor -> doctor.getFirstName().equalsIgnoreCase(firstName))
                .filter(doctor -> doctor.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }

    public List<Doctor> findByLastName(String lastName) {
        return data.values()
                .stream()
                .filter(doctor -> doctor.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }
}
