package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Department;
import com.adz1q.hospital.management.model.Patient;
import com.adz1q.hospital.management.model.Room;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RoomRepository extends FileRepository<UUID, Room> {
    public RoomRepository(Path filepath) {
        super(filepath);
    }

    public void loadFromFile(
            Map<UUID, Department> departments,
            Map<UUID, Patient> patients) {}

    @Override
    public void saveToFile() {}

    public List<Room> findByDepartmentId(UUID departmentId) {
        return data.values()
                .stream()
                .filter(room -> room.getDepartment() != null)
                .filter(room -> room.getDepartment().getId().equals(departmentId))
                .toList();
    }

    public Set<Patient> findPatientsInRoom(UUID roomId) {
        return data.get(roomId).getPatients();
    }
}
