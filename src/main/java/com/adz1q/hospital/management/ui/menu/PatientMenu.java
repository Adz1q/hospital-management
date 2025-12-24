package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.exception.PatientNotFoundException;
import com.adz1q.hospital.management.model.Patient;
import com.adz1q.hospital.management.service.PatientService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PatientMenu extends Menu {
    private final PatientService patientService;

    public PatientMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            PatientService patientService) {
        super(consoleInputReader, consoleViewFormatter);
        this.patientService = patientService;
    }

    @Override
    public void show() {
        while (true) {
            consoleViewFormatter.clearConsole();
            consoleViewFormatter.showPatientMenu();
            int choice = consoleInputReader.readInt();

            switch (choice) {
                case 1 -> viewAllPatients();
                case 2 -> viewPatientById();
                case 3 -> registerNewPatient();
                default -> {
                    System.out.println("Exiting Patient Management Menu...");
                    return;
                }
            }
        }
    }

    private void viewAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        consoleViewFormatter.printHeader("All Patients");
        for (Patient patient : patients) {
            consoleViewFormatter.showEntityDetails(patient);
        }

        returnToMenu();
    }

    private void viewPatientById() {
        do {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Patient ID: ");
                Patient patient = patientService.getPatient(id);
                consoleViewFormatter.showEntityDetails(patient);
                if (returnToMenu()) return;
            } catch (PatientNotFoundException e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }

    private void registerNewPatient() {
        while (true) {
            try {
                String firstName = consoleInputReader.readStringPrompt("Enter First Name: ");
                String lastName = consoleInputReader.readStringPrompt("Enter Last Name: ");
                String birthDateStr = consoleInputReader.readStringPrompt("Enter Birth Date (YYYY-MM-DD): ");
                String pesel = consoleInputReader.readStringPrompt("Enter PESEL (optional, press enter to skip): ");
                Patient newPatient;

                if (pesel.isBlank()) {
                    newPatient = patientService.registerPatient(
                            firstName,
                            lastName,
                            LocalDate.parse(birthDateStr));
                } else {
                    newPatient = patientService.registerPatient(
                            firstName,
                            lastName,
                            LocalDate.parse(birthDateStr),
                            pesel);
                }

                consoleViewFormatter.printMessage("Patient registered successfully with ID: " + newPatient.getId());
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }
}
