package com.tracefinder;

import java.util.ArrayList;
import java.util.List;

public class RuleEngine {

    /**
     * Filters log entries to find critical errors (ERROR level or containing critical failure keywords).
     * 
     * @param entries List of LogEntry objects to analyze
     * @return List of critical LogEntry objects
     */
    public List<LogEntry> findCriticalErrors(List<LogEntry> entries) {
        List<LogEntry> criticalErrors = new ArrayList<>();

        if (entries == null) {
            return criticalErrors;
        }

        for (LogEntry entry : entries) {
            // Flag entries with ERROR level or critical error messages
            if ("ERROR".equalsIgnoreCase(entry.getLogLevel()) || 
                "CRITICAL".equalsIgnoreCase(entry.getLogLevel()) ||
                (entry.getMessage() != null && entry.getMessage().toLowerCase().contains("failed"))) {
                criticalErrors.add(entry);
            }
        }

        return criticalErrors;
    }
}