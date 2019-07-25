package com.bmi.clinicaltrial.parser.i;

import java.util.List;
import java.util.Map;

public interface ICondition extends Parser
{
    Map<String, List<String>> parser(List<String> condition, List<String> nCondition);
}
