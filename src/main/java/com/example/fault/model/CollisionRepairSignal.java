package com.example.fault.model;

public class CollisionRepairSignal {
    private final long id;
    private final String signalType;
    private final String signalName;

    public CollisionRepairSignal(long id, String signalType, String signalName) {
        this.id = id;
        this.signalType = signalType;
        this.signalName = signalName;
    }

    public long getId() {
        return id;
    }

    public String getSignalType() {
        return signalType;
    }

    public String getSignalName() {
        return signalName;
    }

    @Override
    public String toString() {
        return "CollisionRepairSignal{" +
            "id=" + id +
            ", signalType='" + signalType + '\'' +
            ", signalName='" + signalName + '\'' +
            '}';
    }
}
