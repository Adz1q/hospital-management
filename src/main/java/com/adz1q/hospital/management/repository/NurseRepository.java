package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Nurse;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NurseRepository extends FileRepository<UUID, Nurse> {
    public NurseRepository(Path filePath) {
        super(filePath);
        loadFromFile();
    }

    @Override
    protected void loadFromFile() {}

    @Override
    protected void saveToFile() {}

    public Optional<Nurse> findByPesel(String pesel) {
        return data.values()
                .stream()
                .filter(nurse -> nurse.getPesel() != null)
                .filter(nurse -> nurse.getPesel().equals(pesel))
                .findFirst();
    }

    public List<Nurse> findByDepartmentId(UUID departmentId) {
        return data.values()
                .stream()
                .filter(nurse -> nurse.getDepartment() != null)
                .filter(nurse -> nurse.getDepartment().getId().equals(departmentId))
                .toList();
    }

    public List<Nurse> findByFirstNameAndLastName(String firstName, String lastName) {
        return data.values()
                .stream()
                .filter(nurse -> nurse.getFirstName().equalsIgnoreCase(firstName))
                .filter(nurse -> nurse.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }

    public List<Nurse> findByLastName(String lastName) {
        return data.values()
                .stream()
                .filter(nurse -> nurse.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }
}
