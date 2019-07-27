package com.bmi.clinicaltrial.data.fhir;

import com.bmi.clinicaltrial.data.fhir.base.Code;

public class Condition
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
        return "Condition{" +
                "code=" + code +
                '}';
    }
}
