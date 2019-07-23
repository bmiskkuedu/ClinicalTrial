package com.bmi.clinicaltrial.controller;

import com.bmi.clinicaltrial.data.ClinicalStatus;
import com.bmi.clinicaltrial.data.Patient;
import com.bmi.clinicaltrial.parser.DateParserImpl;
import com.bmi.clinicaltrial.parser.GenderParserImpl;
import com.bmi.clinicaltrial.parser.ObservationParserImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class MainController
{
    private Logger logger = LogManager.getLogger();

    private final DateParserImpl dateParserImpl;
    private final GenderParserImpl genderParser;
    private final ObservationParserImpl observationParser;

    public MainController(DateParserImpl dateParserImpl, GenderParserImpl genderParser, ObservationParserImpl observationParser)
    {
        this.dateParserImpl = dateParserImpl;
        this.genderParser = genderParser;
        this.observationParser = observationParser;
    }

    //private final static String testStr = "patient?birthdate=gt2000-01-01&birthdate=lt2010-01-01";

    @GetMapping("/")
    public String main()
    {
        return "Hello World!";
    }

    @GetMapping("/patient")
    @ResponseBody
    public int findNumberOfPatient(
            //  생년월일 체크
            @RequestParam(required = false) List<String> birthdate,

            //  성별 체크
            @RequestParam(required = false, name = "gender", defaultValue = "") String gender,
            @RequestParam(required = false, name = "gender:not", defaultValue = "") String nGender,

            //  검사 결과 체크
            //  CHECK : 1. FHIR example) LOINC CODE를 사용, LOINC 코드가 없는(?) 경우, 텍스트 검색 기능 지원 필요???
            //  CHECK : 2. ex) Condition?code=(http://acme.org/conditions/codes)|ha125 ::: system 코드가 들어가는데 꼭 사용?
            //  CHECK : DB에서 데이터가 어떤지 모르니 일단 제거, client-server-db 간 정의(?) 가 필요할듯
            //  CHECK : 언제, 어떠한 검사를 통해, 그 결과에 대한 수치
            //  CHECK : ex) 2019-07-23, CBC(피검사), 헤모글로빈 수치
            @RequestParam(required = false, name="_has:observation:patient:code") List<String> observation,
            @RequestParam(required = false, name="_has:observation:patient:code:not") List<String> nObservation,

            //  질환 체크
            // TODO : Condition 에서 진환명 검색

            //  약물 처방 체크
            // TODO: 2019-07-22 처방 수량에 대한 내용 조건 추가해야 함 (어떤 약 몇 mg 이상 등)
            @RequestParam(required = false, name="_has:medicationstatement:patient:code") List<String> medicationStatement,
            @RequestParam(required = false, name="_has:medicationstatement:patient:code:not") List<String> nMedicationStatement,

            //  알러지 체크
            //  check : 알러지 포함 과 미포함 사용 시 clinical-status 사용 하면 충돌 발생,
            //  check : 어느 것에 대한 status를 조회하는 것인지 알 수 없음
            @RequestParam(required = false, name="_has:allergyintolerance:patient:code") List<String> allergyintolerance,
            @RequestParam(required = false, name="_has:allergyintolerance:patient:code:not") List<String> nAllergyintolerance,
            @RequestParam(required = false, name = "clinical-status") String clinicalStatus,

            //  변하지 않음, 항상 검색 결과의 count 만을 원함
            @RequestParam(required = false, name = "summary", defaultValue = "count") String summary
            ) throws Exception
    {

        Patient patient = new Patient();

        //  생년월일 체크
        patient.birthdateMap = dateParserImpl.parseToMap(birthdate);
        logger.info("birthDate Map : " + patient.birthdateMap);

        //  성별 체크
        patient.genderMap = genderParser.parser(gender, nGender);
        logger.info("gender Map : " + patient.genderMap);

        //  질병 체크
        patient.observationMap = observationParser.codeParser(observation, nObservation);
        logger.info("observatiomMap : " + patient.observationMap);

        // TODO: 2019-07-23
        //  처방 체크
        //logger.info("medicationStatement : " + medicationStatement);
        //logger.info("exMedicationStatement : " + exMedicationStatement);

        // TODO: 2019-07-23
        //  알러지 체크
        //logger.info("allergyintolerance : " + allergyintolerance);
        //logger.info("exAllergyintolerance : " + exAllergyintolerance);
        //  알러지 상태 확인
        ClinicalStatus cs = ClinicalStatus.getType(clinicalStatus.toLowerCase());
        if(cs == ClinicalStatus.ERROR)
        {
            return -1;
        }
        //logger.info("clinicalStatus : " + cs);

        //  count 만 사용 확인
        //logger.info("summary : " +  summary);

        logger.info("Patient : " + patient);

        return 0;
    }
}
