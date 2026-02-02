package com.example.fault.model;

public class SignalFaultRule {
    private final long id;
    private final String carSeries;
    private final String project;
    private final String signalName;
    private final RuleOperator operator;
    private final double threshold;
    private final String faultType;
    private final String faultCode;

    public SignalFaultRule(long id, String carSeries, String project, String signalName,
                           RuleOperator operator, double threshold, String faultType, String faultCode) {
        this.id = id;
        this.carSeries = carSeries;
        this.project = project;
        this.signalName = signalName;
        this.operator = operator;
        this.threshold = threshold;
        this.faultType = faultType;
        this.faultCode = faultCode;
    }

    public long getId() {
        return id;
    }

    public String getCarSeries() {
        return carSeries;
    }

    public String getProject() {
        return project;
    }

    public String getSignalName() {
        return signalName;
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
        return "SignalFaultRule{" +
            "id=" + id +
            ", carSeries='" + carSeries + '\'' +
            ", project='" + project + '\'' +
            ", signalName='" + signalName + '\'' +
            ", operator=" + operator +
            ", threshold=" + threshold +
            ", faultType='" + faultType + '\'' +
            ", faultCode='" + faultCode + '\'' +
            '}';
    }
}
