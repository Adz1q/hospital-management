package com.adz1q.hospital.management.model;

public enum AppointmentStatus {
    SCHEDULED   ("Scheduled"),
    COMPLETED   ("Completed"),
    CANCELLED    ("Cancelled");

    private final String status;

    AppointmentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
