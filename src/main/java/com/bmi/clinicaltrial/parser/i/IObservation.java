package com.bmi.clinicaltrial.parser.i;

import com.bmi.clinicaltrial.fhir.Observation;
import com.bmi.clinicaltrial.exception.CustomException;

import java.util.List;
import java.util.Map;

public interface IObservation extends Parser
{
    Map<String, List<Observation>> parser(
            List<String> observation,
            List<String> notObservation,
            List<String> observationQuantity,
            List<String> observationCodeAndDate
    ) throws CustomException;
}
