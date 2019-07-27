package com.bmi.clinicaltrial.data.fhir.base;

public class Coding
{
    public String system = "";
    public String code = "";
    public String display = "";

    @Override
    public String toString()
    {
        return "Coding{" +
                "system='" + system + '\'' +
                ", code='" + code + '\'' +
                ", display='" + display + '\'' +
                '}';
    }
}
