package com.bmi.clinicaltrial.fhir.jsondata;

import java.util.ArrayList;

public class Entry
{
    public Patient patient;
    public ArrayList<Observation> observations = new ArrayList<>();
    public ArrayList<MedicationRequest> medicationRequests = new ArrayList<>();
    public ArrayList<AllergyIntolerance> allergyIntolerances = new ArrayList<>();

    @Override
    public String toString()
    {
        return "Entry{" +
                "patient=" + patient +
                ", observations=" + observations +
                ", medicationRequests=" + medicationRequests +
                ", allergyIntolerances=" + allergyIntolerances +
                '}';
    }
}
