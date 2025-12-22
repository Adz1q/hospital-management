package com.adz1q.hospital.management.ui;

import com.adz1q.hospital.management.service.*;

public class ConsoleMenu {
    private final ConsoleInputReader consoleInputReader;

    private final AppointmentService appointmentService;
    private final DiagnosisService diagnosisService;
    private final DepartmentService departmentService;
    private final RoomService roomService;
    private final DoctorService doctorService;
    private final NurseService nurseService;
    private final PatientService patientService;
    private final SurgeryService surgeryService;
    private final MedicationService medicationService;
    private final TherapyService therapyService;
    private final RehabilitationService rehabilitationService;

    public ConsoleMenu(
            ConsoleInputReader consoleInputReader,
            AppointmentService appointmentService,
            DiagnosisService diagnosisService,
            DepartmentService departmentService,
            RoomService roomRepository,
            DoctorService doctorService,
            NurseService nurseService,
            PatientService patientService,
            SurgeryService surgeryService,
            MedicationService medicationService,
            TherapyService therapyService,
            RehabilitationService rehabilitationService) {
        this.consoleInputReader = consoleInputReader;
        this.appointmentService = appointmentService;
        this.diagnosisService = diagnosisService;
        this.departmentService = departmentService;
        this.roomService = roomRepository;
        this.doctorService = doctorService;
        this.nurseService = nurseService;
        this.patientService = patientService;
        this.surgeryService = surgeryService;
        this.medicationService = medicationService;
        this.therapyService = therapyService;
        this.rehabilitationService = rehabilitationService;
    }

    public void run() {}
}
