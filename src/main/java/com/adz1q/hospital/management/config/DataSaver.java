package com.adz1q.hospital.management.config;

import com.adz1q.hospital.management.repository.*;
import com.adz1q.hospital.management.util.Logger;

public class DataSaver {
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

    public DataSaver(
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

    public void saveAllData() {
        Logger.info("Saving data to CSV files");
        medicationRepository.saveToFile();
        therapyRepository.saveToFile();
        rehabilitationRepository.saveToFile();
        doctorRepository.saveToFile();
        departmentRepository.saveToFile();
        nurseRepository.saveToFile();
        surgeryRepository.saveToFile();
        diagnosisRepository.saveToFile();
        patientRepository.saveToFile();
        roomRepository.saveToFile();
        appointmentRepository.saveToFile();
        Logger.info("Data saved successfully");
    }
}
