package com.bmi.clinicaltrial.fhir.jsondata;

import com.bmi.clinicaltrial.fhir.data.Code;
import com.bmi.clinicaltrial.fhir.data.Issued;
import com.bmi.clinicaltrial.fhir.data.ValueQuantity;

public class Observation
{
    public Code code = new Code();
    public ValueQuantity valueQuantity = new ValueQuantity();
    public Issued issued = new Issued();


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
