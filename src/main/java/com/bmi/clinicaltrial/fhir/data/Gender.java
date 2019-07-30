package com.bmi.clinicaltrial.fhir.data;

import java.util.Arrays;

public enum Gender
{
    male("male"),
    female("female"),
    other("other"),
    unknown("unknown"),
    error("error"),
    ;

    private String gender;

    Gender(String gender)
    {
        this.gender = gender;
    }

    public String getGender()
    {
        return gender;
    }


    public static Gender getType(String gender)
    {
        return Arrays.stream(Gender.values()).filter(it -> it.name().equals(gender)).findFirst().orElse(Gender.error);
    }

}
