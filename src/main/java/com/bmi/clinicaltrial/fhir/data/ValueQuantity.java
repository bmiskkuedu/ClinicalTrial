package com.bmi.clinicaltrial.fhir.data;

public class ValueQuantity
{
    public String value; //  -?(0|[1-9][0-9]*)(\.[0-9]+)?([eE][+-]?[0-9]+)?
    public String unit;
    public String system;
    public String code;

    public Prefix prefix;   //  comparator 대용

    @Override
    public String toString()
    {
        return "ValueQuantity{" +
                "value='" + value + '\'' +
                ", unit='" + unit + '\'' +
                ", system='" + system + '\'' +
                ", code='" + code + '\'' +
                ", prefix=" + prefix +
                '}';
    }
}
