package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.model.Department;
import com.adz1q.hospital.management.model.Nurse;
import com.adz1q.hospital.management.model.Room;
import com.adz1q.hospital.management.service.DepartmentService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

import java.util.List;
import java.util.UUID;

public class DepartmentMenu extends Menu {
    private final DepartmentService departmentService;

    public DepartmentMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            DepartmentService departmentService) {
        super(consoleInputReader, consoleViewFormatter);
        this.departmentService = departmentService;
    }

    @Override
    public void show() {
        while (true) {
            consoleViewFormatter.clearConsole();
            consoleViewFormatter.showDepartmentMenu();
            int choice = consoleInputReader.readInt();

            switch (choice) {
                case 1 -> viewAllDepartments();
                case 2 -> viewDepartmentById();
                case 3 -> createNewDepartment();
                case 4 -> renameDepartment();
                case 5 -> closeDepartment();
                case 6 -> viewNursesInDepartment();
                case 7 -> viewRoomsInDepartment();
                default -> {
                    System.out.println("Exiting Department Management Menu...");
                    return;
                }
            }
        }
    }

    private void viewAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        consoleViewFormatter.printHeader("All Departments");

        if (departments.isEmpty()) {
            consoleViewFormatter.printMessage("No departments found.");
            if (returnToMenu()) return;
        }

        for (Department department : departments) {
            consoleViewFormatter.showEntityDetails(department);
        }

        returnToMenu();
    }

    private void viewDepartmentById() {
        do {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Department ID: ");
                Department department = departmentService.getDepartment(id);
                consoleViewFormatter.showEntityDetails(department);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }

    private void createNewDepartment() {
        while (true) {
            try {
                String name = consoleInputReader.readStringPrompt("Enter Department Name: ");
                Department newDepartment = departmentService.createDepartment(name);
                consoleViewFormatter.printMessage("Department created with ID: " + newDepartment.getId());
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void renameDepartment() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Department ID: ");
                String newName = consoleInputReader.readStringPrompt("Enter New Department Name: ");
                departmentService.renameDepartment(id, newName);
                consoleViewFormatter.printMessage("Department renamed successfully with ID: " + id);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void closeDepartment() {
        while (true) {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Department ID: ");
                departmentService.closeDepartment(id);
                consoleViewFormatter.printMessage("Department closed successfully with ID: " + id);
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        }
    }

    private void viewNursesInDepartment() {
        do {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Department ID: ");
                List<Nurse> nurses = departmentService.getNursesInDepartment(id);
                consoleViewFormatter.printHeader("Nurses in Department " + id);
                for (Nurse nurse : nurses) {
                    consoleViewFormatter.showEntityDetails(nurse);
                }
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }

    private void viewRoomsInDepartment() {
        do {
            try {
                UUID id = consoleInputReader.readUUIDPrompt("Enter Department ID: ");
                List<Room> rooms = departmentService.getRoomsInDepartment(id);

                consoleViewFormatter.printHeader("Rooms in Department " + id);
                if (rooms.isEmpty()) {
                    consoleViewFormatter.printMessage("No rooms found in this department.");
                    if (returnToMenu()) return;
                }

                for (Room room : rooms) {
                    consoleViewFormatter.showEntityDetails(room);
                }
                if (returnToMenu()) return;
            } catch (Exception e) {
                consoleViewFormatter.printMessage(e.getMessage());
                if (shouldReturn()) return;
            }
        } while (true);
    }
}
