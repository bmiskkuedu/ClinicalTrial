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
    public int findNumberOfPatient(@RequestParam(required = false) String[] birthdate,
                                   @RequestParam(required = false) String gender)
    {
        //  birthdate check
        List<String> birthDateList = Arrays.asList(birthdate);

        Map<String, String> birthDateMap = parserDate.parseToMap(birthDateList);
        logger.info(birthDateMap.toString());

        //  gender check
        //  todo : gender null 처리 / 멀티 value ???
        logger.info("gender : " + gender);

        return 0;
    }
}
