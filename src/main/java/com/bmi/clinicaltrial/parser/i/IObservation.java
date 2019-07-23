package com.bmi.clinicaltrial.parser.i;

import java.util.List;
import java.util.Map;

public interface IObservation extends Parser
{
    Map<String, List<String>> codeParser(List<String> inStr, List<String> notStr);

    void textParser();
}
