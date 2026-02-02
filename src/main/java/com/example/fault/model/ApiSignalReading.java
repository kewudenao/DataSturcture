package com.example.fault.model;

public class ApiSignalReading {
    private final String signalName;
    private final double value;

    public ApiSignalReading(String signalName, double value) {
        this.signalName = signalName;
        this.value = value;
    }

    public String getSignalName() {
        return signalName;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ApiSignalReading{" +
            "signalName='" + signalName + '\'' +
            ", value=" + value +
            '}';
    }
}
