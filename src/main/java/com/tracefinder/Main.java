package com.tracefinder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Default log file and export report file names
        String filePath = "sample.log";
        String outputPath = "report.txt";

        // Read optional custom log file argument
        if (args.length > 0) {
            filePath = args[0];
        }
        // Read optional custom report destination file argument
        if (args.length > 1) {
            outputPath = args[1];
        }

        System.out.println("Analyzing log file: " + filePath + "\n");

        LogParser parser = new LogParser();
        parser.parseFile(filePath);

        List<LogEntry> entries = parser.getLogEntries();
        List<MalformedLine> malformedLines = parser.getMalformedLines();

        RuleEngine ruleEngine = new RuleEngine();
        List<LogEntry> criticalErrors = ruleEngine.findCriticalErrors(entries);

        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("============ LOG ANALYSIS REPORT ============\n");
        reportBuilder.append("Total Valid Entries Processed: ").append(entries.size()).append("\n");
        reportBuilder.append("Total Malformed Lines Flagged: ").append(malformedLines.size()).append("\n");
        reportBuilder.append("Critical Errors Found: ").append(criticalErrors.size()).append("\n\n");

        if (!malformedLines.isEmpty()) {
            reportBuilder.append("--- Malformed Lines Details ---\n");
            for (MalformedLine line : malformedLines) {
                reportBuilder.append("Line ").append(line.getLineNumber()).append(": ")
                             .append(line.getRawContent()).append("\n");
            }
            reportBuilder.append("\n");
        }

        if (!criticalErrors.isEmpty()) {
            reportBuilder.append("--- Critical Error Details ---\n");
            for (LogEntry entry : criticalErrors) {
                reportBuilder.append(entry).append("\n");
            }
            reportBuilder.append("=============================================\n");
        }

        String reportContent = reportBuilder.toString();

        // Output report to console
        System.out.print(reportContent);

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.print(reportContent);
            System.out.println("\nReport successfully saved to: " + outputPath);
        } catch (IOException e) {
            System.err.println("Failed to export report: " + e.getMessage());
        }
    }
}