package com.bmi.clinicaltrial.parser.i;

import java.util.List;
import java.util.Map;

public interface IAllergy extends Parser
{
    Map<String, String> parseToMap(List<String> originalStr, List<String> secondList ) throws Exception;
}
