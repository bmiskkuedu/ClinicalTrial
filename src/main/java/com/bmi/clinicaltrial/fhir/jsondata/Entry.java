package com.bmi.clinicaltrial.fhir.jsondata;

import java.util.ArrayList;

public class Entry
{
    public Patient patient;
    public ArrayList<Observation> observations = new ArrayList<>();
    public ArrayList<MedicationRequest> medicationRequests = new ArrayList<>();
    public ArrayList<Condition> conditions = new ArrayList<>();
    public ArrayList<AllergyIntolerance> allergyIntolerances = new ArrayList<>();

    @Override
    public String toString()
    {
        return "Entry{" +
                "patient=" + patient +
                ", observations=" + observations +
                ", medicationRequests=" + medicationRequests +
                ", conditions=" + conditions +
                ", allergyIntolerances=" + allergyIntolerances +
                '}';
    }

    private StringBuilder sb;
    public String sizeToString()
    {
        if(sb == null)
        {
            sb = new StringBuilder();
            sb.append("id : ").append(patient.id)
                    .append(", observations : ").append(observations.size())
                    .append(", medicationRequests : ").append(medicationRequests.size())
                    .append(", conditions : ").append(conditions.size())
                    .append(", allergyIntolerances : ").append(allergyIntolerances.size());
        }

        return sb.toString();
    }
}
