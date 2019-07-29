package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.data.Modifier;
import com.bmi.clinicaltrial.data.Prefix;
import com.bmi.clinicaltrial.data.fhir.base.Coding;
import com.bmi.clinicaltrial.data.fhir.base.Issued;
import com.bmi.clinicaltrial.data.fhir.base.ValueQuantity;
import com.bmi.clinicaltrial.exception.CustomAdvice;
import com.bmi.clinicaltrial.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils
{
    public static Map<String, List<String>> getStringListMap(List<String> list1, List<String> list2)
    {
        Map<String, List<String>> map = new HashMap<>();

        if (list1 != null)
        {
            map.put(Modifier.eq.getModifier(), list1);
        }
        if (list2 != null)
        {
            map.put(Modifier.not.getModifier(), list2);
        }

        return map;
    }

    /**
     * 항상 snomed.com|456789 의 형태이어야 함
     */
    static Coding getCoding(String str, CustomAdvice.ErrorCode errorCode) throws CustomException
    {
        String[] splitStr = str.split("\\|");

        if (splitStr.length != 2)
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

    private static Logger logger = LogManager.getLogger();


    static ValueQuantity getValueQuantity(String str, CustomAdvice.ErrorCode errorCode) throws CustomException
    {
        String[] splitStr = str.split("\\|");

        for(int i = 0; i < splitStr.length; i++)
        {
            logger.info("splitStr : " + i + " : " + splitStr[i]);
        }

        if (splitStr.length != 3)
        {
            throw new CustomException(errorCode);
        }
        else
        {
            ValueQuantity valueQuantity = new ValueQuantity();

            Pattern valuePattern = Pattern.compile("^([a-z]*)([0-9]*)");
            Matcher valueMatcher = valuePattern.matcher(splitStr[0]);
            if(valueMatcher.find())
            {
                String prefix = valueMatcher.group(1).isEmpty() ? Prefix.eq.getPrefix()  :valueMatcher.group(1);
                valueQuantity.prefix = Prefix.valueOf(prefix);
                valueQuantity.value = valueMatcher.group(2);
            }

            valueQuantity.system = splitStr[1];
            valueQuantity.unit = splitStr[2];

            return valueQuantity;
        }
    }

    static Issued getIssued(String str, CustomAdvice.ErrorCode errorCode) throws CustomException
    {
        Issued issued = new Issued();

        Pattern issuedPattern = Pattern.compile("^([a-z]*)(\\d{4}-\\d{2}-\\d{2})$");
        Matcher issuedMatcher = issuedPattern.matcher(str);

        if(issuedMatcher.find())
        {
            String prefix = issuedMatcher.group(1).isEmpty() ? Prefix.eq.getPrefix()  :issuedMatcher.group(1);
            issued.prefix = Prefix.valueOf(prefix);
            issued.issued = issuedMatcher.group(2);
        }
        else
        {
            throw new CustomException(errorCode);
        }
        return issued;
    }

}
