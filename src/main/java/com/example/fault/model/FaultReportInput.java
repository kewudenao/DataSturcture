package com.example.fault.model;

import java.time.LocalDateTime;

public class FaultReportInput {
    private final String vin;
    private final String carSeries;
    private final String project;
    private final String faultType;
    private final String faultCode;
    private final LocalDateTime eventTime;

    public FaultReportInput(String vin, String carSeries, String project, String faultType,
                            String faultCode, LocalDateTime eventTime) {
        this.vin = vin;
        this.carSeries = carSeries;
        this.project = project;
        this.faultType = faultType;
        this.faultCode = faultCode;
        this.eventTime = eventTime;
    }

    public String getVin() {
        return vin;
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

    public String getFaultCode() {
        return faultCode;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }
}
