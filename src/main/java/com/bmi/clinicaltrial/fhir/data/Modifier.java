package com.bmi.clinicaltrial.fhir.data;

public enum Modifier
{
    //  Token Parameters, 3.1.1.4.10
    eq("eq"),
    not("not"),
    text("text"),
    above("above"),
    below("below"),
    in("in"),
    not_in("not-in"),
    of_type("of-type"),
    value("value"),
    date("date"),
    ;


    private String modifier;

    Modifier(String modifier)
    {
        this.modifier = modifier;
    }

    public String getModifier()
    {
        return modifier;
    }
}
