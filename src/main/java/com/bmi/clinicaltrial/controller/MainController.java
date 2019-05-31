package com.bmi.clinicaltrial.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class MainController
{
    private Logger logger = LogManager.getLogger();

    @GetMapping(path = "/patient")
    @ResponseBody
    public int findNumberOfPatient(@RequestParam(required = true) int[] number,
                                   @RequestParam(required = false) int count)
    {
        List<Integer> n = Arrays.stream(number).boxed().collect(Collectors.toList());
        int sum = n.parallelStream().mapToInt(Integer::intValue).sum();

        logger.info("sum : " + sum);

        return sum + count;
    }
}
