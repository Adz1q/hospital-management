package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Room;

import java.nio.file.Path;
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
}
