package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.data.ClinicalStatus;
import com.bmi.clinicaltrial.exception.CustomAdvice;
import com.bmi.clinicaltrial.exception.CustomException;
import com.bmi.clinicaltrial.parser.i.IAllergy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AllergyParserImpl implements IAllergy
{
    @Override
    public Map<String, List<String>> parser(List<String> allergyList, List<String> notAllergyList)
    {
        return Utils.getStringListMap(allergyList, notAllergyList);
    }

    @Override
    public Map<String, List<String>> parser(List<String> allergyList, List<String> notAllergyList, String status) throws Exception
    {
        Map<String, List<String>> map = Utils.getStringListMap(allergyList, notAllergyList);

        /*
        // TODO: 2019-07-24 ClinicalStatus 어떻게 처리할 것인지에 대해 생각
        ClinicalStatus cs = ClinicalStatus.getType(status.toLowerCase());

        if(cs == ClinicalStatus.ERROR)
        {
            throw new CustomException(CustomAdvice.ErrorCode.INVALID_CLINICAL_STATUS);
        }
        else
        {
            map.put("clinical_status", cs.getStatus())
        }
        */
        return map;
    }
}
