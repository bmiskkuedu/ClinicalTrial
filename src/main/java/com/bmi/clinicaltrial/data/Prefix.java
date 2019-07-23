package com.bmi.clinicaltrial.data;

public enum Prefix
{
    //  FHIR Prefixes, 3.1.1.4.5
    eq("eq"),
    ne("ne"),
    gt("gt"),
    lt("lt"),
    ge("qe"),
    le("le"),
    sa("sa"),
    eb("eb"),
    ap("ap"),
    ;

    private String prefix;

    Prefix(String prefix)
    {
        this.prefix = prefix;
    }

    public String getPrefix()
    {
        return prefix;
    }

}
