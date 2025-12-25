package com.adz1q.hospital.management.config;

import com.adz1q.hospital.management.repository.*;
import com.adz1q.hospital.management.service.*;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleMenu;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;
import com.adz1q.hospital.management.ui.menu.*;

import java.nio.file.Path;
import java.util.Scanner;

public class ApplicationContext {
    private static final String CSV_PATH = ("src/main/resources/csv/");
    private static final Path APPOINTMENTS_CSV = Path.of(CSV_PATH + "appointments.csv");
    private static final Path DIAGNOSES_CSV = Path.of(CSV_PATH + "diagnoses.csv");
    private static final Path DEPARTMENTS_CSV = Path.of(CSV_PATH + "departments.csv");
    private static final Path ROOMS_CSV = Path.of(CSV_PATH + "rooms.csv");
    private static final Path DOCTORS_CSV = Path.of(CSV_PATH + "doctors.csv");
    private static final Path NURSES_CSV = Path.of(CSV_PATH + "nurses.csv");
    private static final Path PATIENTS_CSV = Path.of(CSV_PATH + "patients.csv");
    private static final Path SURGERIES_CSV = Path.of(CSV_PATH + "surgeries.csv");
    private static final Path MEDICATIONS_CSV = Path.of(CSV_PATH + "medications.csv");
    private static final Path THERAPIES_CSV = Path.of(CSV_PATH + "therapies.csv");
    private static final Path REHABILITATIONS_CSV = Path.of(CSV_PATH + "rehabilitations.csv");

    private final AppointmentRepository appointmentRepository =
            new AppointmentRepository(APPOINTMENTS_CSV);
    private final DiagnosisRepository diagnosisRepository =
            new DiagnosisRepository(DIAGNOSES_CSV);
    private final DepartmentRepository departmentRepository =
            new DepartmentRepository(DEPARTMENTS_CSV);
    private final RoomRepository roomRepository =
            new RoomRepository(ROOMS_CSV);
    private final DoctorRepository doctorRepository =
            new DoctorRepository(DOCTORS_CSV);
    private final NurseRepository nurseRepository =
            new NurseRepository(NURSES_CSV);
    private final PatientRepository patientRepository =
            new PatientRepository(PATIENTS_CSV);
    private final SurgeryRepository surgeryRepository =
            new SurgeryRepository(SURGERIES_CSV);
    private final MedicationRepository medicationRepository =
            new MedicationRepository(MEDICATIONS_CSV);
    private final TherapyRepository therapyRepository =
            new TherapyRepository(THERAPIES_CSV);
    private final RehabilitationRepository rehabilitationRepository =
            new RehabilitationRepository(REHABILITATIONS_CSV);

    private final AppointmentService appointmentService =
            new AppointmentService(appointmentRepository);
    private final DiagnosisService diagnosisService =
            new DiagnosisService(diagnosisRepository);
    private final DepartmentService departmentService =
            new DepartmentService(departmentRepository, roomRepository, nurseRepository);
    private final RoomService roomService =
            new RoomService(roomRepository, departmentRepository, patientRepository);
    private final DoctorService doctorService =
            new DoctorService(doctorRepository);
    private final NurseService nurseService =
            new NurseService(nurseRepository, departmentRepository);
    private final PatientService patientService =
            new PatientService(patientRepository);
    private final SurgeryService surgeryService =
            new SurgeryService(surgeryRepository, doctorRepository);
    private final MedicationService medicationService =
            new MedicationService(medicationRepository);
    private final TherapyService therapyService =
            new TherapyService(therapyRepository);
    private final RehabilitationService rehabilitationService =
            new RehabilitationService(rehabilitationRepository);


    private final Scanner scanner = new Scanner(System.in);
    private final ConsoleInputReader consoleInputReader = new ConsoleInputReader(scanner);
    private final ConsoleViewFormatter consoleViewFormatter = new ConsoleViewFormatter();

    private final AppointmentMenu appointmentMenu =
            new AppointmentMenu(consoleInputReader, consoleViewFormatter, appointmentService, doctorService, patientService, diagnosisService, surgeryService, medicationService, therapyService, rehabilitationService);
    private final DiagnosisMenu diagnosisMenu =
            new DiagnosisMenu(consoleInputReader, consoleViewFormatter, diagnosisService);
    private final DepartmentMenu departmentMenu =
            new DepartmentMenu(consoleInputReader, consoleViewFormatter, departmentService);
    private final RoomMenu roomMenu =
            new RoomMenu(consoleInputReader, consoleViewFormatter, roomService);
    private final DoctorMenu doctorMenu =
            new DoctorMenu(consoleInputReader, consoleViewFormatter, doctorService, surgeryService);
    private final NurseMenu nurseMenu =
            new NurseMenu(consoleInputReader, consoleViewFormatter, nurseService);
    private final PatientMenu patientMenu =
            new PatientMenu(consoleInputReader, consoleViewFormatter, patientService);
    private final SurgeryMenu surgeryMenu =
            new SurgeryMenu(consoleInputReader, consoleViewFormatter, surgeryService, doctorService);
    private final MedicationMenu medicationMenu =
            new MedicationMenu(consoleInputReader, consoleViewFormatter, medicationService);
    private final TherapyMenu therapyMenu =
            new TherapyMenu(consoleInputReader, consoleViewFormatter, therapyService);
    private final RehabilitationMenu rehabilitationMenu =
            new RehabilitationMenu(consoleInputReader, consoleViewFormatter, rehabilitationService);

    private final ConsoleMenu consoleMenu = new ConsoleMenu(
            consoleInputReader,
            consoleViewFormatter,
            appointmentMenu,
            diagnosisMenu,
            departmentMenu,
            roomMenu,
            doctorMenu,
            nurseMenu,
            patientMenu,
            surgeryMenu,
            medicationMenu,
            therapyMenu,
            rehabilitationMenu);

    public ConsoleMenu getConsoleMenu() {
        return consoleMenu;
    }
}
