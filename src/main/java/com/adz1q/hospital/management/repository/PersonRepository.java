package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Person;

import java.nio.file.Path;
import java.util.UUID;

public class PersonRepository extends FileRepository<UUID, Person> {
    public PersonRepository(Path filePath) {
        super(filePath);
        loadFromFile();
    }

    @Override
    protected void loadFromFile() {}

    @Override
    protected void saveToFile() {}
}
