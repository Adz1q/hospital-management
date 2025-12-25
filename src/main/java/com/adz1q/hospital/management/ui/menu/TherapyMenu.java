package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.model.Therapy;
import com.adz1q.hospital.management.service.TherapyService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

import java.util.List;
import java.util.UUID;

public class TherapyMenu extends Menu {
    private final TherapyService therapyService;

    public TherapyMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            TherapyService therapyService) {
        super(consoleInputReader, consoleViewFormatter);
        this.therapyService = therapyService;
    }

    @Override
    public void show() {
        while (true) {
            consoleViewFormatter.clearConsole();
            consoleViewFormatter.showTherapyMenu();
            int choice = consoleInputReader.readInt();

            switch (choice) {
                case 1 -> viewAllTherapies();
                case 2 -> viewTherapyById();
                default -> {
                    System.out.println("Exiting Appointment Management Menu...");
                    return;
                }
            }
        }
    }

    private void viewAllTherapies() {
        List<Therapy> therapies = therapyService.getAllTherapies();
        consoleViewFormatter.printHeader("All Therapies");

        if (therapies.isEmpty()) {
            consoleViewFormatter.printMessage("No therapies found.");
            if (returnToMenu()) return;
        }

        for (Therapy therapy : therapies) {
            consoleViewFormatter.showEntityDetails(therapy);
        }

        returnToMenu();
    }

    private void viewTherapyById() {
        do {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Therapy ID: ");
                Therapy therapy = therapyService.getTherapy(id);
                consoleViewFormatter.showEntityDetails(therapy);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }
}
