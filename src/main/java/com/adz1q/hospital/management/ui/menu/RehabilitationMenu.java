package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.exception.RehabilitationNotFoundException;
import com.adz1q.hospital.management.model.Rehabilitation;
import com.adz1q.hospital.management.service.RehabilitationService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

import java.util.List;
import java.util.UUID;

public class RehabilitationMenu extends Menu {
    private final RehabilitationService rehabilitationService;

    public RehabilitationMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            RehabilitationService rehabilitationService) {
        super(consoleInputReader, consoleViewFormatter);
        this.rehabilitationService = rehabilitationService;
    }

    @Override
    public void show() {
        while (true) {
            consoleViewFormatter.clearConsole();
            consoleViewFormatter.showRehabilitationMenu();
            int choice = consoleInputReader.readInt();

            switch (choice) {
                case 1 -> viewAllRehabilitations();
                case 2 -> viewRehabilitationById();
                default -> {
                    System.out.println("Exiting Appointment Management Menu...");
                    return;
                }
            }
        }
    }

    private void viewAllRehabilitations() {
        List<Rehabilitation> rehabilitations = rehabilitationService.getAllRehabilitations();
        consoleViewFormatter.printHeader("All Rehabilitations");
        for (Rehabilitation rehabilitation : rehabilitations) {
            consoleViewFormatter.showEntityDetails(rehabilitation);
        }

        consoleViewFormatter.printReturnPrompt();
        consoleInputReader.readString();
    }

    private void viewRehabilitationById() {
        do {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Rehabilitation ID: ");
                Rehabilitation rehabilitation = rehabilitationService.getRehabilitation(id);
                consoleViewFormatter.showEntityDetails(rehabilitation);
                if (shouldReturn()) return;
            } catch (RehabilitationNotFoundException e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }
}
