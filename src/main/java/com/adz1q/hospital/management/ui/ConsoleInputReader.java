package com.adz1q.hospital.management.ui;

import java.util.Scanner;
import java.util.UUID;

public class ConsoleInputReader {
    private final Scanner scanner;

    public ConsoleInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readString() {
        return scanner.nextLine();
    }

    public String readStringPrompt(String prompt) {
        System.out.print(prompt);
        return readString();
    }

    public int readInt() {
        while (true) {
            try {
                if (!scanner.hasNextLine()) {
                    System.out.println("No input available.");
                    return -1;
                }

                String input = scanner.nextLine().trim();

                if (input.isBlank()) {
                    return -1;
                }

                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number: ");
            }
        }
    }

    public int readIntPrompt(String prompt) {
        System.out.print(prompt);
        return readInt();
    }

    public UUID readUUID() {
        while (true) {
            try {
                if (!scanner.hasNextLine()) {
                    System.out.println("No input available.");
                    return null;
                }

                String input = scanner.nextLine().trim();

                if (input.isBlank()) {
                    return null;
                }

                return UUID.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID format. Please enter a valid UUID (or press enter to continue with null): ");
            }
        }
    }

    public UUID readUUIDPrompt(String prompt) {
        System.out.print(prompt);
        return readUUID();
    }
}
