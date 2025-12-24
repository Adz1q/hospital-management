package com.adz1q.hospital.management.ui.menu;

import com.adz1q.hospital.management.ui.ConsoleInputReader;
import com.adz1q.hospital.management.ui.ConsoleViewFormatter;

public abstract class Menu implements Showable {
    protected final ConsoleInputReader consoleInputReader;
    protected final ConsoleViewFormatter consoleViewFormatter;

    public Menu(
            ConsoleInputReader consoleInputReader,
            ConsoleViewFormatter consoleViewFormatter) {
        this.consoleInputReader = consoleInputReader;
        this.consoleViewFormatter = consoleViewFormatter;
    }

    protected boolean shouldReturn() {
        consoleViewFormatter.printMessage("Press enter to return to the main menu or any key to retry.");
        String input = consoleInputReader.readString();
        return input.isBlank();
    }
}
