package com.adz1q.hospital.management.ui;

import com.adz1q.hospital.management.ui.menu.*;

public class ConsoleMenu {
    private final ConsoleInputReader consoleInputReader;
    private final ConsoleViewFormatter consoleViewFormatter;

    private final AppointmentMenu appointmentMenu;
    private final DiagnosisMenu diagnosisMenu;
    private final DepartmentMenu departmentMenu;
    private final RoomMenu roomMenu;
    private final DoctorMenu doctorMenu;
    private final NurseMenu nurseMenu;
    private final PatientMenu patientMenu;
    private final SurgeryMenu surgeryMenu;
    private final MedicationMenu medicationMenu;
    private final TherapyMenu therapyMenu;
    private final RehabilitationMenu rehabilitationMenu;

    public ConsoleMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            AppointmentMenu appointmentMenu,
            DiagnosisMenu diagnosisMenu,
            DepartmentMenu departmentMenu,
            RoomMenu roomMenu,
            DoctorMenu doctorMenu,
            NurseMenu nurseMenu,
            PatientMenu patientMenu,
            SurgeryMenu surgeryMenu,
            MedicationMenu medicationMenu,
            TherapyMenu therapyMenu,
            RehabilitationMenu rehabilitationMenu) {
        this.consoleInputReader = consoleInputReader;
        this.consoleViewFormatter = consoleViewFormatter;
        this.appointmentMenu = appointmentMenu;
        this.diagnosisMenu = diagnosisMenu;
        this.departmentMenu = departmentMenu;
        this.roomMenu = roomMenu;
        this.doctorMenu = doctorMenu;
        this.nurseMenu = nurseMenu;
        this.patientMenu = patientMenu;
        this.surgeryMenu = surgeryMenu;
        this.medicationMenu = medicationMenu;
        this.therapyMenu = therapyMenu;
        this.rehabilitationMenu = rehabilitationMenu;
    }

    public void run() {
        while (true) {
            consoleViewFormatter.clearConsole();
            consoleViewFormatter.showMainMenu();
            int choice = consoleInputReader.readInt();

            switch (choice) {
                case 1 -> appointmentMenu.show();
                case 2 -> diagnosisMenu.show();
                case 3 -> departmentMenu.show();
                case 4 -> roomMenu.show();
                case 5 -> doctorMenu.show();
                case 6 -> nurseMenu.show();
                case 7 -> patientMenu.show();
                case 8 -> surgeryMenu.show();
                case 9 -> medicationMenu.show();
                case 10 -> therapyMenu.show();
                case 11 -> rehabilitationMenu.show();
                default -> {
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                }
            }
        }
    }
}
