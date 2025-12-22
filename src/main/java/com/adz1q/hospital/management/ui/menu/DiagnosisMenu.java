package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.service.DiagnosisService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

public class DiagnosisMenu extends Menu {
    private final DiagnosisService diagnosisService;

    public DiagnosisMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            DiagnosisService diagnosisService) {
        super(consoleInputReader, consoleViewFormatter);
        this.diagnosisService = diagnosisService;
    }

    @Override
    public void show() {}
}
