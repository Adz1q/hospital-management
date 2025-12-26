package com.adz1q.hospital.management.config;

import com.adz1q.hospital.management.repository.*;
import com.adz1q.hospital.management.util.Logger;

public class DataLoader {
    private final MedicationRepository medicationRepository;
    private final TherapyRepository therapyRepository;
    private final RehabilitationRepository rehabilitationRepository;
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final NurseRepository nurseRepository;
    private final SurgeryRepository surgeryRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final PatientRepository patientRepository;
    private final RoomRepository roomRepository;
    private final AppointmentRepository appointmentRepository;

    public DataLoader(
            MedicationRepository medicationRepository,
            TherapyRepository therapyRepository,
            RehabilitationRepository rehabilitationRepository,
            DoctorRepository doctorRepository,
            DepartmentRepository departmentRepository,
            NurseRepository nurseRepository,
            SurgeryRepository surgeryRepository,
            DiagnosisRepository diagnosisRepository,
            PatientRepository patientRepository,
            RoomRepository roomRepository,
            AppointmentRepository appointmentRepository) {
        this.medicationRepository = medicationRepository;
        this.therapyRepository = therapyRepository;
        this.rehabilitationRepository = rehabilitationRepository;
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.nurseRepository = nurseRepository;
        this.surgeryRepository = surgeryRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.patientRepository = patientRepository;
        this.roomRepository = roomRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public void loadAllData() {
        Logger.info("Loading data from CSV files");
        medicationRepository.loadFromFile();
        therapyRepository.loadFromFile();
        rehabilitationRepository.loadFromFile();
        doctorRepository.loadFromFile();
        departmentRepository.loadFromFile();
        nurseRepository.loadFromFile(
                departmentRepository.getData());
        surgeryRepository.loadFromFile(
                doctorRepository.getData());
        diagnosisRepository.loadFromFile(
                surgeryRepository.getData(),
                medicationRepository.getData(),
                therapyRepository.getData(),
                rehabilitationRepository.getData());
        patientRepository.loadFromFile(
                diagnosisRepository.getData());
        roomRepository.loadFromFile(
                departmentRepository.getData(),
                patientRepository.getData());
        appointmentRepository.loadFromFile(
                patientRepository.getData(),
                doctorRepository.getData(),
                diagnosisRepository.getData());
        Logger.info("Data loading completed");
    }
}
