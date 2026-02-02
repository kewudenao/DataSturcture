package com.example.fault.model;

public class HandlingConclusion {
    private final long id;
    private final String carSeries;
    private final String project;
    private final String faultType;
    private final String conclusion;

    public HandlingConclusion(long id, String carSeries, String project, String faultType, String conclusion) {
        this.id = id;
        this.carSeries = carSeries;
        this.project = project;
        this.faultType = faultType;
        this.conclusion = conclusion;
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

    public String getFaultType() {
        return faultType;
    }

    public String getConclusion() {
        return conclusion;
    }

    @Override
    public String toString() {
        return "HandlingConclusion{" +
            "id=" + id +
            ", carSeries='" + carSeries + '\'' +
            ", project='" + project + '\'' +
            ", faultType='" + faultType + '\'' +
            ", conclusion='" + conclusion + '\'' +
            '}';
    }
}
