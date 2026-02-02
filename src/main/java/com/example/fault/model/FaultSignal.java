package com.example.fault.model;

public class FaultSignal {
    private final long id;
    private final String faultType;
    private final String signalName;

    public FaultSignal(long id, String faultType, String signalName) {
        this.id = id;
        this.faultType = faultType;
        this.signalName = signalName;
    }

    public long getId() {
        return id;
    }

    public String getFaultType() {
        return faultType;
    }

    public String getSignalName() {
        return signalName;
    }

    @Override
    public String toString() {
        return "FaultSignal{" +
            "id=" + id +
            ", faultType='" + faultType + '\'' +
            ", signalName='" + signalName + '\'' +
            '}';
    }
}
