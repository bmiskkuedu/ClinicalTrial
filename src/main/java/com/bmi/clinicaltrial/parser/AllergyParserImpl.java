package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.fhir.data.Modifier;
import com.bmi.clinicaltrial.fhir.data.Allergy;
import com.bmi.clinicaltrial.fhir.data.Code;
import com.bmi.clinicaltrial.exception.CustomException;
import com.bmi.clinicaltrial.parser.i.IAllergy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bmi.clinicaltrial.exception.CustomAdvice.ErrorCode.INVALID_ALLERGY;

@Service
public class AllergyParserImpl implements IAllergy
{
    private Logger logger = LogManager.getLogger();

    @Override
    public Map<String, List<Allergy>> parser(List<String> allergyList, List<String> notAllergyList) throws Exception
    {
        Map<String, List<Allergy>> allergyMap = new HashMap<>();

        checkStr(allergyList, allergyMap, Modifier.eq);
        checkStr(notAllergyList, allergyMap, Modifier.not);

        return allergyMap;

    }

    private void checkStr(List<String> list, Map<String, List<Allergy>> conditionMap, Modifier status) throws CustomException
    {
        if (list != null && !list.isEmpty())
        {
            List<Allergy> allergyList = new ArrayList<>();
            Allergy allergy = new Allergy();
            Code code = new Code();

            for (String s : list)
            {
                code.coding.add(Utils.getCoding(s, INVALID_ALLERGY));
            }

            allergy.setCode(code);
            allergyList.add(allergy);
            conditionMap.put(status.getModifier(), allergyList);
        }
    }
}
