package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.data.Modifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils
{
    public static Map<String, List<String>> getStringListMap(List<String> list1, List<String> list2)
    {
        Map<String, List<String>> map = new HashMap<>();

        if(list1 != null)
        {
            map.put(Modifier.eq.getModifier(), list1);
        }
        if(list2 != null)
        {
            map.put(Modifier.not.getModifier(), list2);
        }

        return map;
    }
}
