package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.service.RoomService;
import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

public class RoomMenu extends Menu {
    private final RoomService roomService;

    public RoomMenu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter,
            RoomService roomService) {
        super(consoleInputReader, consoleViewFormatter);
        this.roomService = roomService;
    }

    @Override
    public void show() {}
}
