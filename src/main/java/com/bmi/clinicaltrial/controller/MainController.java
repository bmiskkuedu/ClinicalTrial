package com.bmi.clinicaltrial.controller;

import com.bmi.clinicaltrial.parser.ParserDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class MainController
{
    private Logger logger = LogManager.getLogger();

    @Autowired
    private ParserDate parserDate;

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
            //@RequestParam(required = false, name="_has:condition:patient:code") String[] condition,
            //@RequestParam(required = false, name="_has:condition:patient:code:not") String[] exCondition,
            //  질병 체크
            @RequestParam(required = false, name="_has:observation:patient:code") List<String> observation,
            @RequestParam(required = false, name="_has:observation:patient:code:not") List<String> exObservation,
            //  처방 체크
            @RequestParam(required = false, name="_has:medicationstatement:patient:code") List<String> medicationStatement,
            @RequestParam(required = false, name="_has:medicationstatement:patient:code:not") List<String> exMedicationStatement,

            //  알러지 체크

            //  변하지 않음, 항상 검색 결과의 count 만을 원함
            @RequestParam(required = false, name = "summary", defaultValue = "count") String summary
            )
    {
        //  생년월일 체크
        Map<String, String> birthDateMap = parserDate.parseToMap(birthdate);
        logger.info(birthDateMap.toString());

        //  성별 체크
        logger.info("gender : " + gender);
        logger.info("exGender : " + exGender);

        //  질병 체크
        // TODO: 2019-07-16 질병 유무
        logger.info("observation : " + observation);
        logger.info("exObservation : " + exObservation);

        //  처방 체크
        // TODO: 2019-07-16 처방 유무
        logger.info("medicationStatement : " + medicationStatement);
        logger.info("exMedicationStatement : " + exMedicationStatement);

        //  알러지 체크
        // TODO: 2019-07-16 알러지 유무

        //  count 만 사용 확인
        logger.info("summary : " +  summary);

        return 0;
    }
}
