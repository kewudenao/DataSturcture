package com.example.fault.model;

import java.util.Collections;
import java.util.List;

public class ProcessResult {
    private final boolean duplicate;
    private final String message;
    private final List<HandlingConclusion> handlingConclusions;
    private final List<FaultSignal> faultSignals;
    private final List<CollisionRepairSignal> collisionRepairSignals;

    private ProcessResult(boolean duplicate, String message,
                          List<HandlingConclusion> handlingConclusions,
                          List<FaultSignal> faultSignals,
                          List<CollisionRepairSignal> collisionRepairSignals) {
        this.duplicate = duplicate;
        this.message = message;
        this.handlingConclusions = handlingConclusions;
        this.faultSignals = faultSignals;
        this.collisionRepairSignals = collisionRepairSignals;
    }

    public static ProcessResult duplicate(String message) {
        return new ProcessResult(true, message,
            Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    public static ProcessResult success(String message, List<HandlingConclusion> handlingConclusions,
                                        List<FaultSignal> faultSignals,
                                        List<CollisionRepairSignal> collisionRepairSignals) {
        return new ProcessResult(false, message, handlingConclusions, faultSignals, collisionRepairSignals);
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public String getMessage() {
        return message;
    }

    public List<HandlingConclusion> getHandlingConclusions() {
        return handlingConclusions;
    }

    public List<FaultSignal> getFaultSignals() {
        return faultSignals;
    }

    public List<CollisionRepairSignal> getCollisionRepairSignals() {
        return collisionRepairSignals;
    }

    public String toPrettyString() {
        StringBuilder builder = new StringBuilder();
        builder.append("duplicate=").append(duplicate).append('\n');
        builder.append("message=").append(message).append('\n');
        if (!duplicate) {
            builder.append("handlingConclusions=").append(handlingConclusions).append('\n');
            builder.append("faultSignals=").append(faultSignals).append('\n');
            builder.append("collisionRepairSignals=").append(collisionRepairSignals).append('\n');
        }
        return builder.toString();
    }
}
