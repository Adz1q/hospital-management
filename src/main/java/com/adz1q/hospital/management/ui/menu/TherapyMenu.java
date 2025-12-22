package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.service.TherapyService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

public class TherapyMenu extends Menu {
    private final TherapyService therapyService;

    public TherapyMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            TherapyService therapyService) {
        super(consoleInputReader, consoleViewFormatter);
        this.therapyService = therapyService;
    }

    @Override
    public void show() {}
}
