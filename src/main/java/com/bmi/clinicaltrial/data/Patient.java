package com.bmi.clinicaltrial.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patient
{
    public Map<String, String> birthdateMap;
    public Map<String, String> genderMap;
    public Map<String, List<String>> conditionMap;
    public Map<String, List<String>> observationMap;
    public Map<String, String> medicationstatementMap;
    public Map<String, List<String>> allergyintoleranceMap;


    public Patient()
    {
        birthdateMap = new HashMap<>();
        genderMap = new HashMap<>();
        observationMap = new HashMap<>();
        medicationstatementMap = new HashMap<>();
        allergyintoleranceMap = new HashMap<>();
    }

    public Map<String, String> getBirthdateMap()
    {
        return birthdateMap;
    }

    public void setBirthdateMap(Map<String, String> birthdateMap)
    {
        this.birthdateMap = birthdateMap;
    }

    public Map<String, String> getGenderMap()
    {
        return genderMap;
    }

    public void setGenderMap(Map<String, String> genderMap)
    {
        this.genderMap = genderMap;
    }

    public Map<String, List<String>> getObservationMap()
    {
        return observationMap;
    }

    public void setObservationMap(Map<String, List<String>> observationMap)
    {
        this.observationMap = observationMap;
    }

    public Map<String, String> getMedicationstatementMap()
    {
        return medicationstatementMap;
    }

    public void setMedicationstatementMap(Map<String, String> medicationstatementMap)
    {
        this.medicationstatementMap = medicationstatementMap;
    }

    public Map<String, List<String>> getConditionMap()
    {
        return conditionMap;
    }

    public void setConditionMap(Map<String, List<String>> conditionMap)
    {
        this.conditionMap = conditionMap;
    }

    public Map<String, List<String>> getAllergyintoleranceMap()
    {
        return allergyintoleranceMap;
    }

    public void setAllergyintoleranceMap(Map<String, List<String>> allergyintoleranceMap)
    {
        this.allergyintoleranceMap = allergyintoleranceMap;
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
