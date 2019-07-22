package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.parser.i.Parser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ParserGender implements Parser
{
    @Override
    public Map<String, String> parseToMap(List<String> originalStr)
    {
        return null;
    }
}
