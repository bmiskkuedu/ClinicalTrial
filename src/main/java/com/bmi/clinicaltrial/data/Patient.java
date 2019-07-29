package com.bmi.clinicaltrial.data;

import com.bmi.clinicaltrial.data.fhir.Allergy;
import com.bmi.clinicaltrial.data.fhir.Condition;
import com.bmi.clinicaltrial.data.fhir.Observation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patient
{
    public Map<String, String> birthdateMap;
    public Map<String, String> genderMap;
    public Map<String, List<Condition>> conditionMap;
    public Map<String, List<Observation>> observationMap;
    public Map<String, String> medicationstatementMap;
    public Map<String, List<Allergy>> allergyintoleranceMap;


    public Patient()
    {
        birthdateMap = new HashMap<>();
        genderMap = new HashMap<>();
        observationMap = new HashMap<>();
        medicationstatementMap = new HashMap<>();
        allergyintoleranceMap = new HashMap<>();
    }

    @Override
    public String toString()
    {
        return "Patient{" +
                "birthdateMap=" + birthdateMap +
                ", genderMap=" + genderMap +
                ", conditionMap=" + conditionMap +
                ", observationMap=" + observationMap +
                ", medicationstatementMap=" + medicationstatementMap +
                ", allergyintoleranceMap=" + allergyintoleranceMap +
                '}';
    }
}
