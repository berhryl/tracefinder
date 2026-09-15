package com.tracefinder;

import java.time.LocalDateTime;

public class LogEntry {
    private LocalDateTime timestamp;
    private String level;
    private String sourceIp;
    private String target;
    private String action;

    public LogEntry(LocalDateTime timestamp, String level, String sourceIp,
                    String target, String action) {
        this.timestamp = timestamp;
        this.level = level;
        this.sourceIp = sourceIp;
        this.target = target;
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getLevel() {
        return level;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public String getTarget() {
        return target;
    }

    public String getAction() {
        return action;
    }
}