package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.model.Doctor;
import com.adz1q.hospital.management.model.Specialization;
import com.adz1q.hospital.management.service.DoctorService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DoctorMenu extends Menu {
    private final DoctorService doctorService;

    public DoctorMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            DoctorService doctorService) {
        super(consoleInputReader, consoleViewFormatter);
        this.doctorService = doctorService;
    }

    @Override
    public void show() {
        while (true) {
            consoleViewFormatter.clearConsole();
            consoleViewFormatter.showDoctorMenu();
            int choice = consoleInputReader.readInt();

            switch (choice) {
                case 1 -> viewAllDoctors();
                case 2 -> viewDoctorById();
                case 3 -> hireNewDoctor();
                case 4 -> dismissDoctor();
                case 5 -> rehireDoctor();
                case 6 -> updateDoctorSpecializations();
                default -> {
                    System.out.println("Exiting Doctor Management Menu...");
                    return;
                }
            }
        }
    }

    private void viewAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        consoleViewFormatter.printHeader("All Doctors");
        for (Doctor doctor : doctors) {
            consoleViewFormatter.showEntityDetails(doctor);
        }

        returnToMenu();
    }

    private void viewDoctorById() {
        do {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Doctor ID: ");
                Doctor doctor = doctorService.getDoctor(id);
                consoleViewFormatter.showEntityDetails(doctor);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }

    private void hireNewDoctor() {
        while (true) {
            try {
                String firstName = consoleInputReader.readStringPrompt("Enter First Name: ");
                String lastName = consoleInputReader.readStringPrompt("Enter Last Name: ");
                String birthDateStr = consoleInputReader.readStringPrompt("Enter Birth Date (YYYY-MM-DD): ");
                String pesel = consoleInputReader.readStringPrompt("Enter PESEL (optional, press enter to skip): ");
                Set<Specialization> specializations = new HashSet<>();
                Doctor newDoctor;

                do {
                    String specializationStr = consoleInputReader.readStringPrompt("Enter Specialization (or press enter to finish): ");
                    if (specializationStr.isBlank()) break;

                    try {
                        Specialization specialization = Specialization.valueOf(specializationStr.trim().toUpperCase());
                        specializations.add(specialization);
                    } catch (Exception e) {
                        consoleViewFormatter.printMessage("Invalid specialization. Please try again.");
                    }
                } while (true);

                if (pesel.isBlank()) {
                    newDoctor = doctorService.hireDoctor(
                            firstName,
                            lastName,
                            LocalDate.parse(birthDateStr),
                            specializations);
                } else {
                    newDoctor = doctorService.hireDoctor(
                            firstName,
                            lastName,
                            LocalDate.parse(birthDateStr),
                            pesel,
                            specializations);
                }

                consoleViewFormatter.printMessage("Doctor hired successfully with ID: " + newDoctor.getId());
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void dismissDoctor() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Doctor ID to dismiss: ");
                doctorService.dismissDoctor(id);
                consoleViewFormatter.printMessage("Doctor dismissed successfully.");
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void rehireDoctor() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Doctor ID to rehire: ");
                doctorService.rehireDoctor(id);
                consoleViewFormatter.printMessage("Doctor rehired successfully.");
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void updateDoctorSpecializations() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Doctor ID to update specializations: ");
                Doctor doctor = doctorService.getDoctor(id);
                Specialization specialization;

                do {
                    try {
                        String specializationStr = consoleInputReader.readStringPrompt("Enter Specialization (or press enter to finish): ");
                        specialization = Specialization.valueOf(specializationStr.trim().toUpperCase());
                        break;
                    } catch (Exception e) {
                        consoleViewFormatter.printMessage("Invalid specialization. Please try again.");
                    }
                } while (true);

                doctor.addSpecialization(specialization);
                consoleViewFormatter.printMessage("Doctor specializations updated successfully.");
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }
}
