package com.bmi.clinicaltrial.service;

import com.bmi.clinicaltrial.fhir.data.Patient;
import com.bmi.clinicaltrial.fhir.jsondata.Entry;
import com.bmi.clinicaltrial.utils.LoadJson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchImpl implements ISearch
{
    private Logger logger = LogManager.getLogger();

    private LoadJson loadJson = new LoadJson();
    private List<Entry> allSampleData;

    public SearchImpl()
    {
        allSampleData = loadJson.getAllSampleData();
        logger.info("allSampleData : " + allSampleData.size());
    }

    @Override
    public int search(Patient patient)
    {
        /**
         *  1. birthdate check
         *  2. gender check
         *  3. condition check
         *  4. observation check
         *  5. medicationstatement(medicationrequest) check
         *  6. allergyintolerance check
         */
        return 0;
    }
}
