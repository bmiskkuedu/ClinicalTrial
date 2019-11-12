package com.bmi.clinicaltrial.fhir.jsondata;

public class MedicationRequest
{
    public String authoredOn = "";
    public MedicationCodeableConcept medicationCodeableConcept = new MedicationCodeableConcept();

    @Override
    public String toString()
    {
        return "MedicationRequest{" +
                "authoredOn='" + authoredOn + '\'' +
                ", medicationCodeableConcept=" + medicationCodeableConcept +
                '}';
    }
}
