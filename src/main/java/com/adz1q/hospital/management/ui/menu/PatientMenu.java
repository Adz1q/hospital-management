package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.model.Diagnosis;
import com.adz1q.hospital.management.model.Patient;
import com.adz1q.hospital.management.model.Treatment;
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
                case 4 -> viewPatientsByFirstNameAndLastName();
                case 5 -> viewPatientsByLastName();
                case 6 -> viewPatientDocumentation();
                case 7 -> viewPatientTreatmentHistory();
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

        if (patients.isEmpty()) {
            consoleViewFormatter.printMessage("No patients found.");
            if (returnToMenu()) return;
        }

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
            } catch (Exception e) {
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

    private void viewPatientsByFirstNameAndLastName() {
        do {
            try {
                String firstName = consoleInputReader.readStringPrompt("Enter First Name: ");
                String lastName = consoleInputReader.readStringPrompt("Enter Last Name: ");
                List<Patient> patients = patientService.getPatientsByFirstAndLastName(firstName, lastName);

                consoleViewFormatter.printHeader("Patients with name: " + firstName + " " + lastName);
                if (patients.isEmpty()) {
                    consoleViewFormatter.printMessage("No patients found.");
                    if (returnToMenu()) return;
                }

                for (Patient patient : patients) {
                    consoleViewFormatter.showEntityDetails(patient);
                }

                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }

    private void viewPatientsByLastName() {
        do {
            try {
                String lastName = consoleInputReader.readStringPrompt("Enter Last Name: ");
                List<Patient> patients = patientService.getPatientsByLastName(lastName);

                consoleViewFormatter.printHeader("Patients with last name: " + lastName);
                if (patients.isEmpty()) {
                    consoleViewFormatter.printMessage("No patients found.");
                    if (returnToMenu()) return;
                }

                for (Patient patient : patients) {
                    consoleViewFormatter.showEntityDetails(patient);
                }

                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }

    private void viewPatientDocumentation() {
        do {
            try {
                UUID patientId = consoleInputReader.readUUIDPrompt("Enter Patient ID: ");
                List<Diagnosis> documentation = patientService.getPatientDocumentation(patientId);

                consoleViewFormatter.printHeader("Patient Documentation for ID: " + patientId);
                if (documentation.isEmpty()) {
                    consoleViewFormatter.printMessage("No documentation found for this patient.");
                    if (returnToMenu()) return;
                }

                for (Diagnosis diagnosis : documentation) {
                    consoleViewFormatter.showEntityDetails(diagnosis);
                }

                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }

    private void viewPatientTreatmentHistory() {
        do {
            try {
                UUID patientId = consoleInputReader.readUUIDPrompt("Enter Patient ID: ");
                List<Treatment> treatments = patientService.getPatientTreatments(patientId);

                consoleViewFormatter.printHeader("Patient Treatment History for ID: " + patientId);
                if (treatments.isEmpty()) {
                    consoleViewFormatter.printMessage("No treatment history found for this patient.");
                    if (returnToMenu()) return;
                }

                for (Treatment treatment : treatments) {
                    consoleViewFormatter.showEntityDetails(treatment);
                }

                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }
}
