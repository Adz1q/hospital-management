package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.DepartmentNotFoundException;
import com.adz1q.hospital.management.model.Department;
import com.adz1q.hospital.management.repository.DepartmentRepository;
import com.adz1q.hospital.management.util.Logger;

import java.util.List;
import java.util.UUID;

public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
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

    public void deleteDepartment(UUID id)
            throws DepartmentNotFoundException {
        getDepartment(id);
        departmentRepository.deleteById(id);
        Logger.info("Deleted department with ID: " + id);
    }

    public void renameDepartment(
            UUID id,
            String newName)
            throws DepartmentNotFoundException {
        Department department = getDepartment(id);
        department.rename(newName);
        Logger.info("Renamed department with ID: " + id);
    }
}
