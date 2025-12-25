package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.model.Patient;
import com.adz1q.hospital.management.model.Room;
import com.adz1q.hospital.management.service.RoomService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class RoomMenu extends Menu {
    private final RoomService roomService;

    public RoomMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            RoomService roomService) {
        super(consoleInputReader, consoleViewFormatter);
        this.roomService = roomService;
    }

    @Override
    public void show() {
        while (true) {
            consoleViewFormatter.clearConsole();
            consoleViewFormatter.showRoomMenu();
            int choice = consoleInputReader.readInt();

            switch (choice) {
                case 1 -> viewAllRooms();
                case 2 -> viewRoomById();
                case 3 -> createNewRoom();
                case 4 -> renameRoom();
                case 5 -> changeRoomDepartment();
                case 6 -> assignPatientToRoom();
                case 7 -> removePatientFromRoom();
                case 8 -> deleteRoom();
                case 9 -> viewPatientsInRoom();
                default -> {
                    System.out.println("Exiting Room Management Menu...");
                    return;
                }
            }
        }
    }

    private void viewAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        consoleViewFormatter.printHeader("All Rooms");

        if (rooms.isEmpty()) {
            consoleViewFormatter.printMessage("No rooms found.");
            if (returnToMenu()) return;
        }

        for (Room room : rooms) {
            consoleViewFormatter.showEntityDetails(room);
        }

        returnToMenu();
    }

    private void viewRoomById() {
        do {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Room ID: ");
                Room room = roomService.getRoom(id);
                consoleViewFormatter.showEntityDetails(room);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }

    private void createNewRoom() {
        while (true) {
            try {
                String name = consoleInputReader.readStringPrompt("Enter Room Name: ");
                UUID departmentId = consoleInputReader.readUUIDPrompt("Enter Department ID: ");
                int availableSlots = consoleInputReader.readIntPrompt("Enter Available Slots: ");
                Room room = roomService.createRoom(name, departmentId, availableSlots);
                consoleViewFormatter.printMessage("Created new room with ID: " + room.getId());
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void renameRoom() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Room ID: ");
                String newName = consoleInputReader.readStringPrompt("Enter New Room Name: ");
                roomService.renameRoom(id, newName);
                consoleViewFormatter.printMessage("Renamed room with ID: " + id);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void changeRoomDepartment() {
        while (true) {
            try {
                UUID roomId = consoleInputReader.readUUIDPrompt("Enter Room ID: ");
                UUID departmentId = consoleInputReader.readUUIDPrompt("Enter New Department ID: ");
                roomService.changeRoomDepartment(roomId, departmentId);
                consoleViewFormatter.printMessage("Changed department for room with ID: " + roomId);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void assignPatientToRoom() {
        while (true) {
            try {
                UUID roomId = consoleInputReader.readUUIDPrompt("Enter Room ID: ");
                UUID patientId = consoleInputReader.readUUIDPrompt("Enter Patient ID: ");
                roomService.assignPatientToRoom(roomId, patientId);
                consoleViewFormatter.printMessage("Assigned patient to room with ID: " + roomId);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void removePatientFromRoom() {
        while (true) {
            try {
                UUID roomId = consoleInputReader.readUUIDPrompt("Enter Room ID: ");
                UUID patientId = consoleInputReader.readUUIDPrompt("Enter Patient ID: ");
                roomService.removePatientFromRoom(roomId, patientId);
                consoleViewFormatter.printMessage("Removed patient from room with ID: " + roomId);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void deleteRoom() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Room ID: ");
                roomService.deleteRoom(id);
                consoleViewFormatter.printMessage("Deleted room with ID: " + id);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void viewPatientsInRoom() {
        do {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Room ID: ");
                Set<Patient> patients = roomService.getPatientsInRoom(id);

                consoleViewFormatter.printHeader("Patients in Room " + id);
                if (patients.isEmpty()) {
                    consoleViewFormatter.printMessage("No patients found in this room.");
                    if (returnToMenu()) return;
                }

                for (Patient patient : patients) {
                    consoleViewFormatter.showEntityDetails(patient);
                }

                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }
}
