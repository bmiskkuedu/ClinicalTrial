package com.bmi.clinicaltrial.service;

import com.bmi.clinicaltrial.fhir.data.Observation;

import java.util.Comparator;

public class PrefixValueComparator implements Comparator<Observation>
{
    @Override
    public int compare(Observation o1, Observation o2)
    {
        return o1.valueQuantity.prefix.compareTo(o2.valueQuantity.prefix);
    }
}
