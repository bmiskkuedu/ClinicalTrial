package com.bmi.clinicaltrial.controller;

import com.bmi.clinicaltrial.fhir.data.Gender;
import com.bmi.clinicaltrial.fhir.data.Patient;
import com.bmi.clinicaltrial.fhir.jsondata.Entry;
import com.bmi.clinicaltrial.parser.*;
import com.bmi.clinicaltrial.service.SearchImpl;
import com.bmi.clinicaltrial.utils.LoadJson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class MainController
{
    private Logger logger = LogManager.getLogger();

    private final DateParserImpl dateParser;
    private final GenderParserImpl genderParser;
    private final ObservationParserImpl observationParser;
    private final ConditionParserImpl conditionParser;
    private final AllergyParserImpl allergyParser;
    private final MedicationStatementParserImpl medicationStatementParser;

    private final SearchImpl search;

    public MainController(DateParserImpl dateParser, GenderParserImpl genderParser, ObservationParserImpl observationParser, ConditionParserImpl conditionParser, AllergyParserImpl allergyParser, MedicationStatementParserImpl medicationStatementParser, SearchImpl search)
    {
        this.dateParser = dateParser;
        this.genderParser = genderParser;
        this.observationParser = observationParser;
        this.conditionParser = conditionParser;
        this.allergyParser = allergyParser;
        this.medicationStatementParser = medicationStatementParser;


        this.search = search;
    }

    @GetMapping("/Patient")
    @ResponseBody
    public int findNumberOfPatient(
            //  생년월일 체크
            @RequestParam(required = false) List<String> birthdate,

            /**
             * 성별 체크
             * @see Gender
             */
            @RequestParam(required = false, name = "gender") String gender,
            @RequestParam(required = false, name = "gender:not", defaultValue = "") String nGender,

            /**
             *  진단명 체크
             *  @see SNOWMED Clinical Terms Code
             */
            @RequestParam(required = false, name="_has:Condition:patient:code") List<String> condition,
            @RequestParam(required = false, name="_has:Condition:patient:code:not") List<String> nCondition,

            /**
             *  검사 결과 체크    *****
             *  @see LOINC CODE
             */
            @RequestParam(required = false, name="_has:Observation:patient:code") List<String> observation,
            @RequestParam(required = false, name="_has:Observation:patient:code:not") List<String> nObservation,
            @RequestParam(required = false, name="_has:Observation:patient:code-value-quantity") List<String> observationQuantity,
            @RequestParam(required = false, name="_has:Observation:patient:code-value-date") List<String> observationCodeAndDate,

            /**
             *  약물 처방 체크    *****
             *  @see RXNORM CODE             *
             *  CHECK: 2019-07-29    FHIR 에서 Medicationstatement에 처방량에 대한 검색 PARAMETER가 없음 , chained ~  시 복잡
             *                      DOSAGE 에서 검색해야 함
             */
            @RequestParam(required = false, name="_has:Medicationstatement:patient:code") List<String> medicationStatement,
            @RequestParam(required = false, name="_has:Medicationstatement:patient:code:not") List<String> notMedicationStatement,

            /**
             *  알러지 체크  ***
             *  @see SNOMED Clinical Terms Code
             *  CHECK : 알러지 포함 과 미포함 사용 시 clinical-status 사용 하면 충돌 발생,
             *  CHECK : 어느 것에 대한 status를 조회하는 것인지 알 수 없음
             */
            @RequestParam(required = false, name="_has:AllergyIntolerance:patient:code") List<String> allergyintolerance,
            @RequestParam(required = false, name="_has:AllergyIntolerance:patient:code:not") List<String> nAllergyintolerance,
            //@RequestParam(required = false, name = "clinical-status") String clinicalStatus,


            //  변하지 않음, 항상 검색 결과의 count 만을 원함
            @RequestParam(required = false, name = "_summary", defaultValue = "count") String summary
            ) throws Exception
    {

        Patient patient = new Patient();

        //  생년월일 체크
        patient.birthdateMap = dateParser.parseToMap(birthdate);

        //  성별 체크
        patient.genderMap = genderParser.parser(gender, nGender);

        //  질병 체크
        patient.conditionMap = conditionParser.parser(condition, nCondition);

        //  검사 체크
        patient.observationMap = observationParser.parser(observation, nObservation, observationQuantity, observationCodeAndDate);

        //  처방 체크
        patient.medicationstatementMap = medicationStatementParser.parser(medicationStatement, notMedicationStatement);

        //  알러지 체크
        patient.allergyintoleranceMap = allergyParser.parser(allergyintolerance, nAllergyintolerance);

        logger.info("Patient : " + patient);

        int result = search.search(patient);

        return result;
    }
}
