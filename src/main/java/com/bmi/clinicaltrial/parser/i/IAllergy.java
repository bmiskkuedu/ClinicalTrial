package com.bmi.clinicaltrial.parser.i;

import com.bmi.clinicaltrial.fhir.Allergy;

import java.util.List;
import java.util.Map;

public interface IAllergy extends Parser
{
    Map<String, List<Allergy>> parser(List<String> allergyList, List<String> notAllergyList) throws Exception;


}
