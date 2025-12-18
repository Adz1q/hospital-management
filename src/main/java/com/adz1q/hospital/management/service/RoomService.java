package com.adz1q.hospital.management.service;

import com.adz1q.hospital.management.exception.DepartmentNotFoundException;
import com.adz1q.hospital.management.exception.PatientNotFoundException;
import com.adz1q.hospital.management.exception.RoomNotFoundException;
import com.adz1q.hospital.management.model.Department;
import com.adz1q.hospital.management.model.Patient;
import com.adz1q.hospital.management.model.Room;
import com.adz1q.hospital.management.repository.RoomRepository;
import com.adz1q.hospital.management.util.Logger;

import java.util.List;
import java.util.UUID;

public class RoomService {
    private final RoomRepository roomRepository;
    private final DepartmentService departmentService;
    private final PatientService patientService;

    public RoomService(
            RoomRepository roomRepository,
            DepartmentService departmentService,
            PatientService patientService) {
        this.roomRepository = roomRepository;
        this.departmentService = departmentService;
        this.patientService = patientService;
    }

    public Room createRoom(
            String name,
            UUID departmentId,
            int availableSlots)
            throws DepartmentNotFoundException {
        Department department = departmentService.getDepartment(departmentId);
        Room newRoom = new Room(
                name,
                department,
                availableSlots);
        Logger.info("Created room with ID: " + newRoom.getId());
        return roomRepository.save(newRoom);
    }

    public Room getRoom(UUID roomId)
            throws RoomNotFoundException {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room with this ID does not exist."));
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void deleteRoom(UUID roomId)
            throws RoomNotFoundException {
        getRoom(roomId);
        roomRepository.deleteById(roomId);
        Logger.info("Deleted room with ID: " + roomId);
    }

    public void renameRoom(
            UUID roomId,
            String newName)
            throws RoomNotFoundException {
        Room room = getRoom(roomId);
        room.rename(newName);
        Logger.info("Renamed room with ID: " + roomId);
    }

    public void changeRoomDepartment(
            UUID roomId,
            UUID newDepartmentId)
            throws RoomNotFoundException, DepartmentNotFoundException {
        Room room = getRoom(roomId);
        Department newDepartment = departmentService.getDepartment(newDepartmentId);
        room.changeDepartment(newDepartment);
        Logger.info("Changed department of room with ID: " + roomId);
    }

    public void assignPatientToRoom(
            UUID roomId,
            UUID patientId)
            throws RoomNotFoundException,
            PatientNotFoundException {
        Room room = getRoom(roomId);
        Patient patient = patientService.getPatient(patientId);
        room.addPatient(patient);
        Logger.info("Assigned new patient to room with ID: " + roomId);
    }

    public void removePatientFromRoom(
            UUID roomId,
            UUID patientId)
            throws RoomNotFoundException,
            PatientNotFoundException {
        Room room = getRoom(roomId);
        Patient patient = patientService.getPatient(patientId);
        room.removePatient(patient);
        Logger.info("Removed patient from room with ID: " + roomId);
    }
}
