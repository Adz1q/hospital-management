package com.adz1q.hospital.management.application;

import com.adz1q.hospital.management.config.ApplicationContext;

public class HospitalManagementApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext();
        context.getConsoleMenu().run();
    }
}
