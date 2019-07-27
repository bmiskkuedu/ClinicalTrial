package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.data.Modifier;
import com.bmi.clinicaltrial.data.fhir.Condition;
import com.bmi.clinicaltrial.data.fhir.base.Code;
import com.bmi.clinicaltrial.exception.CustomAdvice;
import com.bmi.clinicaltrial.exception.CustomException;
import com.bmi.clinicaltrial.parser.i.ICondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConditionParserImpl implements ICondition
{
    private Logger logger = LogManager.getLogger();

    @Override
    public Map<String, List<Condition>> codeParser(List<String> inStr, List<String> notStr) throws Exception
    {
        Map<String, List<Condition>> conditionMap = new HashMap<>();

        checkStr(inStr, conditionMap, Modifier.eq);
        checkStr(notStr, conditionMap, Modifier.not);

        return conditionMap;
}

    private void checkStr(List<String> list, Map<String, List<Condition>> conditionMap, Modifier status) throws CustomException
    {
        if(list != null && !list.isEmpty())
        {
            List<Condition> conditionList = new ArrayList<>();
            Condition condition = new Condition();
            Code code = new Code();

            for(String s : list)
            {
                code.coding.add(Utils.getCoding(s, CustomAdvice.ErrorCode.INVALID_CONDITION));
            }

            condition.setCode(code);
            conditionList.add(condition);
            conditionMap.put(status.getModifier(), conditionList);
        }
    }
}
