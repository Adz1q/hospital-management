package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.DepartmentNotFoundException;
import com.adz1q.hospital.management.exception.DoctorNotFoundException;
import com.adz1q.hospital.management.exception.NurseNotFoundException;
import com.adz1q.hospital.management.model.Department;
import com.adz1q.hospital.management.model.Doctor;
import com.adz1q.hospital.management.model.Nurse;
import com.adz1q.hospital.management.repository.NurseRepository;
import com.adz1q.hospital.management.util.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class NurseService {
    private final NurseRepository nurseRepository;
    private final DepartmentService departmentService;

    public NurseService(
            NurseRepository nurseRepository,
            DepartmentService departmentService) {
        this.nurseRepository = nurseRepository;
        this.departmentService = departmentService;
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

        departmentService.getDepartment(department.getId());
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

        departmentService.getDepartment(department.getId());
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
}
