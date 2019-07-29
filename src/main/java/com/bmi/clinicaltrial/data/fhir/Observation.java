package com.bmi.clinicaltrial.data.fhir;

import com.bmi.clinicaltrial.data.fhir.base.Code;
import com.bmi.clinicaltrial.data.fhir.base.Issued;
import com.bmi.clinicaltrial.data.fhir.base.Resource;
import com.bmi.clinicaltrial.data.fhir.base.ValueQuantity;

public class Observation extends Resource
{
    public Code code = new Code();

    public ValueQuantity valueQuantity = new ValueQuantity();

    public Issued issued;

    @Override
    public String toString()
    {
        return "Observation{" +
                "code=" + code +
                ", valueQuantity=" + valueQuantity +
                ", issued=" + issued +
                '}';
    }
}
