package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.model.Doctor;
import com.adz1q.hospital.management.model.Surgery;
import com.adz1q.hospital.management.service.DoctorService;
import com.adz1q.hospital.management.service.SurgeryService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class SurgeryMenu extends Menu {
    private final SurgeryService surgeryService;
    private final DoctorService doctorService;

    public SurgeryMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            SurgeryService surgeryService,
            DoctorService doctorService) {
        super(consoleInputReader, consoleViewFormatter);
        this.surgeryService = surgeryService;
        this.doctorService = doctorService;
    }

    @Override
    public void show() {
        while (true) {
            consoleViewFormatter.clearConsole();
            consoleViewFormatter.showSurgeryMenu();
            int choice = consoleInputReader.readInt();

            switch (choice) {
                case 1 -> viewAllSurgeries();
                case 2 -> viewSurgeryById();
                case 3 -> scheduleNewSurgery();
                case 4 -> rescheduleSurgery();
                case 5 -> changeSurgeryDoctor();
                case 6 -> completeSurgery();
                case 7 -> cancelSurgery();
                default -> {
                    System.out.println("Exiting Appointment Management Menu...");
                    return;
                }
            }
        }
    }

    private void viewAllSurgeries() {
        List<Surgery> surgeries = surgeryService.getAllSurgeries();
        consoleViewFormatter.printHeader("All Surgeries");
        for (Surgery surgery : surgeries) {
            consoleViewFormatter.showEntityDetails(surgery);
        }

        returnToMenu();
    }

    private void viewSurgeryById() {
        do {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Surgery ID: ");
                Surgery surgery = surgeryService.getSurgery(id);
                consoleViewFormatter.showEntityDetails(surgery);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }

    private void scheduleNewSurgery() {
        while (true) {
            try {
                String surgeryDescription = consoleInputReader.readStringPrompt("Enter Surgery Description: ");
                String prescriptionDateStr = consoleInputReader.readStringPrompt("Enter Prescription Date (YYYY-MM-DD): ");
                String surgeryDateStr = consoleInputReader.readStringPrompt("Enter Surgery Date (YYYY-MM-DD): ");
                UUID doctorId = consoleInputReader.readUUIDPrompt("Enter Surgery Doctor ID: ");

                LocalDate prescriptionDate = LocalDate.parse(prescriptionDateStr);
                LocalDate surgeryDate = LocalDate.parse(surgeryDateStr);
                Doctor doctor = doctorService.getDoctor(doctorId);
                Surgery surgery = surgeryService.scheduleSurgery(surgeryDescription, prescriptionDate, doctor, surgeryDate);
                consoleViewFormatter.printMessage("Surgery scheduled successfully with ID: " + surgery.getId());
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void rescheduleSurgery() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Surgery ID to Reschedule: ");
                String newSurgeryDateStr = consoleInputReader.readStringPrompt("Enter New Surgery Date (YYYY-MM-DD): ");
                LocalDate newSurgeryDate = LocalDate.parse(newSurgeryDateStr);
                surgeryService.rescheduleSurgery(id, newSurgeryDate);
                consoleViewFormatter.printMessage("Surgery rescheduled successfully.");
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void changeSurgeryDoctor() {
        while (true) {
            try {
                UUID surgeryId = consoleInputReader.readUUIDPrompt("Enter Surgery ID to Change Doctor: ");
                UUID newDoctorId = consoleInputReader.readUUIDPrompt("Enter New Doctor ID: ");
                Doctor newDoctor = doctorService.getDoctor(newDoctorId);
                surgeryService.changeSurgeryDoctor(surgeryId, newDoctor);
                consoleViewFormatter.printMessage("Surgery doctor changed successfully.");
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void completeSurgery() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Surgery ID to Complete: ");
                surgeryService.completeSurgery(id);
                consoleViewFormatter.printMessage("Surgery completed successfully.");
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void cancelSurgery() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Surgery ID to Cancel: ");
                surgeryService.cancelSurgery(id);
                consoleViewFormatter.printMessage("Surgery cancelled successfully.");
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }
}
