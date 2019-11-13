package com.bmi.clinicaltrial.service;

import com.bmi.clinicaltrial.fhir.data.Patient;

public interface ISearch
{
    int search(Patient patient);
}
