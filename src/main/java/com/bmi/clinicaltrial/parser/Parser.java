package com.bmi.clinicaltrial.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Parser
{
    public List<String> parse(String originalStr);

    public Map<String, String> parseToMap(List<String> originalStr);
}
