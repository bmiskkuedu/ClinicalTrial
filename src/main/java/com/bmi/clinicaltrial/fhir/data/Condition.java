package com.bmi.clinicaltrial.fhir.data;

import com.bmi.clinicaltrial.fhir.data.Code;

public class Condition
{
    public Code code = new Code();

    @Override
    public String toString()
    {
        return "Condition{" +
                code +
                '}';
    }
}
