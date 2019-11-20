package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.fhir.data.Modifier;
import com.bmi.clinicaltrial.fhir.data.Observation;
import com.bmi.clinicaltrial.fhir.data.Code;
import com.bmi.clinicaltrial.exception.CustomAdvice;
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
    private final static String OBSERVATION_REGEX = "\\$";

    @Override
    public Map<String, List<Observation>> parser(List<String> observation, List<String> notObservation,
                                                 List<String> observationQuantity, List<String> observationCodeAndDate) throws CustomException
    {
        Map<String, List<Observation>> observationMap = new HashMap<>();

        //  1 - _has:observation:patient:code
        //  특정 코드만 검색 한 경우
        checkCode(observation, observationMap, Modifier.eq);
        //  2 - _has:observation:patient:code:not
        //  특정 코드의 제외를 검색한 경우
        checkCode(notObservation, observationMap, Modifier.not);
        //  3 - _has:observation:patient:code-value-quantity
        //  특정 코드와 검사 수치를 통해 검색한 경우
        checkCodeAndQuantity(observationQuantity, observationMap);
        //  4 - _has:observation:patient:code-value-date
        //  특정 검사와 그 날짜를 검색한 경우
        checkCodeAndDate(observationCodeAndDate, observationMap);

        return observationMap;
    }

    private void checkCodeAndQuantity(List<String> list, Map<String, List<Observation>> observationMap ) throws CustomException
    {
        if(list != null && !list.isEmpty())
        {
            List<Observation> observationList = new ArrayList<>();

            for(String s : list)
            {
                String[] temp = s.split(OBSERVATION_REGEX);

                Observation observation = new Observation();
                observation.code.coding.add(Utils.getCoding(temp[0], CustomAdvice.ErrorCode.INVALID_OBSERVATION));
                observation.valueQuantity = Utils.getValueQuantity(temp[1], CustomAdvice.ErrorCode.INVALID_OBSERVATION);
                observationList.add(observation);
            }
            observationMap.put(Modifier.eq.getModifier(), observationList);
        }
    }

    private void checkCodeAndDate(List<String> list, Map<String, List<Observation>> observationMap ) throws CustomException
    {
        if(list != null && !list.isEmpty())
        {
            List<Observation> observationList = new ArrayList<>();

            for(String s : list)
            {
                String[] str = s.split(OBSERVATION_REGEX);

                Observation observation = new Observation();
                observation.code.coding.add(Utils.getCoding(str[0], CustomAdvice.ErrorCode.INVALID_OBSERVATION));
                observation.issued = Utils.getIssued(str[1], CustomAdvice.ErrorCode.INVALID_CONDITION);

                observationList.add(observation);
            }
            observationMap.put(Modifier.eq.getModifier(), observationList);
        }
    }

    private void checkCode(List<String> list, Map<String, List<Observation>> observationMap, Modifier status) throws CustomException
    {
        if(list != null && !list.isEmpty())
        {
           List<Observation> observationList = new ArrayList<>();
           Observation observation = new Observation();
           Code code = new Code();

           for(String s : list)
           {
               code.coding.add(Utils.getCoding(s, CustomAdvice.ErrorCode.INVALID_OBSERVATION));
           }
           observation.code = code;
           observationList.add(observation);
           observationMap.put(status.getModifier(), observationList);
        }
    }
}
