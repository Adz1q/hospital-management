package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.model.Medication;
import com.adz1q.hospital.management.service.MedicationService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

import java.util.List;
import java.util.UUID;

public class MedicationMenu extends Menu {
    private final MedicationService medicationService;

    public MedicationMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            MedicationService medicationService) {
        super(consoleInputReader, consoleViewFormatter);
        this.medicationService = medicationService;
    }

    @Override
    public void show() {
        while (true) {
            consoleViewFormatter.clearConsole();
            consoleViewFormatter.showMedicationMenu();
            int choice = consoleInputReader.readInt();

            switch (choice) {
                case 1 -> viewAllMedications();
                case 2 -> viewMedicationById();
                default -> {
                    System.out.println("Exiting Medication Management Menu...");
                    return;
                }
            }
        }
    }

    private void viewAllMedications() {
        List<Medication> medications = medicationService.getAllMedications();
        consoleViewFormatter.printHeader("All Medications");

        if (medications.isEmpty()) {
            consoleViewFormatter.printMessage("No medications found.");
            if (returnToMenu()) return;
        }

        for (Medication medication : medications) {
            consoleViewFormatter.showEntityDetails(medication);
        }

        returnToMenu();
    }

    private void viewMedicationById() {
        do {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Medication ID: ");
                Medication medication = medicationService.getMedication(id);
                consoleViewFormatter.showEntityDetails(medication);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }
}
