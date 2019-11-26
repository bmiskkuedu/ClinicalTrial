package com.bmi.clinicaltrial.service;

import com.bmi.clinicaltrial.fhir.data.Observation;

import java.util.Comparator;

public class PrefixIssuedComparator implements Comparator<Observation>
{
    @Override
    public int compare(Observation o1, Observation o2)
    {
        return o1.issued.prefix.compareTo(o2.issued.prefix);
    }
}
