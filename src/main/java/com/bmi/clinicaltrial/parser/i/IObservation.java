package com.bmi.clinicaltrial.parser.i;

import com.bmi.clinicaltrial.data.fhir.Condition;
import com.bmi.clinicaltrial.data.fhir.Observation;
import com.bmi.clinicaltrial.exception.CustomException;

import java.util.List;
import java.util.Map;

public interface IObservation extends Parser
{
    Map<String, List<Observation>> codeParser(
            List<String> observation,
            List<String> notObservation,
            List<String> observationQuantity,
            List<String> notObservationQuantity,
            List<String> observationCodeAndDate
    ) throws CustomException;
}
