package com.bmi.clinicaltrial.parser.i;

import com.bmi.clinicaltrial.exception.CustomException;

public interface IMedicationStatement extends Parser
{
    void parser() throws CustomException;
}
