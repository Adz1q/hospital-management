package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.DepartmentNotFoundException;
import com.adz1q.hospital.management.exception.NurseNotFoundException;
import com.adz1q.hospital.management.model.Department;
import com.adz1q.hospital.management.model.Nurse;
import com.adz1q.hospital.management.repository.DepartmentRepository;
import com.adz1q.hospital.management.repository.NurseRepository;
import com.adz1q.hospital.management.util.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class NurseService {
    private final NurseRepository nurseRepository;
    private final DepartmentRepository departmentRepository;

    public NurseService(
            NurseRepository nurseRepository,
            DepartmentRepository departmentRepository) {
        this.nurseRepository = nurseRepository;
        this.departmentRepository = departmentRepository;
    }

    public Nurse hireNurse(
            String firstName,
            String lastName,
            LocalDate birthDate,
            Department department)
            throws DepartmentNotFoundException {
        if (department == null) {
            throw new NullPointerException("Department cannot be null.");
        }

        getDepartment(department.getId());
        Nurse newNurse = new Nurse(firstName, lastName, birthDate, department);
        Logger.info("Hired nurse with ID: " + newNurse.getId());
        return nurseRepository.save(newNurse);
    }

    public Nurse hireNurse(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String pesel,
            Department department)
            throws DepartmentNotFoundException {
        if (nurseRepository.findByPesel(pesel).isPresent()) {
            throw new IllegalArgumentException("Nurse with this PESEL already exists.");
        }

        if (department == null) {
            throw new NullPointerException("Department cannot be null.");
        }

        getDepartment(department.getId());
        Nurse newNurse = new Nurse(
                firstName,
                lastName,
                birthDate,
                pesel,
                department);
        Logger.info("Hired nurse with ID: " + newNurse.getId());
        return nurseRepository.save(newNurse);
    }

    public Nurse getNurse(UUID id) throws NurseNotFoundException {
        return nurseRepository.findById(id)
                .orElseThrow(() -> new NurseNotFoundException("Nurse with this ID does not exist."));
    }

    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    public void dismissNurse(UUID id)
            throws NurseNotFoundException {
        Nurse nurse = getNurse(id);
        nurse.dismiss();
        Logger.info("Dismissed nurse with ID: " + id);
    }

    public void rehireNurse(UUID id)
            throws NurseNotFoundException {
        Nurse nurse = getNurse(id);
        nurse.rehire();
        Logger.info("Rehired nurse with ID: " + id);
    }

    public void changeNurseDepartment(
            UUID id,
            Department department)
            throws NurseNotFoundException {
        Nurse nurse = getNurse(id);
        nurse.changeDepartment(department);
        Logger.info("Changed department for nurse with ID: " + id);
    }

    public boolean existsAnyNurseInDepartment(UUID departmentId) {
        return !nurseRepository.findByDepartmentId(departmentId).isEmpty();
    }

    public Department getDepartment(UUID id)
            throws DepartmentNotFoundException {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department with this ID does not exist."));
    }

    public List<Nurse> getNursesByFirstNameAndLastName(
            String firstName,
            String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("First name and last name cannot be null.");
        }

        if (firstName.isBlank() || lastName.isBlank()) {
            throw new IllegalArgumentException("First name and last name cannot be blank.");
        }

        return nurseRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public List<Nurse> getNursesByLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Last name cannot be null.");
        }

        if (lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be blank.");
        }

        return nurseRepository.findByLastName(lastName);
    }
}
