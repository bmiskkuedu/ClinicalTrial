package com.bmi.clinicaltrial.parser.i;

import java.util.List;
import java.util.Map;

public interface IAllergy extends Parser
{
    Map<String, List<String>> parser(List<String> allergyList, List<String> notAllergyList, String status ) throws Exception;

    Map<String, List<String>> parser(List<String> allergyList, List<String> notAllergyList );
}
