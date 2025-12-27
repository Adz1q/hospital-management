package com.adz1q.hospital.management.ui;

import com.adz1q.hospital.management.model.Describable;
import com.adz1q.hospital.management.model.Specialization;

import java.util.List;

public class ConsoleViewFormatter {
    private static final int HEADER_SEPARATOR_LENGTH = 25;

    public void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    public void printHeader(String title) {
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(HEADER_SEPARATOR_LENGTH))
                .append(" ")
                .append(title)
                .append(" ")
                .append("=".repeat(HEADER_SEPARATOR_LENGTH));
        System.out.println(sb);
    }

    public void printBottomSeparator(String title) {
        System.out.println("=".repeat((title.length() + 2) + (HEADER_SEPARATOR_LENGTH * 2)));
    }

    public void printOptions(List<String> options) {
        for (int i = 1; i <= options.size(); i++) {
            if (i / 10 == 0) {
                System.out.printf("%d.  %s%n", i, options.get(i - 1));
            } else {
                System.out.printf("%d. %s%n", i, options.get(i - 1));
            }
        }
    }

    public void printExitPrompt() {
        System.out.println("Press enter to exit...");
    }

    public void printReturnPrompt() {
        System.out.println("Press enter to return to main menu...");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void showEntityDetails(Describable entity) {
        printHeader(entity.getClass().getName());
        entity.showDetails();
        printBottomSeparator(entity.getClass().getName());
    }

    public void showDoctorSpecializations() {
        String title = "Doctor Specializations";
        printHeader(title);
        for (Specialization specialization : Specialization.values()) {
            printMessage(specialization.getName());
        }
        printBottomSeparator(title);
    }

    public void showTreatmentMethods() {
        String title = "Treatment Methods";
        List<String> methods = List.of(
                "Surgery",
                "Medication",
                "Therapy",
                "Rehabilitation");
        printHeader(title);
        for (String method : methods) {
            printMessage(method);
        }
        printBottomSeparator(title);
    }

    public void showMainMenu() {
        String title = "Hospital Management";
        List<String> options = List.of(
                "Manage Appointments",
                "Manage Diagnoses",
                "Manage Departments",
                "Manage Rooms",
                "Manage Doctors",
                "Manage Nurses",
                "Manage Patients",
                "Manage Surgeries",
                "Manage Medications",
                "Manage Therapies",
                "Manage Rehabilitations");

        printHeader(title);
        printOptions(options);
        printExitPrompt();
        printBottomSeparator(title);
    }

    public void showAppointmentMenu() {
        String title = "Appointment Management";
        List<String> options = List.of(
                "View All Appointments",
                "View Appointment by ID",
                "Schedule New Appointment",
                "Reschedule Appointment",
                "Change Appointment Doctor",
                "Complete Appointment",
                "Cancel Appointment");

        printHeader(title);
        printOptions(options);
        printReturnPrompt();
        printBottomSeparator(title);
    }

    public void showDiagnosisMenu() {
        String title = "Diagnosis Management";
        List<String> options = List.of(
                "View All Diagnoses",
                "View Diagnosis by ID");

        printHeader(title);
        printOptions(options);
        printReturnPrompt();
        printBottomSeparator(title);
    }

    public void showDepartmentMenu() {
        String title = "Department Management";
        List<String> options = List.of(
                "View All Departments",
                "View Department by ID",
                "Create New Department",
                "Rename Department",
                "Close Department",
                "View Nurses in Department",
                "View Rooms in Department");

        printHeader(title);
        printOptions(options);
        printReturnPrompt();
        printBottomSeparator(title);
    }

    public void showRoomMenu() {
        String title = "Room Management";
        List<String> options = List.of(
                "View All Rooms",
                "View Room by ID",
                "Create New Room",
                "Rename Room",
                "Change Room Department",
                "Assign Patient to Room",
                "Remove Patient from Room",
                "Delete Room",
                "View Patients in Room");

        printHeader(title);
        printOptions(options);
        printReturnPrompt();
        printBottomSeparator(title);
    }

    public void showDoctorMenu() {
        String title = "Doctor Management";
        List<String> options = List.of(
                "View All Doctors",
                "View Doctor by ID",
                "Hire New Doctor",
                "Dismiss Doctor",
                "Rehire Doctor",
                "Update Doctor Specializations",
                "View Doctors by First Name and Last Name",
                "View Doctors by Last Name",
                "View Doctor Surgeries");

        printHeader(title);
        printOptions(options);
        printReturnPrompt();
        printBottomSeparator(title);
    }

    public void showNurseMenu() {
        String title = "Nurse Management";
        List<String> options = List.of(
                "View All Nurses",
                "View Nurse by ID",
                "Hire New Nurse",
                "Dismiss Nurse",
                "Rehire Nurse",
                "Change Nurse Department",
                "View Nurses by First Name and Last Name",
                "View Nurses by Last Name");

        printHeader(title);
        printOptions(options);
        printReturnPrompt();
        printBottomSeparator(title);
    }

    public void showPatientMenu() {
        String title = "Patient Management";
        List<String> options = List.of(
                "View All Patients",
                "View Patient by ID",
                "Register New Patient",
                "View Patients by First Name and Last Name",
                "View Patients by Last Name",
                "View Patient Documentation",
                "View Patient Treatment History");

        printHeader(title);
        printOptions(options);
        printReturnPrompt();
        printBottomSeparator(title);
    }

    public void showSurgeryMenu() {
        String title = "Surgery Management";
        List<String> options = List.of(
                "View All Surgeries",
                "View Surgery by ID",
                "Reschedule Surgery",
                "Change Surgery Doctor",
                "Complete Surgery",
                "Cancel Surgery");

        printHeader(title);
        printOptions(options);
        printReturnPrompt();
        printBottomSeparator(title);
    }

    public void showMedicationMenu() {
        String title = "Medication Management";
        List<String> options = List.of(
                "View All Medications",
                "View Medication by ID");

        printHeader(title);
        printOptions(options);
        printReturnPrompt();
        printBottomSeparator(title);
    }

    public void showTherapyMenu() {
        String title = "Therapy Management";
        List<String> options = List.of(
                "View All Therapies",
                "View Therapy by ID");

        printHeader(title);
        printOptions(options);
        printReturnPrompt();
        printBottomSeparator(title);
    }

    public void showRehabilitationMenu() {
        String title = "Rehabilitation Management";
        List<String> options = List.of(
                "View All Rehabilitations",
                "View Rehabilitation by ID");

        printHeader(title);
        printOptions(options);
        printReturnPrompt();
        printBottomSeparator(title);
    }
}
