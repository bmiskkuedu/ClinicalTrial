package com.bmi.clinicaltrial.parser.i;

import com.bmi.clinicaltrial.exception.CustomException;
import com.bmi.clinicaltrial.fhir.data.MedicationStatement;

import java.util.List;
import java.util.Map;

public interface IMedicationStatement extends Parser
{
    Map<String, List<MedicationStatement>> parser(List<String> medicationStatement, List<String> notMedicationStatement ) throws CustomException;
}
