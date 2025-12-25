package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Doctor;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DoctorRepository extends FileRepository<UUID, Doctor> {
    public DoctorRepository(Path filePath) {
        super(filePath);
        loadFromFile();
    }

    @Override
    protected void loadFromFile() {}

    @Override
    protected void saveToFile() {}

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
