package com.bmi.clinicaltrial.parser.i;

import java.util.Map;

public interface IGender extends Parser
{
    Map<String, String> parser(String gender, String nGender) throws Exception;
}
