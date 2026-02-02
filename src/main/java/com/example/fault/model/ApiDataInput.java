package com.example.fault.model;

import java.time.LocalDateTime;
import java.util.List;

public class ApiDataInput {
    private final String vin;
    private final String carSeries;
    private final String project;
    private final LocalDateTime eventTime;
    private final List<ApiSignalReading> signals;

    public ApiDataInput(String vin, String carSeries, String project, LocalDateTime eventTime,
                        List<ApiSignalReading> signals) {
        this.vin = vin;
        this.carSeries = carSeries;
        this.project = project;
        this.eventTime = eventTime;
        this.signals = signals;
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

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public List<ApiSignalReading> getSignals() {
        return signals;
    }
}
