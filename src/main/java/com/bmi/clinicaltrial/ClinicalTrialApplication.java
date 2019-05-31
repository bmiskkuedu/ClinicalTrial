package com.bmi.clinicaltrial;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicalTrialApplication {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        SpringApplication.run(ClinicalTrialApplication.class, args);
        logger.info("ClinicalTrial Application start");
    }

}
