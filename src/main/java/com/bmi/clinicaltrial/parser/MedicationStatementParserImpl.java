package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.exception.CustomAdvice;
import com.bmi.clinicaltrial.exception.CustomException;
import com.bmi.clinicaltrial.fhir.Condition;
import com.bmi.clinicaltrial.fhir.MedicationStatement;
import com.bmi.clinicaltrial.fhir.data.Code;
import com.bmi.clinicaltrial.fhir.data.Modifier;
import com.bmi.clinicaltrial.parser.i.IMedicationStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MedicationStatementParserImpl implements IMedicationStatement
{
    private Logger logger = LogManager.getLogger();

    @Override
    public Map<String, List<MedicationStatement>> parser(List<String> medicationStatementList, List<String> notMedicationStatement) throws CustomException
    {
        Map<String, List<MedicationStatement>> medicationMap = new HashMap<>();

        checkStr(medicationStatementList, medicationMap, Modifier.eq);
        checkStr(notMedicationStatement, medicationMap, Modifier.not);

        return medicationMap;
    }

    private void checkStr(List<String> list, Map<String, List<MedicationStatement>> medicationMap, Modifier status) throws CustomException
    {
        if(list != null && !list.isEmpty())
        {
            List<MedicationStatement> medicationStatementList = new ArrayList<>();
            MedicationStatement medicationStatement = new MedicationStatement();
            Code code = new Code();

            for(String s : list)
            {
                code.coding.add(Utils.getCoding(s, CustomAdvice.ErrorCode.INVALID_MEDICATIONSTATEMENT));
            }
            medicationStatement.code = code;
            medicationStatementList.add(medicationStatement);
            medicationMap.put(status.getModifier(), medicationStatementList);
        }
    }
}
