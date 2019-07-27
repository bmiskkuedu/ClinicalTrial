package com.bmi.clinicaltrial.data.fhir;

import com.bmi.clinicaltrial.data.fhir.base.Code;
import com.bmi.clinicaltrial.data.fhir.base.Resource;
import com.bmi.clinicaltrial.data.fhir.base.ValueQuantity;

public class Observation extends Resource
{
    private Code code = new Code();

    private ValueQuantity valueQuantity = new ValueQuantity();

    public Code getCode()
    {
        return code;
    }

    public void setCode(Code code)
    {
        this.code = code;
    }

    public ValueQuantity getValueQuantity()
    {
        return valueQuantity;
    }

    public void setValueQuantity(ValueQuantity valueQuantity)
    {
        this.valueQuantity = valueQuantity;
    }

    @Override
    public String toString()
    {
        return "Observation{" +
                "code=" + code +
                ", valueQuantity=" + valueQuantity +
                '}';
    }


}
