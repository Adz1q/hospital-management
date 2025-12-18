package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Room;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class RoomRepository extends FileRepository<UUID, Room> {
    public RoomRepository(Path filepath) {
        super(filepath);
        loadFromFile();
    }

    @Override
    protected void loadFromFile() {}

    @Override
    protected void saveToFile() {}

    public List<Room> findByDepartmentId(UUID departmentId) {
        return data.values()
                .stream()
                .filter(room -> room.getDepartment() != null)
                .filter(room -> room.getDepartment().getId().equals(departmentId))
                .toList();
    }
}
