package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.exception.CustomAdvice;
import com.bmi.clinicaltrial.exception.CustomException;
import com.bmi.clinicaltrial.parser.i.IDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DateParserImpl implements IDate
{
    private Logger logger = LogManager.getLogger();
    private final static String REGEX_DATE = "^([a-z]*)(\\d{4}-\\d{2}-\\d{2})$";

    @Override
    public Map<String, String> parseToMap(List<String> originalStr) throws Exception
    {
        Map<String, String> resultMap = new HashMap<>();
        Pattern datePattern = Pattern.compile(REGEX_DATE);
        Matcher dateMatcher;

        if (originalStr != null)
        {
            for (String s : originalStr)
            {
                dateMatcher = datePattern.matcher(s);

                if (dateMatcher.find())
                {

                    /**
                     *  index
                     *  0   :   original string
                     *  1   :   첫번째 괄호 ([a-z]*)
                     *  2   :   date format (\\d{4}-\\d{2}-\\d{2})
                     *  ...
                     */
                    // TODO: 이상한 값 체크 ex) ?
                    String key = dateMatcher.group(1).isEmpty() ? "eq" : dateMatcher.group(1);  //  prefix가 없는 경우 eq 취급
                    resultMap.put(key, dateMatcher.group(2));
                }
                else
                {
                    logger.error("birthdate match not find");
                    throw new CustomException(CustomAdvice.ErrorCode.INVAILD_BIRTHDATE);
                }
            }
        }
        return resultMap;
    }
}
