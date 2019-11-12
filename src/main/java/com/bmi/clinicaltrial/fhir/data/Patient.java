package com.bmi.clinicaltrial.fhir.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patient
{
    public Map<String, String> birthdateMap;
    public Map<String, String> genderMap;
    public Map<String, List<Condition>> conditionMap;
    public Map<String, List<Observation>> observationMap;
    public Map<String, List<MedicationStatement>> medicationstatementMap;
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
