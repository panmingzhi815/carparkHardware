package com.dongluhitec.card.clientInUse;

public class Constants {
    // public static final boolean DEV_MODE = true;
    public static final boolean DEV_MODE = false;

    public boolean isDebuging() {
        // return true;
        return java.lang.management.ManagementFactory.getRuntimeMXBean()
                .getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
    }
}
