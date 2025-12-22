package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.service.PatientService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

public class PatientMenu extends Menu {
    private final PatientService patientService;

    public PatientMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            PatientService patientService) {
        super(consoleInputReader, consoleViewFormatter);
        this.patientService = patientService;
    }

    @Override
    public void show() {}
}
