package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.data.Modifier;
import com.bmi.clinicaltrial.parser.i.IObservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ObservationParserImpl implements IObservation
{
    private Logger logger = LogManager.getLogger();

    @Override
    public Map<String, List<String>> codeParser(List<String> inStr, List<String> notStr)
    {
        Map<String, List<String>> observationMap = new HashMap<>();

        if(inStr != null)
        {
            observationMap.put(Modifier.eq.getModifier(), inStr);
        }
        if(notStr != null)
        {
            observationMap.put(Modifier.not.getModifier(), notStr);
        }

        return observationMap;
    }


    @Override
    public void textParser()
    {

    }
}
