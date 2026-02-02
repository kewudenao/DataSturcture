package com.example.fault.model;

public class DetectedFault {
    private final long ruleId;
    private final String signalName;
    private final double signalValue;
    private final RuleOperator operator;
    private final double threshold;
    private final String faultType;
    private final String faultCode;

    public DetectedFault(long ruleId, String signalName, double signalValue, RuleOperator operator,
                         double threshold, String faultType, String faultCode) {
        this.ruleId = ruleId;
        this.signalName = signalName;
        this.signalValue = signalValue;
        this.operator = operator;
        this.threshold = threshold;
        this.faultType = faultType;
        this.faultCode = faultCode;
    }

    public long getRuleId() {
        return ruleId;
    }

    public String getSignalName() {
        return signalName;
    }

    public double getSignalValue() {
        return signalValue;
    }

    public RuleOperator getOperator() {
        return operator;
    }

    public double getThreshold() {
        return threshold;
    }

    public String getFaultType() {
        return faultType;
    }

    public String getFaultCode() {
        return faultCode;
    }

    @Override
    public String toString() {
        return "DetectedFault{" +
            "ruleId=" + ruleId +
            ", signalName='" + signalName + '\'' +
            ", signalValue=" + signalValue +
            ", operator=" + operator +
            ", threshold=" + threshold +
            ", faultType='" + faultType + '\'' +
            ", faultCode='" + faultCode + '\'' +
            '}';
    }
}
