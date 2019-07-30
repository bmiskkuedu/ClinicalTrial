package com.bmi.clinicaltrial.fhir.data;

public class Issued
{
    public String issued;
    public Prefix prefix;   //  comparator 대용

    @Override
    public String toString()
    {
        return "Issued{" +
                "issued='" + issued + '\'' +
                ", prefix=" + prefix +
                '}';
    }
}
