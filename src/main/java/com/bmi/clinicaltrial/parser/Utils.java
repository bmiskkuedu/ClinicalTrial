package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.data.Modifier;
import com.bmi.clinicaltrial.data.fhir.base.Coding;
import com.bmi.clinicaltrial.exception.CustomAdvice;
import com.bmi.clinicaltrial.exception.CustomException;

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

    /**
     항상 snomed.com|456789 의 형태이어야 함
     */
    static Coding getCoding(String str, CustomAdvice.ErrorCode errorCode) throws CustomException
    {
        String[] splitStr = str.split("\\|");

        if(splitStr.length != 2)
        {
            throw new CustomException(errorCode);
        }
        else
        {
            Coding coding = new Coding();
            coding.system = splitStr[0];
            coding.code = splitStr[1];

            return coding;
        }
    }
}
