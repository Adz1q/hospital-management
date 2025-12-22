package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.service.MedicationService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

public class MedicationMenu extends Menu {
    private final MedicationService medicationService;

    public MedicationMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            MedicationService medicationService) {
        super(consoleInputReader, consoleViewFormatter);
        this.medicationService = medicationService;
    }

    @Override
    public void show() {}
}
