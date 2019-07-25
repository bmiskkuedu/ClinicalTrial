package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.parser.i.ICondition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ConditionParserImpl implements ICondition
{
    /**
     *  SNOMED Clinical Terms code 사용
     */
    @Override
    public Map<String, List<String>> parser(List<String> condition, List<String> nCondition)
    {

        return Utils.getStringListMap(condition, nCondition);
    }
}
