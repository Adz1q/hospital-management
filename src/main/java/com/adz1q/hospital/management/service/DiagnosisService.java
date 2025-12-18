package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.DiagnosisNotFoundException;
import com.adz1q.hospital.management.model.Diagnosis;
import com.adz1q.hospital.management.model.Treatment;
import com.adz1q.hospital.management.repository.DiagnosisRepository;
import com.adz1q.hospital.management.util.Logger;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;

    public DiagnosisService(
            DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    public Diagnosis diagnosePatient(
            String description,
            Set<Treatment> treatments) {
        Diagnosis newDiagnosis = new Diagnosis(description, treatments);
        Logger.info("Diagnosed new patient with diagnosis ID: " + newDiagnosis.getId());
        return diagnosisRepository.save(newDiagnosis);
    }

    public Diagnosis getDiagnosis(UUID id)
            throws DiagnosisNotFoundException {
        return diagnosisRepository.findById(id)
                .orElseThrow(() -> new DiagnosisNotFoundException("Diagnosis with this ID does not exists."));
    }

    public List<Diagnosis> getAllDiagnoses() {
        return diagnosisRepository.findAll();
    }

    public void removeDiagnosis(UUID id)
            throws DiagnosisNotFoundException {
        getDiagnosis(id);
        diagnosisRepository.deleteById(id);
        Logger.info("Removed diagnosis with ID: " + id);
    }
}
