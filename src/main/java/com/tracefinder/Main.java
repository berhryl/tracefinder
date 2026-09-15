package com.tracefinder;

public class Main {
    public static void main(String[] args) {
        // Step 1: Parse the log file
        LogParser parser = new LogParser();
        parser.parseFile("sample.log");

        // Step 2: Run the rule analysis engine
        RuleEngine ruleEngine = new RuleEngine();
        ruleEngine.generateReport(parser.getLogEntries(), parser.getMalformedLines());
    }
}