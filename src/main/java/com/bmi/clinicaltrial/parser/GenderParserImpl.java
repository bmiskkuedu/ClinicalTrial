package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.data.Gender;
import com.bmi.clinicaltrial.data.Modifier;
import com.bmi.clinicaltrial.exception.CustomAdvice;
import com.bmi.clinicaltrial.exception.CustomException;
import com.bmi.clinicaltrial.parser.i.IGender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GenderParserImpl implements IGender
{
    private Logger logger = LogManager.getLogger();

    /**
     * <prefixes, parameter> 로 사용
     * prefixes 에는 eq or not 사용
     * @param gender    해당 성별을 포함하는 조건
     * @param nGender   해당 성별을 포함하지 않는 조건
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> parser(String gender, String nGender) throws Exception
    {
        Map<String, String> genderMap = new HashMap<>();

        if(!gender.isEmpty())
        {
            genderMap.put(Modifier.eq.getModifier(), checkGender(gender.toLowerCase()));
        }

        if(!nGender.isEmpty())
        {
            genderMap.put(Modifier.not.getModifier(), checkGender(nGender.toLowerCase()));
        }

        return genderMap;
    }

    private String checkGender(String str) throws CustomException
    {
        Gender g = Gender.getType(str);
        if(g == Gender.error)
        {
            throw new CustomException(CustomAdvice.ErrorCode.INVALID_GENDER);
        }
        return g.getGender();
    }
}
