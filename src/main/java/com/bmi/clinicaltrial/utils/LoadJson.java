package com.bmi.clinicaltrial.utils;


import com.bmi.clinicaltrial.fhir.data.Coding;
import com.bmi.clinicaltrial.fhir.data.ValueQuantity;
import com.bmi.clinicaltrial.fhir.jsondata.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class LoadJson
{
    private Logger logger = LogManager.getLogger();
    private List<Entry> allSampleData = new ArrayList<Entry>();

    public LoadJson()
    {
        try
        {
            List<File> fileList = Files.walk(Paths.get("sample_data/fhir/"))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            logger.info("file count : " + fileList.size());

            JSONParser parser = new JSONParser();

            for (int i = 0; i < fileList.size(); i++)
            {
                Entry entry = new Entry();
                //logger.info("file name : " + fileList.get(i).getName() + " /// " + fileList.get(i).getAbsolutePath());

                FileReader reader = new FileReader(fileList.get(i).getAbsoluteFile());

                Object json = parser.parse(reader);
                JSONObject jsonObject = (JSONObject) json;

                JSONArray entryList = (JSONArray) jsonObject.get("entry");
                Iterator iterator = entryList.iterator();

                //logger.info(i + " ::: " + fileList.get(i).getAbsoluteFile());
                while (iterator.hasNext())
                {
                    JSONObject item = (JSONObject) iterator.next();
                    JSONObject resource = (JSONObject) item.get("resource");
                    String type = (String) resource.get("resourceType");

                    switch (type)
                    {
                        case "Patient":
                            entry.patient = parserPatient(resource);
                            break;
                        case "Observation":
                            entry.observations.add(parserObservation(resource));
                            break;
                        case "Condition":
                            entry.conditions.add(parserCondition(resource));
                            break;
                        case "MedicationRequest":
                            entry.medicationRequests.add(parserMedicationRequest(resource));
                            break;
                        case "AllergyIntolerance":
                            entry.allergyIntolerances.add(parserAllergyIntolerance(resource));
                            break;
                    }
                }
                //logger.info(entry.sizeToString());
                allSampleData.add(entry);
            }

            logger.info("sample data size : " + allSampleData.size());

        }
        catch (Exception e)
        {
            logger.error("LoadJson " + e);
        }
    }

    private Patient parserPatient(JSONObject resource)
    {
        Patient patient = new Patient();
        patient.id = (String) resource.get("id");
        patient.gender = (String) resource.get("gender");
        patient.birthDate = (String) resource.get("birthDate");

        return patient;
    }

    private Observation parserObservation(JSONObject resource)
    {
        Observation observation = new Observation();

        observation.issued.issued = (String) resource.get("issued");

        JSONObject codeObject = (JSONObject) resource.get("code");
        JSONArray codeArray = (JSONArray) codeObject.get("coding");

        for (Object o : codeArray)
        {
            JSONObject item = (JSONObject) o;
            Coding coding = new Coding();
            coding.code = (String) item.get("code");
            coding.system = (String) item.get("system");
            coding.display = (String) item.get("display");
            observation.code.coding.add(coding);
        }

        JSONObject valueObject = (JSONObject) resource.getOrDefault("valueQuantity", null);
        if (valueObject != null)
        {
            ValueQuantity valueQuantity = new ValueQuantity();
            valueQuantity.code = (String) valueObject.get("code");
            valueQuantity.value = String.valueOf(valueObject.get("value"));
            valueQuantity.unit = (String) valueObject.get("unit");
            valueQuantity.system = (String) valueObject.get("system");
            observation.valueQuantity = valueQuantity;
        }

        return observation;
    }

    private Condition parserCondition(JSONObject resource)
    {
        Condition condition = new Condition();
        JSONObject codeObject = (JSONObject) resource.get("code");
        JSONArray codeArray = (JSONArray) codeObject.get("coding");

        for (Object o : codeArray)
        {
            JSONObject item = (JSONObject) o;
            Coding coding = new Coding();
            coding.code = (String) item.get("code");
            coding.system = (String) item.get("system");
            coding.display = (String) item.get("display");
            condition.code.coding.add(coding);
        }
        return condition;
    }

    private MedicationRequest parserMedicationRequest(JSONObject resource)
    {
        MedicationRequest medicationRequest = new MedicationRequest();
        medicationRequest.authoredOn = (String) resource.getOrDefault("authoredOn", "");
        JSONObject medicationCodeableConceptOjbect = (JSONObject) resource.get("medicationCodeableConcept");
        JSONArray codingArray = (JSONArray) medicationCodeableConceptOjbect.get("coding");

        for (Object o : codingArray)
        {
            JSONObject item = (JSONObject) o;
            Coding coding = new Coding();
            coding.code = (String) item.get("code");
            coding.system = (String) item.get("system");
            coding.display = (String) item.get("display");
            medicationRequest.medicationCodeableConcept.coding.add(coding);
        }


        return medicationRequest;
    }

    private AllergyIntolerance parserAllergyIntolerance(JSONObject resource)
    {
        AllergyIntolerance allergyIntolerance = new AllergyIntolerance();

        JSONObject codeObject = (JSONObject) resource.get("code");
        JSONArray codeArray = (JSONArray) codeObject.get("coding");

        for (Object o : codeArray)
        {
            JSONObject item = (JSONObject) o;
            Coding coding = new Coding();
            coding.code = (String) item.get("code");
            coding.system = (String) item.get("system");
            coding.display = (String) item.get("display");
            allergyIntolerance.code.coding.add(coding);
        }
        return allergyIntolerance;
    }

    public List<Entry> getAllSampleData()
    {
        return allSampleData;
    }
}

