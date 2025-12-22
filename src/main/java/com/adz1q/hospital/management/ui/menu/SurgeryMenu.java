package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.service.SurgeryService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

public class SurgeryMenu extends Menu {
    private final SurgeryService surgeryService;

    public SurgeryMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            SurgeryService surgeryService) {
        super(consoleInputReader, consoleViewFormatter);
        this.surgeryService = surgeryService;
    }

    @Override
    public void show() {}
}
