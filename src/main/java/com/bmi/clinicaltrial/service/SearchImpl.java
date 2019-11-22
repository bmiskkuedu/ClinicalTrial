package com.bmi.clinicaltrial.service;

import com.bmi.clinicaltrial.fhir.data.*;
import com.bmi.clinicaltrial.fhir.jsondata.AllergyIntolerance;
import com.bmi.clinicaltrial.fhir.jsondata.Entry;
import com.bmi.clinicaltrial.fhir.jsondata.MedicationRequest;
import com.bmi.clinicaltrial.utils.LoadJson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.awt.event.WindowFocusListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SearchImpl implements ISearch
{
    private Logger logger = LogManager.getLogger();
    private List<Entry> allSampleData;

    public SearchImpl()
    {
        LoadJson loadJson = new LoadJson();
        allSampleData = loadJson.getAllSampleData();
        logger.info("allSampleData : " + allSampleData.size());
    }

    @Override
    public int search(Patient patient)
    {
        /**
         *  1. gender check
         *  2. birthdate check
         *  3. condition check
         *  4. observation check
         *  5. medicationstatement(medicationrequest) check
         *  6. allergyintolerance check
         */
        List<Entry> result = new ArrayList<>();

        result = checkGender(patient);  //  1. gender check
        result = checkBirthdate(result, patient);   //  2. birthdate check
        result = checkCondition(result, patient);   //  3. condition check
        result = checkObservation(result, patient); //  4. observation check
        result = checkMedicationRequest(result, patient);   //  5. medicationrequest check
        result = checkAllergyIntolerance(result, patient);  //  6. allergyintolerance check
        logger.info("result : " + result.size());

        return result.size();
    }

    /**
     *  성별 검사
     * @param patient   검사하고자 하는 조건
     * @return
     */
    private List<Entry> checkGender(Patient patient)
    {
        List<Entry> tempResult = new ArrayList<>();

        if(patient.genderMap.keySet().size() > 0)
        {
            Iterator genderIterator = patient.genderMap.keySet().iterator();
            while (genderIterator.hasNext())
            {
                String key = (String) genderIterator.next();
                if (Modifier.eq.getModifier().equals(key))
                {
                    tempResult = allSampleData.stream().filter(it -> it.patient.gender.equals(patient.genderMap.get(key))).collect(Collectors.toList());
                }
                else
                {
                    tempResult = allSampleData.stream().filter(it -> !(it.patient.gender.equals(patient.genderMap.get(key)))).collect(Collectors.toList());
                }
            }
            return tempResult;
        }
        else
        {
            return allSampleData;
        }
    }

    /**
     *  생년월일을 확인 함
     * @param beforeResult  환자 데이터들
     * @param patient   검사하고자 하는 조건
     * @return
     */
    private List<Entry> checkBirthdate(List<Entry> beforeResult, Patient patient)
    {
        try
        {
            if(patient.birthdateMap.keySet().size() > 0)
            {
                List<Entry> tempResult = new ArrayList<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                for (int i = 0; i < beforeResult.size(); i++)
                {
                    com.bmi.clinicaltrial.fhir.jsondata.Patient p = beforeResult.get(i).patient;
                    Date pDate = sdf.parse(p.birthDate);

                    Iterator birthdateIterator = patient.birthdateMap.keySet().iterator();

                    while(birthdateIterator.hasNext())
                    {
                        String key = (String) birthdateIterator.next();
                        Date cDate = sdf.parse(patient.birthdateMap.get(key));

                        if((key.equals("eq") && (sdf.format(pDate).equals(sdf.format(cDate))))
                        || (key.equals("le") && (pDate.before(cDate)))
                        || (key.equals("ge") && (pDate.after(cDate))))
                        {
                            if(!tempResult.contains(beforeResult.get(i)))
                            {
                                tempResult.add(beforeResult.get(i));
                            }
                        }
                        else
                        {
                            tempResult.remove(beforeResult.get(i));
                        }
                    }
                }
                return tempResult;
            }
            else
            {
                return beforeResult;
            }
        }
        catch (Exception e)
        {
            logger.error("date parser error : " + e);
            return beforeResult;
        }
    }

    /**
     *  Conditon 검사
     * @param beforeResult  이전의 성별, 생일 검사를 통과한 환자 정보
     * @param patient   검사하고자 하는 조건
     * @return
     */
    private List<Entry> checkCondition(List<Entry> beforeResult, Patient patient)
    {
        if(patient.conditionMap.keySet().size() > 0)
        {
            List<Entry> tempResult = new ArrayList<>();
            Iterator iterator = patient.conditionMap.keySet().iterator();

            List<Coding> eqList = new ArrayList<>();
            List<Coding> notList = new ArrayList<>();

            while(iterator.hasNext())
            {
                //  검색 조건을 확인
                String key = (String)iterator.next();
                List<Condition> cCondition = (List<Condition>)patient.conditionMap.get(key);
                if(key.equals(Modifier.eq.getModifier()))
                {
                    cCondition.forEach(cit-> eqList.addAll(cit.code.coding));
                }
                if(key.equals(Modifier.not.getModifier()))
                {
                    cCondition.forEach(cit-> notList.addAll(cit.code.coding));
                }
            }

            for (Entry e : beforeResult)
            {   //  개인의 condition list를 돌면서 coding 데이터만 수집
                List<Coding> eCoding = new ArrayList<Coding>();
                e.conditions.forEach(it -> eCoding.addAll(it.code.coding));

                extractCondition(tempResult, eqList, notList, e, eCoding);
            }
            return tempResult;
        }

        return beforeResult;
    }

    private List<Entry> checkObservation(List<Entry> beforeResult, Patient patient)
    {
        if(patient.observationMap.keySet().size() > 0)
        {
            List<Entry> tempResult = new ArrayList<>();

            Iterator iterator = patient.observationMap.keySet().iterator();

            List<Observation> eqList = new ArrayList<>();
            List<Observation> notList = new ArrayList<>();

            //  eq or not 같이 사용 가능
            if(patient.observationMap.containsKey(Modifier.eq.getModifier()))
            {
                tempResult = extractObservation(beforeResult, Modifier.eq.getModifier(), patient);  //  일치하는 조건 검색
            }
            if(patient.observationMap.containsKey(Modifier.not.getModifier()))
            {
                tempResult = extractObservation(tempResult, Modifier.not.getModifier(), patient);   //  일치하는 조건 삭제
            }
            //  code-value-quantity 따로 사용
            if(patient.observationMap.containsKey(Modifier.value.getModifier()))
            {
                tempResult = extractObservationValue(beforeResult, patient);          //  일치하는 검사 결과 조회
            }
            //  code-value-date 따로 사용
            if(patient.observationMap.containsKey(Modifier.date.getModifier()))
            {

            }

            logger.info("checkObservation : " + tempResult.size() + " :: " + tempResult);
        }

        return beforeResult;
    }

    private List<Entry> checkMedicationRequest(List<Entry> beforeResult, Patient patient)
    {
        if(patient.medicationRequestMap.keySet().size() > 0)
        {
            List<Entry> tempResult = new ArrayList<>();
            Iterator iterator = patient.medicationRequestMap.keySet().iterator();

            //beforeResult.forEach(it->it.medicationRequests.forEach(it2->logger.info(it2)));

            List<Coding> eqList = new ArrayList<>();
            List<Coding> notList = new ArrayList<>();

            while (iterator.hasNext())
            {
                String key = (String)iterator.next();
                List<MedicationStatement> cMedicationStatement = (List<MedicationStatement>)patient.medicationRequestMap.get(key);

                if (key.equals(Modifier.eq.getModifier()))
                {
                    cMedicationStatement.forEach(cit -> eqList.addAll(cit.code.coding));
                }
                if (key.equals(Modifier.not.getModifier()))
                {
                    cMedicationStatement.forEach(cit -> notList.addAll(cit.code.coding));
                }
            }

            for ( Entry e: beforeResult)
            {
                List<Coding> eCoding = new ArrayList<>();
                e.medicationRequests.forEach(it -> eCoding.addAll(it.medicationCodeableConcept.coding));

                extractCondition(tempResult, eqList, notList, e, eCoding);
            }
            return tempResult;

        }
        return beforeResult;
    }

    private List<Entry> checkAllergyIntolerance(List<Entry> beforeResult, Patient patient)
    {
        if(patient.allergyintoleranceMap.keySet().size() > 0)
        {
            List<Entry> tempResult = new ArrayList<>();
            Iterator iterator = patient.allergyintoleranceMap.keySet().iterator();

            //beforeResult.forEach(it->it.allergyIntolerances.forEach(it2->logger.info(it2)));

            List<Coding> eqList = new ArrayList<>();
            List<Coding> notList = new ArrayList<>();

            while (iterator.hasNext())
            {
                String key = (String)iterator.next();
                List<Allergy> cAllergyIntolerance = (List<Allergy>)patient.allergyintoleranceMap.get(key);
                //logger.info("key : " + key);

                if (key.equals(Modifier.eq.getModifier()))
                {
                    cAllergyIntolerance.forEach(cit -> eqList.addAll(cit.code.coding));
                }
                if (key.equals(Modifier.not.getModifier()))
                {
                    cAllergyIntolerance.forEach(cit -> notList.addAll(cit.code.coding));
                }
            }

            for ( Entry e: beforeResult)
            {
                List<Coding> eCoding = new ArrayList<>();
                e.allergyIntolerances.forEach(it -> eCoding.addAll(it.code.coding));

                extractCondition(tempResult, eqList, notList, e, eCoding);
            }
            return tempResult;
        }
        return beforeResult;
    }

    /**
     *  eq, not 조건에 맞는 항목 추출
     * @param tempResult    이전의 조건 검사 결과
     * @param eqList        일치하는 조건 리스트
     * @param notList       제외하는 조건 리스트
     * @param e             이전의 조건 검사 결과 리스트의 항목
     * @param eCoding
     */
    private void extractCondition(List<Entry> tempResult, List<Coding> eqList, List<Coding> notList, Entry e, List<Coding> eCoding)
    {
        for (Coding coding : eCoding)
        {
            for (Coding eqCoding : eqList)
            {
                if(coding.system.equals(eqCoding.system) && coding.code.equals(eqCoding.code))
                {
                    tempResult.add(e);
                }
            }
            for (Coding notCoding : notList)
            {
                if(coding.system.equals(notCoding.system) && coding.code.equals(notCoding.code))
                {
                    tempResult.remove(e);
                }
            }
        }
    }

    /**
     *  Observation에서 일치/불일치 Code의 환자정보를 검색 함
     * @param beforeResult  이전의 검색 결과
     * @param key   eq or not
     * @param patient   검색하고자 하는 조건
     * @return
     */
    private List<Entry> extractObservation(List<Entry> beforeResult, String key, Patient patient)
    {
        List<Entry> tempResult = new ArrayList<>();
        List<Observation> observationList = patient.observationMap.get(key);

        observationList.forEach(it->
        {
            if(key.equals(Modifier.eq.getModifier()))
            {
                beforeResult.forEach(it2-> it2.observations.forEach(it3->it3.code.coding.forEach(it4->
                   {
                       it.code.coding.forEach(it5->{
                                                  if (it5.code.equals(it4.code))
                                                  {
                                                      if(!tempResult.contains(it2))
                                                      {
                                                          tempResult.add(it2);
                                                      }
                                                  }
                                              });

                   })));
            }
            if(key.equals(Modifier.not.getModifier()))
            {
                beforeResult.forEach(it2-> it2.observations.forEach(it3->it3.code.coding.forEach(it4->
                   {
                       it.code.coding.forEach(it5 ->{
                           if(it5.code.equals(it4.code))
                           {
                               tempResult.remove(it2);
                           }
                       });
                   })));
            }
        });

        return tempResult;
    }

    private List<Entry> extractObservationValue(List<Entry> beforeResult, Patient patient )
    {

        List<Entry> tempResult = new ArrayList<>();
        List<Observation> observationList = patient.observationMap.get(Modifier.value.getModifier());

        beforeResult.forEach(it->{
            it.observations.forEach(it2->{
                it2.code.coding.forEach(it3->{
                    observationList.forEach(it4->{
                        it4.code.coding.forEach(it5->{
                        if (it5.system.equals(it3.system) && it5.code.equals(it3.code))
                        {
                            //  it4 : 검색 조건, it2 : 환자 정보
                            if(observationList.size() > 1)
                            {
                                //  검색 조건이 여러개
                            }
                            else
                            {
                                //  검색 조건이 1개
                                if((it4.valueQuantity.prefix.getPrefix().equals(Prefix.ge.getPrefix())
                                        && Double.parseDouble(it4.valueQuantity.value) <= Double.parseDouble(it2.valueQuantity.value))
                                        || (it4.valueQuantity.prefix.getPrefix().equals(Prefix.gt.getPrefix())
                                        && Double.parseDouble(it4.valueQuantity.value) < Double.parseDouble(it2.valueQuantity.value))
                                        || (it4.valueQuantity.prefix.getPrefix().equals(Prefix.le.getPrefix())
                                        && Double.parseDouble(it4.valueQuantity.value) >= Double.parseDouble(it2.valueQuantity.value))
                                        || (it4.valueQuantity.prefix.getPrefix().equals(Prefix.lt.getPrefix())
                                        && Double.parseDouble(it4.valueQuantity.value) > Double.parseDouble(it2.valueQuantity.value)))
                                {

                                    if(!tempResult.contains(it))
                                    {
                                        tempResult.add(it);
                                    }
                                }
                                else
                                {
                                    tempResult.remove(it);
                                }
                            }
                        }});
                    });
                });
            });
        });
        return tempResult;
    }

    private void checkConditionDate()
    {

    }
}
