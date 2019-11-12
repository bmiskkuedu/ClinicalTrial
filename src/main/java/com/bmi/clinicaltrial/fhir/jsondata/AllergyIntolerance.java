package com.bmi.clinicaltrial.fhir.jsondata;

import com.bmi.clinicaltrial.fhir.data.Code;
import com.bmi.clinicaltrial.fhir.data.Issued;
import com.bmi.clinicaltrial.fhir.data.ValueQuantity;

public class AllergyIntolerance
{
    public Code code = new Code();

    @Override
    public String toString()
    {
        return "AllergyIntolerance{" +
                "code=" + code +
                '}';
    }
}
