package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.service.DepartmentService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

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
    public void show() {}
}
