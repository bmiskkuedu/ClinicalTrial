package com.bmi.clinicaltrial.fhir.jsondata;

import com.bmi.clinicaltrial.fhir.data.Gender;

public class Patient
{
    public String id = "";
    public String gender = "";
    public String birthDate = "";

    @Override
    public String toString()
    {
        return "Patient{" +
                "id='" + id + '\'' +
                ", gender=" + gender +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }
}
