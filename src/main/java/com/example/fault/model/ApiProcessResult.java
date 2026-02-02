package com.example.fault.model;

import java.util.Collections;
import java.util.List;

public class ApiProcessResult {
    private final boolean faultDetected;
    private final String message;
    private final List<DetectedFault> detectedFaults;
    private final List<ProcessResult> processingResults;

    private ApiProcessResult(boolean faultDetected, String message,
                             List<DetectedFault> detectedFaults,
                             List<ProcessResult> processingResults) {
        this.faultDetected = faultDetected;
        this.message = message;
        this.detectedFaults = detectedFaults;
        this.processingResults = processingResults;
    }

    public static ApiProcessResult noFault(String message) {
        return new ApiProcessResult(false, message, Collections.emptyList(), Collections.emptyList());
    }

    public static ApiProcessResult withFault(String message, List<DetectedFault> detectedFaults,
                                             List<ProcessResult> processingResults) {
        return new ApiProcessResult(true, message, detectedFaults, processingResults);
    }

    public boolean isFaultDetected() {
        return faultDetected;
    }

    public String getMessage() {
        return message;
    }

    public List<DetectedFault> getDetectedFaults() {
        return detectedFaults;
    }

    public List<ProcessResult> getProcessingResults() {
        return processingResults;
    }

    public String toPrettyString() {
        StringBuilder builder = new StringBuilder();
        builder.append("faultDetected=").append(faultDetected).append('\n');
        builder.append("message=").append(message).append('\n');
        builder.append("detectedFaults=").append(detectedFaults).append('\n');
        builder.append("processingResults=").append(processingResults).append('\n');
        return builder.toString();
    }
}
