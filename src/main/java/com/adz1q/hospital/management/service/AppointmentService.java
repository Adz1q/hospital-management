package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.AppointmentNotFoundException;
import com.adz1q.hospital.management.model.Appointment;
import com.adz1q.hospital.management.model.Diagnosis;
import com.adz1q.hospital.management.model.Doctor;
import com.adz1q.hospital.management.model.Patient;
import com.adz1q.hospital.management.repository.AppointmentRepository;
import com.adz1q.hospital.management.util.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment scheduleAppointment(
            Doctor doctor,
            Patient patient,
            LocalDate date) {
        Appointment newAppointment = new Appointment(
                patient,
                doctor,
                date);
        Logger.info("Scheduled new appointment with ID: " + newAppointment.getId());
        return appointmentRepository.save(newAppointment);
    }

    public Appointment getAppointment(UUID id) throws AppointmentNotFoundException {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with this ID does not exist."));
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public void cancelAppointment(UUID id)
            throws AppointmentNotFoundException {
        getAppointment(id);
        appointmentRepository.deleteById(id);
        Logger.info("Cancelled appointment with ID: " + id);
    }

    public void changeDoctor(
            UUID appointmentId,
            Doctor newDoctor)
            throws AppointmentNotFoundException {
        Appointment appointment = getAppointment(appointmentId);
        appointment.changeDoctor(newDoctor);
        Logger.info("Changed doctor for appointment with ID: " + appointmentId);
    }

    public void rescheduleAppointment(
            UUID appointmentId,
            LocalDate newDate)
            throws AppointmentNotFoundException {
        Appointment appointment = getAppointment(appointmentId);
        appointment.changeDate(newDate);
        Logger.info("Rescheduled appointment with ID: " + appointmentId);
    }

    public void completeAppointment(
            UUID id,
            Diagnosis diagnosis)
            throws AppointmentNotFoundException {
        Appointment appointment = getAppointment(id);
        appointment.completeAppointment(diagnosis);
        appointment.getPatient().addDiagnosis(diagnosis);
        Logger.info("Completed appointment with ID: " + id);
    }
}
