package com.bmi.clinicaltrial.fhir.jsondata;

import com.bmi.clinicaltrial.fhir.data.Coding;

import java.util.ArrayList;
import java.util.List;

public class MedicationCodeableConcept
{
    public List<Coding> coding = new ArrayList<Coding>();

    @Override
    public String toString()
    {
        return "MedicationCodeableConcept{" +
                "coding=" + coding +
                '}';
    }
}
