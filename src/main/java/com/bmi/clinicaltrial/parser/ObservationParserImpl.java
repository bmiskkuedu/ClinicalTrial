package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.data.Modifier;
import com.bmi.clinicaltrial.data.fhir.Condition;
import com.bmi.clinicaltrial.data.fhir.Observation;
import com.bmi.clinicaltrial.data.fhir.base.Code;
import com.bmi.clinicaltrial.exception.CustomException;
import com.bmi.clinicaltrial.parser.i.IObservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ObservationParserImpl implements IObservation
{
    private Logger logger = LogManager.getLogger();

    @Override
    public Map<String, List<Observation>> codeParser(List<String> observation, List<String> notObservation, List<String> observationQuantity, List<String> notObservationQuantity, List<String> observationCodeAndDate)
    {
        Map<String, List<Observation>> map = new HashMap<>();


        //  1

        //  2
        //  3
        //  4
        //  5
        return null;
    }

    private void checkCode(List<String> list, Map<String, List<Observation>> observationMap, Modifier status) throws CustomException
    {
        if(list != null && !list.isEmpty())
        {
           List<Observation> observationList = new ArrayList<>();
           Observation observation = new Observation();
           Code code = new Code();
        }
    }

    private void checkCodeAndQuantity()
    {

    }

    private void checkCodeAndDate()
    {

    }
}
