package com.bmi.clinicaltrial.controller;

import com.bmi.clinicaltrial.data.Gender;
import com.bmi.clinicaltrial.data.Patient;
import com.bmi.clinicaltrial.parser.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class MainController
{
    private Logger logger = LogManager.getLogger();

    final DateParserImpl dateParser;
    final GenderParserImpl genderParser;
    final ObservationParserImpl observationParser;
    final ConditionParserImpl conditionParser;

    public final AllergyParserImpl allergyParser;

    public MainController(DateParserImpl dateParser, GenderParserImpl genderParser, ObservationParserImpl observationParser, ConditionParserImpl conditionParser, AllergyParserImpl allergyParser)
    {
        this.dateParser = dateParser;
        this.genderParser = genderParser;
        this.observationParser = observationParser;
        this.conditionParser = conditionParser;
        this.allergyParser = allergyParser;
    }

    //private final static String testStr = "patient?birthdate=gt2000-01-01&birthdate=lt2010-01-01";

    @GetMapping("/")
    public String main()
    {
        return "Hello World!";
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
             *  LOINC 코드가 없는(?) 경우, 텍스트 검색 기능 지원 필요???
             *  언제, 어떠한 검사를 통해, 그 결과에 대한 수치
             *  ex) 2019-07-23, CBC(피검사), 헤모글로빈 수치
             */
            @RequestParam(required = false, name="_has:Observation:patient:code") List<String> observation,
            @RequestParam(required = false, name="_has:Observation:patient:code:not") List<String> nObservation,
            @RequestParam(required = false, name="_has:Observation:patient:code-value-quantity") List<String> observationQuantity,
            @RequestParam(required = false, name="_has:Observation:patient:code-value-quantity:not") List<String> notObservationQuantity,
            @RequestParam(required = false, name="_has:Observation:patient:code-value-date") List<String> observationCodeAndDate,

            /**
             *  약물 처방 체크    *****
             *  @see RXNORM CODE
             *  TODO: 2019-07-24      처방 수량에 대한 내용 조건 추가해야 함 (어떤 약 몇 mg 이상 등)
             */
            @RequestParam(required = false, name="_has:Medicationstatement:patient:code") List<String> medicationStatement,
            @RequestParam(required = false, name="_has:Medicationstatement:patient:code:not") List<String> nMedicationStatement,

            /**
             *  알러지 체크  ***
             *  @see SNOMED Clinical Terms Code
             *  check : 알러지 포함 과 미포함 사용 시 clinical-status 사용 하면 충돌 발생,
             *  check : 어느 것에 대한 status를 조회하는 것인지 알 수 없음
             */
            @RequestParam(required = false, name="_has:AllergyIntolerance:patient:code") List<String> allergyintolerance,
            @RequestParam(required = false, name="_has:AllergyIntolerance:patient:code:not") List<String> nAllergyintolerance,
            @RequestParam(required = false, name = "clinical-status") String clinicalStatus,

            @RequestParam(required = false, name = "value-quantity") String valueQuantity,
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
        patient.conditionMap = conditionParser.codeParser(condition, nCondition);

        //  검사 체크
        //patient.observationMap = observationParser.codeParser(observation, nObservation);

        // TODO: 2019-07-23
        //  처방 체크
        //logger.info("medicationStatement : " + medicationStatement);
        //logger.info("exMedicationStatement : " + exMedicationStatement);

        //  알러지 체크
        patient.allergyintoleranceMap = allergyParser.codeParser(allergyintolerance, nAllergyintolerance);

        //  count 만 사용 확인
        //logger.info("summary : " +  summary);

        logger.info("Patient : " + patient);

        return 0;
    }

    @GetMapping("/test")
    public int test(@RequestParam Map<String, String> map)
    {
        logger.info(map.toString());
        return 0;
    }
}
