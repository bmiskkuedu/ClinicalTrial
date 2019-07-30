package com.bmi.clinicaltrial.fhir;

import com.bmi.clinicaltrial.fhir.data.Code;

public class Allergy
{
    private Code code;

    public Code getCode()
    {
        return code;
    }

    public void setCode(Code code)
    {
        this.code = code;
    }

    @Override
    public String toString()
    {
        return "Allergy{" +
                code +
                '}';
    }
}
