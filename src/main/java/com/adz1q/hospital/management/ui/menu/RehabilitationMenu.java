package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.service.RehabilitationService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

public class RehabilitationMenu extends Menu {
    private final RehabilitationService rehabilitationService;

    public RehabilitationMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            RehabilitationService rehabilitationService) {
        super(consoleInputReader, consoleViewFormatter);
        this.rehabilitationService = rehabilitationService;
    }

    @Override
    public void show() {}
}
