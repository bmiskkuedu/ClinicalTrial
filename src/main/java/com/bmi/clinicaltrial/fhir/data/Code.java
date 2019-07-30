package com.bmi.clinicaltrial.fhir.data;

import java.util.ArrayList;
import java.util.List;

public class Code
{
    public List<Coding> coding = new ArrayList<Coding>();

    @Override
    public String toString()
    {
        return "Code{" +
                coding +
                "}";
    }
}
