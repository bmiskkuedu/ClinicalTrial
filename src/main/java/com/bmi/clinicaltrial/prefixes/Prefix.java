package com.bmi.clinicaltrial.prefixes;

public enum Prefix
{
    eq("eq"),
    ne("ne"),
    gt("gt"),
    lt("lt"),
    ge("qe"),
    le("le"),
    sa("sa"),
    eb("eb"),
    ap("ap");

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
