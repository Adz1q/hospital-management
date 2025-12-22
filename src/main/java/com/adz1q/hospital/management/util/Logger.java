package com.adz1q.hospital.management.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String PATH = "src/main/resources/logs/logs.txt";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(dtf);
        String formattedMessage = String.format("[%s] [%s] %s", timestamp, level, message);

        try (FileWriter fw = new FileWriter(PATH, true);
             PrintWriter pw = new PrintWriter(fw)) {
             pw.println(formattedMessage);
        } catch (IOException e) {
            System.err.println("Failed to write to log file.");
            e.printStackTrace();
        }
    }

    public static void info(String message) {
        log("INFO", message);
    }

    public static void warn(String message) {
        log("WARN", message);
    }

    public static void error(String message) {
        log("ERROR", message);
    }
}
