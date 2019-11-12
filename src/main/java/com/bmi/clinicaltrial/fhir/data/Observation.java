package com.bmi.clinicaltrial.fhir.data;

import com.bmi.clinicaltrial.fhir.data.Code;
import com.bmi.clinicaltrial.fhir.data.Issued;
import com.bmi.clinicaltrial.fhir.data.Resource;
import com.bmi.clinicaltrial.fhir.data.ValueQuantity;

public class Observation extends Resource
{
    public Code code = new Code();

    public ValueQuantity valueQuantity = new ValueQuantity();

    public Issued issued;

    @Override
    public String toString()
    {
        return "Observation{" +
                code + " , " +
                valueQuantity + " , " +
                issued +
                '}';
    }
}
