package com.tracefinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogParser {
    private final List<LogEntry> logEntries = new ArrayList<>();
    private final List<MalformedLine> malformedLines = new ArrayList<>();

    public void parseFile(String filePath) {
        // Clear previous results before parsing a new file
        logEntries.clear();
        malformedLines.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip completely empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Split line assuming standard log format: "TIMESTAMP LEVEL MESSAGE" (e.g. "2026-09-15 INFO System started")
                String[] parts = line.split(" ", 3);

                // Validation check: line must have at least 3 parts (Timestamp, Level, Message)
                if (parts.length < 3 || isMalformedLevel(parts[1])) {
                    malformedLines.add(new MalformedLine(lineNumber, line));
                } else {
                    String timestamp = parts[0];
                    String logLevel = parts[1];
                    String message = parts[2];

                    logEntries.add(new LogEntry(timestamp, logLevel, message));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }
    }

    // Helper method to validate standard log levels
    private boolean isMalformedLevel(String level) {
        return !level.matches("INFO|WARN|ERROR|DEBUG|TRACE");
    }

    public List<LogEntry> getLogEntries() {
        return logEntries;
    }

    public List<MalformedLine> getMalformedLines() {
        return malformedLines;
    }
}