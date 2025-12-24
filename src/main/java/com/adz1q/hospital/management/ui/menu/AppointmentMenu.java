package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.exception.AppointmentNotFoundException;
import com.adz1q.hospital.management.model.*;
import com.adz1q.hospital.management.service.*;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

import java.time.LocalDate;
import java.util.*;

public class AppointmentMenu extends Menu {
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final DiagnosisService diagnosisService;
    private final SurgeryService surgeryService;
    private final MedicationService medicationService;
    private final TherapyService therapyService;
    private final RehabilitationService rehabilitationService;

    public AppointmentMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            AppointmentService appointmentService,
            DoctorService doctorService,
            PatientService patientService,
            DiagnosisService diagnosisService,
            SurgeryService surgeryService,
            MedicationService medicationService,
            TherapyService therapyService,
            RehabilitationService rehabilitationService) {
        super(consoleInputReader, consoleViewFormatter);
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.diagnosisService = diagnosisService;
        this.surgeryService = surgeryService;
        this.medicationService = medicationService;
        this.therapyService = therapyService;
        this.rehabilitationService = rehabilitationService;
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

    private Surgery scheduleSurgery() {
        while (true) {
            try {
                String surgeryDescription = consoleInputReader.readStringPrompt("Enter Surgery Description: ");
                String prescriptionDateStr = consoleInputReader.readStringPrompt("Enter Prescription Date (YYYY-MM-DD): ");
                String surgeryDateStr = consoleInputReader.readStringPrompt("Enter Surgery Date (YYYY-MM-DD): ");
                UUID doctorId = consoleInputReader.readUUIDPrompt("Enter Surgery Doctor ID: ");

                LocalDate prescriptionDate = LocalDate.parse(prescriptionDateStr);
                LocalDate surgeryDate = LocalDate.parse(surgeryDateStr);
                Doctor doctor = doctorService.getDoctor(doctorId);
                Surgery surgery = surgeryService.scheduleSurgery(surgeryDescription, prescriptionDate, doctor, surgeryDate);
                consoleViewFormatter.printMessage("Surgery scheduled successfully with ID: " + surgery.getId());
                return surgery;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error scheduling surgery: " + e.getMessage());
                if (shouldReturn()) return null;
            }
        }
    }

    private Medication prescribeMedication() {
        while (true) {
            try {
                String medicationDescription = consoleInputReader.readStringPrompt("Enter Medication Description: ");
                String prescriptionDateStr = consoleInputReader.readStringPrompt("Enter Prescription Date (YYYY-MM-DD): ");

                List<String> medicines = new ArrayList<>();
                do {
                    String medicine = consoleInputReader.readStringPrompt("Enter Medicine Name (or press enter to finish): ");
                    if (medicine.isBlank()) {
                        break;
                    }
                    medicines.add(medicine);
                } while (true);

                LocalDate prescriptionDate = LocalDate.parse(prescriptionDateStr);
                Medication medication = medicationService.prescribeMedication(medicationDescription, prescriptionDate, medicines);
                consoleViewFormatter.printMessage("Medication prescribed successfully with ID: " + medication.getId());
                return medication;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error prescribing medication: " + e.getMessage());
                if (shouldReturn()) return null;
            }
        }
    }

    private Therapy prescribeTherapy() {
        while (true) {
            try {
                String therapyDescription = consoleInputReader.readStringPrompt("Enter Therapy Description: ");
                String prescriptionDateStr = consoleInputReader.readStringPrompt("Enter Prescription Date (YYYY-MM-DD): ");
                String therapyName = consoleInputReader.readStringPrompt("Enter Therapy Name: ");

                LocalDate prescriptionDate = LocalDate.parse(prescriptionDateStr);
                Therapy therapy = therapyService.prescribeTherapy(therapyDescription, prescriptionDate, therapyName);
                consoleViewFormatter.printMessage("Therapy prescribed successfully with ID: " + therapy.getId());
                return therapy;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error prescribing therapy: " + e.getMessage());
                if (shouldReturn()) return null;
            }
        }
    }

    private Rehabilitation prescribeRehabilitation() {
        while (true) {
            try {
                String rehabilitationDescription = consoleInputReader.readStringPrompt("Enter Rehabilitation Description: ");
                String prescriptionDateStr = consoleInputReader.readStringPrompt("Enter Prescription Date (YYYY-MM-DD): ");

                List<String> therapies = new ArrayList<>();
                do {
                    String therapy = consoleInputReader.readStringPrompt("Enter Therapy Name for Rehabilitation (or press enter to finish): ");
                    if (therapy.isBlank()) {
                        break;
                    }
                    therapies.add(therapy);
                } while (true);

                LocalDate prescriptionDate = LocalDate.parse(prescriptionDateStr);
                Rehabilitation rehabilitation = rehabilitationService.prescribeRehabilitation(prescriptionDate, rehabilitationDescription, therapies);
                consoleViewFormatter.printMessage("Rehabilitation prescribed successfully with ID: " + rehabilitation.getId());
                return rehabilitation;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error prescribing rehabilitation: " + e.getMessage());
                if (shouldReturn()) return null;
            }
        }
    }

    private void viewAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        consoleViewFormatter.printHeader("All Appointments");
        for (Appointment appointment : appointments) {
            consoleViewFormatter.showEntityDetails(appointment);
        }

        returnToMenu();
    }

    private void viewAppointmentById() {
        do {
            try {
                UUID surgeryId = consoleInputReader.readUUIDPrompt("Enter Appointment ID: ");
                Appointment appointment = appointmentService.getAppointment(surgeryId);
                consoleViewFormatter.showEntityDetails(appointment);
                if (returnToMenu()) return;
            } catch (AppointmentNotFoundException e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }

    private void scheduleNewAppointment() {
        while (true) {
            try {
                UUID patientId = consoleInputReader.readUUIDPrompt("Enter Patient ID: ");
                UUID doctorId = consoleInputReader.readUUIDPrompt("Enter Doctor ID: ");
                String dateStr = consoleInputReader.readStringPrompt("Enter Appointment Date (YYYY-MM-DD): ");

                Patient patient = patientService.getPatient(patientId);
                Doctor doctor = doctorService.getDoctor(doctorId);
                LocalDate date = LocalDate.parse(dateStr);

                Appointment appointment = appointmentService.scheduleAppointment(doctor, patient, date);
                consoleViewFormatter.printMessage("Appointment scheduled successfully with ID: " + appointment.getId());
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void rescheduleAppointment() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Appointment ID: ");
                String newDateStr = consoleInputReader.readStringPrompt("Enter New Appointment Date (YYYY-MM-DD): ");
                LocalDate newDate = LocalDate.parse(newDateStr);

                appointmentService.rescheduleAppointment(id, newDate);
                consoleViewFormatter.printMessage("Appointment rescheduled successfully.");
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void changeAppointmentDoctor() {
        while (true) {
            try {
                UUID appointmentId = consoleInputReader.readUUIDPrompt("Enter Appointment ID: ");
                UUID newDoctorId = consoleInputReader.readUUIDPrompt("Enter New Doctor ID: ");

                Doctor newDoctor = doctorService.getDoctor(newDoctorId);
                appointmentService.changeAppointmentDoctor(appointmentId, newDoctor);
                consoleViewFormatter.printMessage("Appointment doctor changed successfully.");
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void completeAppointment() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Appointment ID: ");
                String diagnosisDescription = consoleInputReader.readStringPrompt("Enter Diagnosis Description: ");

                Set<Treatment> treatments = new HashSet<>();
                do {
                    String treatmentStr = consoleInputReader.readStringPrompt("Enter Treatment Name (or press enter to finish): ");
                    if (treatmentStr.isBlank()) {
                        break;
                    }

                    switch (treatmentStr.trim().toLowerCase()) {
                        case "surgery" -> {
                            Surgery surgery = scheduleSurgery();
                            if (surgery != null) treatments.add(surgery);

                        }
                        case "medication" -> {
                            Medication medication = prescribeMedication();
                            if (medication != null) treatments.add(medication);

                        }
                        case "therapy" -> {
                            Therapy therapy = prescribeTherapy();
                            if (therapy != null) treatments.add(therapy);
                        }
                        case "rehabilitation" -> {
                            Rehabilitation rehabilitation = prescribeRehabilitation();
                            if (rehabilitation != null) treatments.add(rehabilitation);
                        }
                        default -> consoleViewFormatter.printMessage("Unknown treatment type. Please try again.");
                    }
                } while (true);

                Diagnosis diagnosis = diagnosisService.diagnosePatient(diagnosisDescription, treatments);
                appointmentService.completeAppointment(id, diagnosis);
                consoleViewFormatter.printMessage("Appointment completed successfully.");
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void cancelAppointment() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Appointment ID: ");
                appointmentService.cancelAppointment(id);
                consoleViewFormatter.printMessage("Appointment cancelled successfully.");
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage("Error: " + e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }
}
