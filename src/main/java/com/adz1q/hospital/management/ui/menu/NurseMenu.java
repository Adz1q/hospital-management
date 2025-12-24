package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.exception.NurseNotFoundException;
import com.adz1q.hospital.management.model.Department;
import com.adz1q.hospital.management.model.Nurse;
import com.adz1q.hospital.management.service.NurseService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class NurseMenu extends Menu {
    private final NurseService nurseService;

    public NurseMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            NurseService nurseService) {
        super(consoleInputReader, consoleViewFormatter);
        this.nurseService = nurseService;
    }

    @Override
    public void show() {
        while (true) {
            consoleViewFormatter.clearConsole();
            consoleViewFormatter.showNurseMenu();
            int choice = consoleInputReader.readInt();

            switch (choice) {
                case 1 -> viewAllNurses();
                case 2 -> viewNurseById();
                case 3 -> hireNewNurse();
                case 4 -> dismissNurse();
                case 5 -> rehireNurse();
                case 6 -> changeNurseDepartment();
                default -> {
                    System.out.println("Exiting Nurse Management Menu...");
                    return;
                }
            }
        }
    }

    private void viewAllNurses() {
        List<Nurse> nurses = nurseService.getAllNurses();
        consoleViewFormatter.printHeader("All Nurses");
        for (Nurse nurse : nurses) {
            consoleViewFormatter.showEntityDetails(nurse);
        }

        returnToMenu();
    }

    private void viewNurseById() {
        do {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Nurse ID: ");
                Nurse nurse = nurseService.getNurse(id);
                consoleViewFormatter.showEntityDetails(nurse);
                if (returnToMenu()) return;
            } catch (NurseNotFoundException e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }

    private void hireNewNurse() {
        while (true) {
            try {
                String firstName = consoleInputReader.readStringPrompt("Enter First Name: ");
                String lastName = consoleInputReader.readStringPrompt("Enter Last Name: ");
                String birthDateStr = consoleInputReader.readStringPrompt("Enter Birth Date (YYYY-MM-DD): ");
                String pesel = consoleInputReader.readStringPrompt("Enter PESEL (optional, press enter to skip): ");
                UUID departmentId = consoleInputReader.readUUIDPrompt("Enter Department ID: ");

                LocalDate birthDate = LocalDate.parse(birthDateStr);
                Department department = nurseService.getDepartment(departmentId);

                Nurse newNurse;

                if (pesel.isBlank()) {
                    newNurse = nurseService.hireNurse(
                            firstName,
                            lastName,
                            birthDate,
                            department);
                } else {
                    newNurse = nurseService.hireNurse(
                            firstName,
                            lastName,
                            birthDate,
                            pesel,
                            department);
                }

                consoleViewFormatter.printMessage("Patient registered successfully with ID: " + newNurse.getId());
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void dismissNurse() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Nurse ID to dismiss: ");
                nurseService.dismissNurse(id);
                consoleViewFormatter.printMessage("Nurse with ID " + id + " has been dismissed.");
                if (returnToMenu()) return;
            } catch (NurseNotFoundException e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void rehireNurse() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Nurse ID to rehire: ");
                nurseService.rehireNurse(id);
                consoleViewFormatter.printMessage("Nurse with ID " + id + " has been rehired.");
                if (returnToMenu()) return;
            } catch (NurseNotFoundException e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void changeNurseDepartment() {
        while (true) {
            try {
                UUID nurseId = consoleInputReader.readUUIDPrompt("Enter Nurse ID: ");
                UUID departmentId = consoleInputReader.readUUIDPrompt("Enter New Department ID: ");
                Department department = nurseService.getDepartment(departmentId);
                nurseService.changeNurseDepartment(nurseId, department);
                consoleViewFormatter.printMessage("Nurse with ID " + nurseId + " has been moved to new department.");
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }
}
