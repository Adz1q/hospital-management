package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.DepartmentNotFoundException;
import com.adz1q.hospital.management.model.Department;
import com.adz1q.hospital.management.repository.DepartmentRepository;
import com.adz1q.hospital.management.repository.NurseRepository;
import com.adz1q.hospital.management.repository.RoomRepository;
import com.adz1q.hospital.management.util.Logger;

import java.util.List;
import java.util.UUID;

public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final RoomRepository roomRepository;
    private final NurseRepository nurseRepository;

    public DepartmentService(
            DepartmentRepository departmentRepository,
            RoomRepository roomRepository,
            NurseRepository nurseRepository) {
        this.departmentRepository = departmentRepository;
        this.roomRepository = roomRepository;
        this.nurseRepository = nurseRepository;
    }

    public Department createDepartment(String name) {
        Department newDepartment = new Department(name);
        Logger.info("Created department with ID: " + newDepartment.getId());
        return departmentRepository.save(newDepartment);
    }

    public Department getDepartment(UUID id)
            throws DepartmentNotFoundException {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department with this ID does not exist."));
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public void closeDepartment(UUID id)
            throws DepartmentNotFoundException {
        Department department = getDepartment(id);

        if (existsAnyRoomInDepartment(id)) {
            throw new IllegalArgumentException("Cannot close department with assigned rooms.");
        }

        if (existsAnyNurseInDepartment(id)) {
            throw new IllegalArgumentException("Cannot close department with assigned nurses.");
        }

        department.close();
        Logger.info("Closed department with ID: " + id);
    }

    public void renameDepartment(
            UUID id,
            String newName)
            throws DepartmentNotFoundException {
        Department department = getDepartment(id);
        department.rename(newName);
        Logger.info("Renamed department with ID: " + id);
    }

    public boolean existsAnyRoomInDepartment(UUID departmentId) {
        return !roomRepository.findByDepartmentId(departmentId).isEmpty();
    }

    public boolean existsAnyNurseInDepartment(UUID departmentId) {
        return !nurseRepository.findByDepartmentId(departmentId).isEmpty();
    }
}
