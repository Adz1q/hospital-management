package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.model.Diagnosis;
import com.adz1q.hospital.management.service.DiagnosisService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

import java.util.List;
import java.util.UUID;

public class DiagnosisMenu extends Menu {
    private final DiagnosisService diagnosisService;

    public DiagnosisMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            DiagnosisService diagnosisService) {
        super(consoleInputReader, consoleViewFormatter);
        this.diagnosisService = diagnosisService;
    }

    @Override
    public void show() {
        while (true) {
            consoleViewFormatter.clearConsole();
            consoleViewFormatter.showDiagnosisMenu();
            int choice = consoleInputReader.readInt();

            switch (choice) {
                case 1 -> viewAllDiagnoses();
                case 2 -> viewDiagnosisById();
                default -> {
                    System.out.println("Exiting Diagnosis Management Menu...");
                    return;
                }
            }
        }
    }

    private void viewAllDiagnoses() {
        List<Diagnosis> diagnoses = diagnosisService.getAllDiagnoses();
        consoleViewFormatter.printHeader("All Diagnoses");

        if (diagnoses.isEmpty()) {
            consoleViewFormatter.printMessage("No diagnoses found.");
            if (returnToMenu()) return;
        }

        for (Diagnosis diagnosis : diagnoses) {
            consoleViewFormatter.showEntityDetails(diagnosis);
        }

        returnToMenu();
    }

    private void viewDiagnosisById() {
        do {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Diagnosis ID: ");
                Diagnosis diagnosis = diagnosisService.getDiagnosis(id);
                consoleViewFormatter.showEntityDetails(diagnosis);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }
}
