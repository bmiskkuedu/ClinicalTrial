package com.bmi.clinicaltrial.data.fhir;

public class Observation extends Resource
{
     private ValueQuantity valueQuantity = new ValueQuantity();

    public ValueQuantity getValueQuantity()
    {
        return valueQuantity;
    }

    public void setValueQuantity(ValueQuantity valueQuantity)
    {
        this.valueQuantity = valueQuantity;
    }
}
