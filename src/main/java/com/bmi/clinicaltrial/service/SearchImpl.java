package com.bmi.clinicaltrial.service;

import com.bmi.clinicaltrial.fhir.data.Modifier;
import com.bmi.clinicaltrial.fhir.data.Patient;
import com.bmi.clinicaltrial.fhir.jsondata.Entry;
import com.bmi.clinicaltrial.utils.LoadJson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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


        logger.info(result);
        logger.info("result : " + result.size());

        return 0;
    }

    private List<Entry> checkGender(Patient patient)
    {
        List<Entry> tempResult = new ArrayList<>();

        if(patient.genderMap.keySet().size() > 0)
        {
            Iterator genderIterator = patient.genderMap.keySet().iterator();
            while (genderIterator.hasNext())
            {
                String key = (String) genderIterator.next();
                //  check eq 우선 검색, (eq, not) 동시에 들어올 시 eq만 검색 (문제???)
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
                    //logger.info("birthdate : " + pDate);

                    Iterator birthdateIterator = patient.birthdateMap.keySet().iterator();

                    while(birthdateIterator.hasNext())
                    {
                        String key = (String) birthdateIterator.next();
                        Date cDate = sdf.parse(patient.birthdateMap.get(key));
                        //logger.info("check birthdate : " + key + " : " + sdf.format(cDate) + " >>> " + sdf.format(pDate));

                        if((key.equals("eq") && (sdf.format(pDate).equals(sdf.format(cDate))))
                        || (key.equals("le") && (pDate.before(cDate)))
                        || (key.equals("ge") && (pDate.after(cDate))))
                        {
                            if(!tempResult.contains(beforeResult.get(i)))
                            {
                                //logger.info("add : ");
                                tempResult.add(beforeResult.get(i));
                            }
                        }
                        else
                        {
                            if(tempResult.contains(beforeResult.get(i)))
                            {
                                //logger.info("remove : ");
                                tempResult.remove(beforeResult.get(i));
                            }
                        }
                    }
                    //logger.info("----------------------");
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

    private List<Entry> checkCondition(List<Entry> beforeResult, Patient patient)
    {
        List<Entry> tempResult = new ArrayList<>();
        return tempResult;
    }
}
