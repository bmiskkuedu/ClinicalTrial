package com.bmi.clinicaltrial.controller;

import com.bmi.clinicaltrial.data.ClinicalStatus;
import com.bmi.clinicaltrial.data.Patient;
import com.bmi.clinicaltrial.parser.ImplDateParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class MainController
{
    private Logger logger = LogManager.getLogger();

    @Autowired
    private ImplDateParser implDateParser;

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
            @RequestParam(required = false, name = "gender") String gender,
            @RequestParam(required = false, name = "gender:not") String exGender,

            //  CHECK : LOINC, SNOMED, ICD? 코드 번호만으로 모두 처리가 가능한가?
            //  질병 체크
            @RequestParam(required = false, name="_has:observation:patient:code") List<String> observation,
            @RequestParam(required = false, name="_has:observation:patient:code:not") List<String> exObservation,

            //  처방 체크
            // TODO: 2019-07-22 처방 수량에 대한 내용 조건 추가해야 함 (어떤 약 몇 mg 이상 등)
            @RequestParam(required = false, name="_has:medicationstatement:patient:code") List<String> medicationStatement,
            @RequestParam(required = false, name="_has:medicationstatement:patient:code:not") List<String> exMedicationStatement,

            //  알러지 체크
            //  check : 알러지 포함 과 미포함 사용 시 clinical-status 사용 하면 충돌 발생,
            //  check : 어느 것에 대한 status를 조회하는 것인지 알 수 없음
            @RequestParam(required = false, name="_has:allergyintolerance:patient:code") List<String> allergyintolerance,
            @RequestParam(required = false, name="_has:allergyintolerance:patient:code:not") List<String> exAllergyintolerance,
            @RequestParam(required = false, name = "clinical-status") String clinicalStatus,

            //  변하지 않음, 항상 검색 결과의 count 만을 원함
            @RequestParam(required = false, name = "summary", defaultValue = "count") String summary
            ) throws Exception
    {

        Patient patient = new Patient();

        //  생년월일 체크
        patient.birthdateMap = implDateParser.parseToMap(birthdate);
        logger.info("birthDate Map : " + patient.birthdateMap.toString());

        //  성별 체크
        logger.info("gender : " + gender);
        logger.info("exGender : " + exGender);

        //  질병 체크
        logger.info("observation : " + observation);
        logger.info("exObservation : " + exObservation);

        //  처방 체크
        logger.info("medicationStatement : " + medicationStatement);
        logger.info("exMedicationStatement : " + exMedicationStatement);

        //  알러지 체크
        logger.info("allergyintolerance : " + allergyintolerance);
        logger.info("exAllergyintolerance : " + exAllergyintolerance);
        //  알러지 상태 확인
        ClinicalStatus cs = ClinicalStatus.getType(clinicalStatus.toLowerCase());
        if(cs == ClinicalStatus.ERROR)
        {
            return -1;
        }
        logger.info("clinicalStatus : " + cs);



        //  count 만 사용 확인
        logger.info("summary : " +  summary);

        return 0;
    }
}
