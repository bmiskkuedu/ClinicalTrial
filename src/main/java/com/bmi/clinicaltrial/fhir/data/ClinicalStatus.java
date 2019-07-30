package com.bmi.clinicaltrial.fhir.data;

import java.util.Arrays;

public enum ClinicalStatus
{
    active("active"),
    INACTIVE("inactive"),
    RESOLVED("resolved"),
    NONE(""),
    ERROR("error");

    private String status;

    ClinicalStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public static ClinicalStatus getType(String status)
    {
        return Arrays.stream(ClinicalStatus.values())
                .filter(it -> it.name().equals(status))
                .findFirst()
                .orElse(ClinicalStatus.ERROR);
    }
}

