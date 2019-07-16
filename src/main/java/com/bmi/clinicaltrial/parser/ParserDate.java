package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.prefixes.Prefix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ParserDate implements Parser
{
    private final String tag = this.getClass().getSimpleName();

    private Logger logger = LogManager.getLogger();
    private final static String REGEX_DATE = "^([a-z]*)(\\d{4}-\\d{2}-\\d{2})$";

    @Override
    public Map<String, String> parseToMap(List<String> originalStr)
    {
        Map<String, String> resultMap = new HashMap<>();
        Pattern datePattern = Pattern.compile(REGEX_DATE);
        Matcher dateMatcher;

        for(String s : originalStr)
        {
            dateMatcher = datePattern.matcher(s);

            if(dateMatcher.find())
            {

                /**
                 *  index
                 *  0   :   original string
                 *  1   :   첫번째 괄호 ([a-z]*)
                 *  2   :   date format (\\d{4}-\\d{2}-\\d{2})
                 *  ...
                 */
                // TODO: 이상한 값 체크
                //  ex) 1800-13-34, Prefix에 없는 문자
                for(int i = 1; i <= dateMatcher.groupCount(); i++)
                {
                    //logger.info(i + " : " + dateMatcher.group(i));
                }
                resultMap.put(dateMatcher.group(1), dateMatcher.group(2));
            }
            else
            {
                logger.error("birthdate match not find");
            }
        }
        return resultMap;
    }
}
