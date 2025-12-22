package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.service.NurseService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

public class NurseMenu extends Menu {
    private final NurseService nurseService;

    public NurseMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            NurseService nurseService) {
        super(consoleInputReader, consoleViewFormatter);
        this.nurseService = nurseService;
    }

    @Override
    public void show() {}
}
