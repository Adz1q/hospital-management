package com.adz1q.hospital.management.repository;

import com.adz1q.hospital.management.model.Identifiable;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class FileRepository<ID, T extends Identifiable<ID>>
        implements Repository<ID, T> {
    protected final Map<ID, T> data;
    protected final Path filePath;

    public FileRepository(Path filePath) {
        this.filePath = filePath;
        this.data = new HashMap<>();
    }

    public abstract void saveToFile();

    @Override
    public T save(T entity) {
        data.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<T> findAll() {
        return data.values()
                .stream()
                .toList();
    }

    @Override
    public void deleteById(ID id) {
        data.remove(id);
    }

    public Map<ID, T> getData() {
        return data;
    }
}
