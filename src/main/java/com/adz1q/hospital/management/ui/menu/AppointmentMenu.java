package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.exception.AppointmentNotFoundException;
import com.adz1q.hospital.management.model.Appointment;
import com.adz1q.hospital.management.service.AppointmentService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

import java.util.List;
import java.util.UUID;

public class AppointmentMenu extends Menu {
    private final AppointmentService appointmentService;

    public AppointmentMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            AppointmentService appointmentService) {
        super(consoleInputReader, consoleViewFormatter);
        this.appointmentService = appointmentService;
    }

    @Override
    public void show() {
        while (true) {
            consoleViewFormatter.clearConsole();
            consoleViewFormatter.showAppointmentMenu();
            int choice = consoleInputReader.readInt();

            switch (choice) {
                case 1 -> viewAllAppointments();
                case 2 -> viewAppointmentById();
                case 3 -> scheduleNewAppointment();
                case 4 -> rescheduleAppointment();
                case 5 -> changeAppointmentDoctor();
                case 6 -> completeAppointment();
                case 7 -> cancelAppointment();
                default -> {
                    System.out.println("Exiting Appointment Management Menu...");
                    return;
                }
            }
        }
    }

    private void viewAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        consoleViewFormatter.printHeader("All Appointments");
        for (Appointment appointment : appointments) {
            consoleViewFormatter.showEntityDetails(appointment);
        }

        consoleViewFormatter.printReturnPrompt();
        consoleInputReader.readString();
    }

    private void viewAppointmentById() {
        UUID id = consoleInputReader.readUUIDPrompt("Enter Appointment ID: ");

        do {
            try {
                Appointment appointment = appointmentService.getAppointment(id);
                consoleViewFormatter.showEntityDetails(appointment);
            } catch (AppointmentNotFoundException e) {
                consoleViewFormatter.printMessage("Appointment not found. Please try again or press enter to return.");
                id = consoleInputReader.readUUIDPrompt("Enter Appointment ID: ");
            }
        } while (id != null);
    }

    private void scheduleNewAppointment() {}

    private void rescheduleAppointment() {}

    private void changeAppointmentDoctor() {}

    private void completeAppointment() {}

    private void cancelAppointment() {}
}
