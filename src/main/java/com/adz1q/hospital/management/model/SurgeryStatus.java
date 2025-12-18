package com.adz1q.hospital.management.model;

public enum SurgeryStatus {
    SCHEDULED ("Scheduled"),
    COMPLETED ("Completed"),
    CANCELLED ("Cancelled");

    private final String status;

    SurgeryStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
