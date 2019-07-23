package com.bmi.clinicaltrial.parser.i;

import java.util.List;
import java.util.Map;

public interface IDate extends Parser
{
    public Map<String, String> parseToMap(List<String> originalStr) throws Exception;
}
