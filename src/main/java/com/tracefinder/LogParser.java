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
        logEntries.clear();
        malformedLines.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                // Format: YYYY-MM-DD HH:MM:SS [LEVEL] Message
                if (line.matches("^\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}\\s+\\[[A-Z]+\\].*")) {
                    int firstBracket = line.indexOf('[');
                    int lastBracket = line.indexOf(']');

                    String timestamp = line.substring(0, firstBracket).trim();
                    String logLevel = line.substring(firstBracket + 1, lastBracket).trim();
                    String message = line.substring(lastBracket + 1).trim();

                    logEntries.add(new LogEntry(timestamp, logLevel, message));
                } else {
                    malformedLines.add(new MalformedLine(lineNumber, line));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }
    }

    public List<LogEntry> getLogEntries() {
        return logEntries;
    }

    public List<MalformedLine> getMalformedLines() {
        return malformedLines;
    }
}