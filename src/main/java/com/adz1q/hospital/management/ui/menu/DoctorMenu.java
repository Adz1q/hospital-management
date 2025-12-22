package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.service.DoctorService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

public class DoctorMenu extends Menu {
    private final DoctorService doctorService;

    public DoctorMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            DoctorService doctorService) {
        super(consoleInputReader, consoleViewFormatter);
        this.doctorService = doctorService;
    }

    @Override
    public void show() {}
}
