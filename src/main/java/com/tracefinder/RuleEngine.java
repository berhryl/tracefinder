package com.tracefinder;

import java.util.ArrayList;
import java.util.List;

public class RuleEngine {

    public List<LogEntry> filterByLevel(List<LogEntry> entries, String level) {
        List<LogEntry> filtered = new ArrayList<>();
        for (LogEntry entry : entries) {
            if (entry.getLogLevel().equalsIgnoreCase(level)) {
                filtered.add(entry);
            }
        }
        return filtered;
    }

    public void generateReport(List<LogEntry> entries, List<MalformedLine> malformed) {
        System.out.println("================ LOG ANALYSIS REPORT ================");
        System.out.println("Total Valid Entries Processed: " + entries.size());
        System.out.println("Total Malformed Lines Flagged: " + malformed.size());
        
        List<LogEntry> errors = filterByLevel(entries, "ERROR");
        System.out.println("Critical Errors Found: " + errors.size());
        
        if (!errors.isEmpty()) {
            System.out.println("\n--- Critical Error Details ---");
            for (LogEntry error : errors) {
                System.out.println(error);
            }
        }
        
        if (!malformed.isEmpty()) {
            System.out.println("\n--- Malformed Lines Details ---");
            for (MalformedLine line : malformed) {
                System.out.println("Line " + line.getLineNumber() + ": " + line.getRawContent());
            }
        }
        System.out.println("====================================================");
    }
}