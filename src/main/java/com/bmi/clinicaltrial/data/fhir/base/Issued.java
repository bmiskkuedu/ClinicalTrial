package com.bmi.clinicaltrial.data.fhir.base;

import com.bmi.clinicaltrial.data.Prefix;

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
