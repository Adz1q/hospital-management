package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Diagnosis;
import com.adz1q.hospital.management.model.Patient;
import com.adz1q.hospital.management.model.Treatment;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PatientRepository extends FileRepository<UUID, Patient> {
    public PatientRepository(Path filePath) {
        super(filePath);
    }

    public void loadFromFile(Map<UUID, Diagnosis> diagnoses) {}

    @Override
    public void saveToFile() {}

    public Optional<Patient> findByPesel(String pesel) {
        return findAll().stream()
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
        return data.get(patientId)
                .getDocumentation()
                .stream()
                .flatMap(diagnosis -> diagnosis.getTreatments().stream())
                .toList();
    }
}
