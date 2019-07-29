package com.bmi.clinicaltrial.parser.i;

import com.bmi.clinicaltrial.data.fhir.Condition;

import java.util.List;
import java.util.Map;

public interface ICondition extends Parser
{
    Map<String, List<Condition>> parser(List<String> inStr, List<String> notStr) throws Exception;


}
